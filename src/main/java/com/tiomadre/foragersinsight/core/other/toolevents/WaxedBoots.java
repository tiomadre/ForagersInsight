package com.tiomadre.foragersinsight.core.other.toolevents;

import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class WaxedBoots {
    public static final String WAXED_DURATION_TAG = "ForagersInsightWaxedDuration";
    private static final String WAXED_LEVEL_TAG = "ForagersInsightWaxedLevel";
    public static final int HONEYCOMB_COUNT = 2;
    public static final int STICKY_RESISTANCE_LEVEL = 1;
    public static final int STICKY_RESISTANCE_II_LEVEL = 2;
    private static final int WAXED_DURATION = 4800;
    private static final int BLOCK_WAXED_DURATION = 3 * WAXED_DURATION;
    private static final int WALKING_DRAIN = 20;
    private static final int STUCK_PREVENTION_DRAIN = 200;
    private static final int STICKY_RESISTANCE_REFRESH_DURATION = 40;

    public static boolean isBoots(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.FEET;
    }

    public static void wax(ItemStack stack) {
        stack.getOrCreateTag().putInt(WAXED_DURATION_TAG, HONEYCOMB_COUNT * WAXED_DURATION);
        stack.getOrCreateTag().putInt(WAXED_LEVEL_TAG, STICKY_RESISTANCE_LEVEL);
    }

    public static void waxWithHoneycombBlock(ItemStack stack) {
        stack.getOrCreateTag().putInt(WAXED_DURATION_TAG, BLOCK_WAXED_DURATION);
        stack.getOrCreateTag().putInt(WAXED_LEVEL_TAG, STICKY_RESISTANCE_II_LEVEL);
    }

    public static boolean isWaxed(ItemStack stack) {
        return getWaxedDuration(stack) > 0;
    }

    public static int getWaxedDuration(ItemStack stack) {
        return stack.getOrCreateTag().getInt(WAXED_DURATION_TAG);
    }

    public static int getWaxedLevel(ItemStack stack) {
        if (!isWaxed(stack)) return 0;

        return Math.max(STICKY_RESISTANCE_LEVEL, stack.getOrCreateTag().getInt(WAXED_LEVEL_TAG));
    }

    public static void appendTooltip(ItemStack stack, java.util.List<Component> tooltip) {
        int duration = getWaxedDuration(stack);
        if (duration <= 0) return;

        int stepsRemaining = (int) Math.ceil(duration / (double) WALKING_DRAIN);
        tooltip.add(Component.translatable("tooltip.foragersinsight.waxed_boots", stepsRemaining)
                .withStyle(ChatFormatting.GRAY));
    }

    public static void tick(LivingEntity entity) {
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (!isWaxed(boots)) return;

        int amplifier = getWaxedLevel(boots) - 1;
        entity.addEffect(new MobEffectInstance(FIMobEffects.STICKY_RESISTANCE, STICKY_RESISTANCE_REFRESH_DURATION, amplifier, false, false, true));

        if (entity.level().getGameTime() % WALKING_DRAIN == 0 && entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5D) {
            shrinkWaxedDuration(boots, WALKING_DRAIN);
        }
    }

    public static void drainForStuckPrevention(LivingEntity entity) {
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (!isWaxed(boots)) return;

        shrinkWaxedDuration(boots, STUCK_PREVENTION_DRAIN);
    }

    private static void shrinkWaxedDuration(ItemStack stack, int amount) {
        int duration = Math.max(0, getWaxedDuration(stack) - amount);
        if (duration == 0) {
            stack.getOrCreateTag().remove(WAXED_DURATION_TAG);
            stack.getOrCreateTag().remove(WAXED_LEVEL_TAG);
            return;
        }
        stack.getOrCreateTag().putInt(WAXED_DURATION_TAG, duration);
    }
}