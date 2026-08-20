package com.tiomadre.foragersinsight.common.gui;

import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class HandbasketSlot extends Slot {

    public HandbasketSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return canPlaceInHandbasket(stack);
    }

    public static boolean canPlaceInHandbasket(ItemStack stack) {
        return stack.is(FITags.ItemTag.HANDBASKET_ALLOWED);
    }
}
