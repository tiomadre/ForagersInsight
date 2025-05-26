package com.doltandtio.foragersinsight.core.mixin.shears_fortune;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class ShearsFortuneMixin {
    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    private void allowFortuneOnShears(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        // Check if  Fortune enchanted ShearsItem
        if ((Object) this == Enchantments.BLOCK_FORTUNE && stack.getItem() instanceof ShearsItem) {
            cir.setReturnValue(true); // Allow enchanting
        }
    }
}