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
        if (stack.getItem() instanceof ShearsItem &&
                EnchantmentHelper.getEnchantments(stack).containsKey(Enchantments.BLOCK_FORTUNE)) {
            // Implement conditional logic for applying enchantments here.
            // Customize behavior if needed.
        }
    }
}
