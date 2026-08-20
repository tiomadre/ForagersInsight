package com.tiomadre.foragersinsight.common.gui;

import com.tiomadre.foragersinsight.core.registry.FIMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HandbasketMenu extends AbstractContainerMenu {

    private final Container container;


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



    public HandbasketMenu(int id, Inventory inventory) {
        this(id, inventory, new SimpleContainer(10));
    }

    public HandbasketMenu(int id, Inventory inventory, Container container) {
        super(FIMenuTypes.HANDBASKET_MENU.get(), id);
        checkContainerSize(container, 14);
        this.container = container;
        container.startOpen(inventory.player);

        for (int row = 0; row < BASKET_ROWS; row++) {
            for (int col = 0; col < BASKET_COLS; col++) {
                int index = col + row * BASKET_COLS;
                int x = BASKET_START_X + col * SLOT_SIZE + SHIFT_X;
                int y = BASKET_START_Y + row * SLOT_SIZE + SHIFT_Y;
                this.addSlot(new HandbasketSlot(container, index, x, y));
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = col + row * 9 + 9;
                int x = INV_START_X + col * SLOT_SIZE;
                int y = INV_START_Y + row * SLOT_SIZE;
                this.addSlot(new Slot(inventory, slotIndex, x, y));
            }
        }

        for (int col = 0; col < 9; col++) {
            int x = INV_START_X + col * SLOT_SIZE;
            this.addSlot(new Slot(inventory, col, x, HOTBAR_Y));
        }
    }




    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack copyOfSourceStack = ItemStack.EMPTY;
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }
        if (sourceSlot.hasItem()) {
            ItemStack sourceStack = sourceSlot.getItem();
            copyOfSourceStack = sourceStack.copy();
            if (index < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(sourceStack, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(sourceStack, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (sourceStack.isEmpty()) {
                sourceSlot.set(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }
        }
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

//Not sure what this method is for, but leaving it for now in case needed later
//    @Override
//    public void clicked(int slotId, int dragType, @NotNull ClickType clickType, @NotNull Player player) {
//        if (slotId == this.basketSlotId()) {
//            return;
//        }
//        super.clicked(slotId, dragType, clickType, player);
//    }
//
//
}