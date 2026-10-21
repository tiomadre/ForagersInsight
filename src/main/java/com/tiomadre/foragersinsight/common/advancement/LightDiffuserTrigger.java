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

public class LightDiffuserTrigger extends SimpleCriterionTrigger<LightDiffuserTrigger.LightDiffuserTriggerInstance> {


    @Override
    public Codec<LightDiffuserTrigger.LightDiffuserTriggerInstance> codec() {
        return LightDiffuserTrigger.LightDiffuserTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, LightDiffuserTrigger.LightDiffuserTriggerInstance::test);
    }

    public static record LightDiffuserTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<LightDiffuserTrigger.LightDiffuserTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(LightDiffuserTrigger.LightDiffuserTriggerInstance::player))
                        .apply(builder, LightDiffuserTrigger.LightDiffuserTriggerInstance::new)
        );

        public static Criterion<LightDiffuserTrigger.LightDiffuserTriggerInstance> lightDiffuser() {
            return FIAdvancements.LIGHT_DIFFUSER_TRIGGER.get().createCriterion(
                    new LightDiffuserTrigger.LightDiffuserTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}