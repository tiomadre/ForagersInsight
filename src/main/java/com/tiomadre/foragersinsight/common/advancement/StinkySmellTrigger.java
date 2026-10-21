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

public class StinkySmellTrigger  extends SimpleCriterionTrigger<StinkySmellTrigger.StinkySmellTriggerInstance> {


    @Override
    public Codec<StinkySmellTrigger.StinkySmellTriggerInstance> codec() {
        return StinkySmellTrigger.StinkySmellTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, StinkySmellTrigger.StinkySmellTriggerInstance::test);
    }

    public static record StinkySmellTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<StinkySmellTrigger.StinkySmellTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(StinkySmellTrigger.StinkySmellTriggerInstance::player))
                        .apply(builder, StinkySmellTrigger.StinkySmellTriggerInstance::new)
        );

        public static Criterion<StinkySmellTrigger.StinkySmellTriggerInstance> stinkySmell() {
            return FIAdvancements.STINKY_SMELL_TRIGGER.get().createCriterion(
                    new StinkySmellTrigger.StinkySmellTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}