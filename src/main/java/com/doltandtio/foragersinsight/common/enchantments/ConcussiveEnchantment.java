package com.doltandtio.foragersinsight.common.enchantments;
import com.doltandtio.foragersinsight.common.item.MalletItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

// Mallet and Shovel only.
// I: +10% Critical damage, 2s stun, 12s target cooldown
// II: +10% Critical damage, 2.5s stun, 11s target cooldown
// III: +10% Critical damage, 3s stun, 10s target cooldown

public class ConcussiveEnchantment extends Enchantment {
    public ConcussiveEnchantment(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof MalletItem
                || stack.getItem() instanceof ShovelItem;
    }

    @Override
    public int getMinCost(int level) {
        return 10 + 5 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
