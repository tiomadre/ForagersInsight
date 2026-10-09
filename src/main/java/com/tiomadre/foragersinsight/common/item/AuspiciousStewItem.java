package com.tiomadre.foragersinsight.common.item;

import com.tiomadre.foragersinsight.core.registry.FIDataComponents;
import com.tiomadre.foragersinsight.data.server.recipes.FICookingRecipes;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class AuspiciousStewItem extends ConsumableItem {
    private static final int EFFECT_DURATION = 400;

    public AuspiciousStewItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }





    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Holder<MobEffect> effect = stack.get(FIDataComponents.AUSPICIOUS_EFFECT);
        if (effect != null) {
            entity.addEffect(new MobEffectInstance(effect, EFFECT_DURATION));
        }

        return super.finishUsingItem(stack, level, entity);
    }



    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entity) {
        if (level.isClientSide) {
            return;
        }
        Holder<MobEffect> effect = stack.get(FIDataComponents.AUSPICIOUS_EFFECT);
        if (effect != null) {
            entity.addEffect(new MobEffectInstance(effect, EFFECT_DURATION));
        }
    }
}