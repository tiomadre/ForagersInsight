//package com.tiomadre.foragersinsight.core.mixin.shears_fortune;
//
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.AnvilMenu;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.ShearsItem;
//import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraft.world.item.enchantment.Enchantments;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//



// I think this is no longer necessary
//@Mixin(AnvilMenu.class)
//public class FortuneApplyAnvilMixin {
//    @Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
//    private void allowFortuneBooks(Player player, ItemStack stack, CallbackInfo ci) {
//        AnvilMenu menu = (AnvilMenu) (Object) this;
//        ItemStack leftStack = menu.getSlot(0).getItem();
//        ItemStack rightStack = menu.getSlot(1).getItem();
//
//        ItemStack shears = ItemStack.EMPTY;
//        ItemStack book = ItemStack.EMPTY;
//
//        if (leftStack.getItem() instanceof ShearsItem &&
//                EnchantmentHelper.getEnchantments(rightStack).containsKey(Enchantments.FORTUNE)) {
//            shears = leftStack;
//            book = rightStack;
//        } else if (rightStack.getItem() instanceof ShearsItem &&
//                EnchantmentHelper.getEnchantments(leftStack).containsKey(Enchantments.FORTUNE)) {
//            shears = rightStack;
//            book = leftStack;
//        }
//
//        if (!shears.isEmpty() && !book.isEmpty()) {
//            int shearsLevel = stack.getEnchantmentLevel(player.level().holderOrThrow(Enchantments.FORTUNE));
//            int bookLevel = stack.getEnchantmentLevel(player.level().holderOrThrow(Enchantments.FORTUNE));
//            int newLevel = (shearsLevel == bookLevel) ? shearsLevel + 1 : Math.max(shearsLevel, bookLevel);
//            newLevel = Math.min(newLevel, 3);
//
//            var enchantments = stack.get(EnchantmentEffectComponents.EQUIPMENT_DROPS);
//            enchantments.put(Enchantments.FORTUNE, newLevel);
//            EnchantmentHelper.setEnchantments(enchantments, stack);
//
//            ci.cancel();
//        }
//    }
//}