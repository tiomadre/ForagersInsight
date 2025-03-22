package com.doltandtio.naturesdelight.common.item;

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
    // Duration (ticks) to drink the item – same as a milk bucket or potion
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
        return UseAnim.DRINK;  // Use drinking animation (glug glug)
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // Starts drink action. This then calls on finishUsingItem after DRINK_DURATION ticks.
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // Clears effects on the server to avoid duplicating logic on client&#8203;:contentReference[oaicite:5]{index=5}
        if (!level.isClientSide) {
            entity.removeAllEffects();  // remove all potion effects (like drinking a milk bucket)
        }

        if (entity instanceof Player player) {
            // If the player is not in creative mode, give back an empty bucket as a container
            if (!player.getAbilities().instabuild) {
                return new ItemStack(Items.BUCKET);
            }
        }

        // If player is in creative or entity is not a player, simply return the remaining stack
        return super.finishUsingItem(stack, level, entity);
    }
}
