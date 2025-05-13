package com.doltandtio.foragersinsight.common.enchantments;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class FarmhandEnchantment extends Enchantment {
    public FarmhandEnchantment() {
        super(Rarity.UNCOMMON, FIEnchantCategories.Harvest_Exclusive, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        // any hoes
        if (item instanceof HoeItem) return true;
        // any shears
        if (item instanceof ShearsItem) return true;
        // any knives
        if (item instanceof KnifeItem) return true;
        return false;
    }
}