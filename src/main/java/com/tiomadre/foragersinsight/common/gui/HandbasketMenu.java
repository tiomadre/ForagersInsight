package com.tiomadre.foragersinsight.common.gui;

import com.tiomadre.foragersinsight.core.registry.FIMenuTypes;
import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;


import java.util.Objects;

public class HandbasketMenu extends AbstractContainerMenu {

    ItemStack basket;
    IItemHandler inputs;
    int originalSlot;

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
        this(id,inventory, new ItemStackHandler(10),ItemStack.EMPTY,0);
    }

    public HandbasketMenu(int id, Inventory inventory, IItemHandler inputs, ItemStack basket, int slot) {
        super(FIMenuTypes.HANDBASKET_MENU.get(), id);
        this.basket=basket;
        this.inputs=inputs;
        this.originalSlot=slot;
        NonNullList<ItemStack> basketInv = NonNullList.withSize(10,ItemStack.EMPTY);
        Objects.requireNonNull(basket.get(DataComponents.CONTAINER)).copyInto(basketInv);
        for (int row = 0; row < BASKET_ROWS; row++) {
            for (int col = 0; col < BASKET_COLS; col++) {
                int index = col + row * BASKET_COLS;
                int x = BASKET_START_X + col * SLOT_SIZE + SHIFT_X;
                int y = BASKET_START_Y + row * SLOT_SIZE + SHIFT_Y;

                this.addSlot(new SlotItemHandler(inputs, index, x, y));


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
    public void removed(Player player){
        super.removed(player);
        if (player instanceof ServerPlayer) {
            if (!basket.is(Items.AIR)) {
                NonNullList<ItemStack> basketContents = NonNullList.withSize(10, ItemStack.EMPTY);
                for (int i = 0; i < 10; i++) {
                    basketContents.set(i, inputs.getStackInSlot(i));

                }
                this.basket.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(basketContents));
                player.setItemInHand(player.getUsedItemHand(), basket);
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack copyOfSourceStack = ItemStack.EMPTY;
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack sourceStack = sourceSlot.getItem();
        copyOfSourceStack = sourceStack.copy();

            if (index < 10) {
                if (!sourceStack.is(FITags.ItemTag.HANDBASKET_ALLOWED)) {
                    return ItemStack.EMPTY;
                }
                if (!this.moveItemStackTo(sourceStack, BASKET_ROWS * BASKET_COLS, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else{
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
    public boolean stillValid(Player player) {
        return true;
    }



}