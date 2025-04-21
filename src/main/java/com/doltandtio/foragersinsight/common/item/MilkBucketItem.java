package com.doltandtio.foragersinsight.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MilkBucketItem extends Item {
    private static final int DRINK_DURATION = 32;
    public MilkBucketItem (Properties settings) {
        super(settings);
    }
    @Override
    public int getUseDuration(ItemStack stack) {
        return DRINK_DURATION;
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            entity.removeAllEffects();
        }
        if (entity instanceof Player player) {

            if (!player.getAbilities().instabuild) {
                return new ItemStack(Items.BUCKET);
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }
}
