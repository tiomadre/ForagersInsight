package com.tiomadre.foragersinsight.core.other;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.other.toolevents.WaxedBoots;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;

import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.UUID;

//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID)
public class StickyResistanceEvents {
    private static final int SPEED_BOOST_DURATION = 60;
    private static final String SPEED_BOOST_END_TICK_TAG = "ForagersInsightStickyResistanceSpeedEndTick";
    private static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(
            Attributes.MOVEMENT_SPEED.getKey().location(),
            0.1D,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );

    @SubscribeEvent
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        MobEffect incomingEffect = event.getEffectInstance().getEffect().value();
        if (incomingEffect != FIMobEffects.STUCK.get()) return;
        if (!event.getEntity().hasEffect(FIMobEffects.STICKY_RESISTANCE)) return;

        WaxedBoots.drainForStuckPrevention(event.getEntity());
        if (event.getEntity().getEffect(FIMobEffects.STICKY_RESISTANCE).getAmplifier() >= 1) {
            applySpeedBoost(event.getEntity());
        }
        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        MobEffect addedEffect = event.getEffectInstance().getEffect().value();
        if (addedEffect != FIMobEffects.STICKY_RESISTANCE.get()) return;
        if (!entity.hasEffect(FIMobEffects.STUCK)) return;

        entity.removeEffect(FIMobEffects.STUCK);
        applySpeedBoost(entity);
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Pre event) {
        LivingEntity entity = event.getEntity().getControllingPassenger();
        if (entity.level().isClientSide()) return;

        long speedEndTick = entity.getPersistentData().getLong(SPEED_BOOST_END_TICK_TAG);
        if (speedEndTick == 0L || entity.level().getGameTime() < speedEndTick) return;

        removeSpeedBoost(entity);
        entity.getPersistentData().remove(SPEED_BOOST_END_TICK_TAG);
    }

    private static void applySpeedBoost(LivingEntity entity) {
        AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) return;

        removeSpeedBoost(entity);
        movementSpeed.addTransientModifier(SPEED_BOOST_MODIFIER);
        entity.getPersistentData().putLong(
                SPEED_BOOST_END_TICK_TAG,
                entity.level().getGameTime() + SPEED_BOOST_DURATION
        );
    }

    private static void removeSpeedBoost(LivingEntity entity) {
        AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null || movementSpeed.getModifier(SPEED_BOOST_MODIFIER.id()) == null) return;

        movementSpeed.removeModifier( Attributes.MOVEMENT_SPEED.getKey().location());
    }
}