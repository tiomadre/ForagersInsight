package com.tiomadre.foragersinsight.core.other.toolevents;

import com.tiomadre.foragersinsight.common.item.component.WaxedComponent;
import com.tiomadre.foragersinsight.core.registry.FIDataComponents;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class WaxedBoots {
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
        stack.set(FIDataComponents.BOOTWAXED,
                new WaxedComponent(true, HONEYCOMB_COUNT * WAXED_DURATION, STICKY_RESISTANCE_LEVEL));
    }

    public static void waxWithHoneycombBlock(ItemStack stack) {
        stack.set(FIDataComponents.BOOTWAXED,
                new WaxedComponent(true, BLOCK_WAXED_DURATION, STICKY_RESISTANCE_II_LEVEL));
    }

    public static boolean isWaxed(ItemStack stack) {
        return stack.get(FIDataComponents.BOOTWAXED).isWaxed();
    }

    public static int getWaxedDuration(ItemStack stack) {
        if(!isWaxed(stack)){
            return 0;
        }else{
            return Objects.requireNonNull(stack.get(FIDataComponents.BOOTWAXED)).duration();
        }
    }

    public static int getWaxedLevel(ItemStack stack) {

        if(!isWaxed(stack)){
            return 0;
        }else{
            return Objects.requireNonNull(stack.get(FIDataComponents.BOOTWAXED)).enchantLevel();
        }

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
            stack.remove(FIDataComponents.BOOTWAXED);
            return;
        }
        stack.set(FIDataComponents.BOOTWAXED,new WaxedComponent(true,duration,
                stack.get(FIDataComponents.BOOTWAXED).enchantLevel()));
    }
}