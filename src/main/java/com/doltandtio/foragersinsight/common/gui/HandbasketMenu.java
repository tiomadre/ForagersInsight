package com.doltandtio.foragersinsight.common.gui;

import com.doltandtio.foragersinsight.core.registry.FIMenuTypes;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class HandbasketMenu extends AbstractContainerMenu {
    private final IItemHandler basketInv;
    private final ItemStack basketStack;
    private final int basketSlotIndex;

    private static final int SLOT_SIZE     = 18;
    private static final int GUI_WIDTH     = 176;
    private static final int DISPLAY_Y     = 17;
    private static final int SHIFT_X      =  1;
    private static final int SHIFT_Y      = -3;
    private static final int BASKET_COLS   = 5;
    private static final int BASKET_ROWS   = 2;
    private static final int BASKET_START_X = (GUI_WIDTH - BASKET_COLS * SLOT_SIZE) / 2;
    private static final int BASKET_START_Y = DISPLAY_Y + SLOT_SIZE + 4;
    private static final int INV_START_X  = 8;
    private static final int INV_START_Y  = 84;
    private static final int HOTBAR_Y     = INV_START_Y + 3 * SLOT_SIZE + 4; // 142

    public HandbasketMenu(int id, Inventory playerInv, ItemStack basketStack) {

        super(FIMenuTypes.HANDBASKET_MENU.get(), id);
        this.basketStack = basketStack;
        this.basketInv = basketStack
                .getCapability(ForgeCapabilities.ITEM_HANDLER)
                .orElseThrow(() -> new IllegalStateException("Missing handler"));
        this.basketSlotIndex = findBasketSlot(playerInv, basketStack);
        // basket’s 2×5 griddy
        for (int row = 0; row < BASKET_ROWS; row++) {
            for (int col = 0; col < BASKET_COLS; col++) {
                int index = col + row * BASKET_COLS;
                int x = BASKET_START_X + col * SLOT_SIZE + SHIFT_X;
                int y = BASKET_START_Y + row * SLOT_SIZE + SHIFT_Y;
                addSlot(new SlotItemHandler(basketInv, index, x, y) {
                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return stack.is(FITags.ItemTag.HANDBASKET_ALLOWED);
                    }
                });
            }
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = col + row * 9 + 9;
                int x = INV_START_X + col * SLOT_SIZE;
                int y = INV_START_Y + row * SLOT_SIZE;
                addSlot(createPlayerSlot(playerInv, slotIndex, x, y));
            }
        }
        for (int col = 0; col < 9; col++) {
            int x = INV_START_X + col * SLOT_SIZE;
            addSlot(createPlayerSlot(playerInv, col, x, HOTBAR_Y));
        }
    }
    public HandbasketMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, buf.readItem());
    }
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // no basket in itself
        if (ItemStack.isSameItemSameTags(sourceStack, this.basketStack)) {
            return ItemStack.EMPTY;
        }

        if (index < BASKET_ROWS * BASKET_COLS) {
            if (!sourceStack.is(FITags.ItemTag.HANDBASKET_ALLOWED)) {
                return ItemStack.EMPTY;
            }
            if (!this.moveItemStackTo(sourceStack, BASKET_ROWS * BASKET_COLS, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!sourceStack.is(FITags.ItemTag.HANDBASKET_ALLOWED) ||
                    !this.moveItemStackTo(sourceStack, 0, BASKET_ROWS * BASKET_COLS, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        return copyOfSourceStack;
    }
    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
    // prevent moving
    private int findBasketSlot(Inventory playerInv, ItemStack basketStack) {
        for (int i = 0; i < playerInv.getContainerSize(); i++) {
            if (ItemStack.isSameItemSameTags(playerInv.getItem(i), basketStack)) {
                return i;
            }
        }
        return -1;
    }
    private Slot createPlayerSlot(Inventory playerInv, int index, int x, int y) {
        return new Slot(playerInv, index, x, y) {
            @Override
            public boolean mayPickup(@NotNull Player player) {
                if (index == HandbasketMenu.this.basketSlotIndex) {
                    return false;
                }
                return super.mayPickup(player);
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                if (index == HandbasketMenu.this.basketSlotIndex) {
                    return false;
                }
                return super.mayPlace(stack);
            }
        };
    }
}
//basquet gang rise up!