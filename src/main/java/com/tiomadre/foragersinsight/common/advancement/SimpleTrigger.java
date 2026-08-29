package com.tiomadre.foragersinsight.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.core.registry.FIAdvancements;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class SimpleTrigger extends SimpleCriterionTrigger<SimpleTrigger.TriggerInstance>{


    @Override
    public Codec<TriggerInstance> codec() {
        return SimpleTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, TriggerInstance::test);
    }

    public static record TriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<SimpleTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SimpleTrigger.TriggerInstance::player))
                        .apply(builder, SimpleTrigger.TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> simple() {
            return FIAdvancements.SIMPLE_TRIGGER.get().createCriterion(
                    new SimpleTrigger.TriggerInstance(Optional.empty())
            );
        }

        public boolean test() {
            return true;
        }

    }
}