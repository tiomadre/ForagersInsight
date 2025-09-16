package com.doltandtio.foragersinsight.common.item;

import com.doltandtio.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class TonicItem extends DrinkableItem {
    private static final int MEDICINAL_DURATION = 1200;

    public TonicItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide) {
            entity.addEffect(new MobEffectInstance(FIMobEffects.MEDICINAL.get(), MEDICINAL_DURATION));
        }
        return result;
    }
}