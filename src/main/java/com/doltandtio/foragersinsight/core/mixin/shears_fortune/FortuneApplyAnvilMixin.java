package com.doltandtio.foragersinsight.core.mixin.shears_fortune;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class FortuneApplyAnvilMixin {
    @Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
    private void allowFortuneBooks(Player player, ItemStack stack, CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu) (Object) this;
        ItemStack leftStack = menu.getSlot(0).getItem();
        ItemStack rightStack = menu.getSlot(1).getItem();

        ItemStack shears = ItemStack.EMPTY;
        ItemStack book = ItemStack.EMPTY;

        if (leftStack.getItem() instanceof ShearsItem &&
                EnchantmentHelper.getEnchantments(rightStack).containsKey(Enchantments.BLOCK_FORTUNE)) {
            shears = leftStack;
            book = rightStack;
        } else if (rightStack.getItem() instanceof ShearsItem &&
                EnchantmentHelper.getEnchantments(leftStack).containsKey(Enchantments.BLOCK_FORTUNE)) {
            shears = rightStack;
            book = leftStack;
        }

        if (!shears.isEmpty() && !book.isEmpty()) {
            int shearsLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, shears);
            int bookLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, book);
            int newLevel = (shearsLevel == bookLevel) ? shearsLevel + 1 : Math.max(shearsLevel, bookLevel);
            newLevel = Math.min(newLevel, Enchantments.BLOCK_FORTUNE.getMaxLevel());

            var enchantments = EnchantmentHelper.getEnchantments(stack);
            enchantments.put(Enchantments.BLOCK_FORTUNE, newLevel);
            EnchantmentHelper.setEnchantments(enchantments, stack);

            ci.cancel();
        }
    }
}