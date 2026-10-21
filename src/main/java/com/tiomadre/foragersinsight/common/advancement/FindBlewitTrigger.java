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

public class FindBlewitTrigger extends SimpleCriterionTrigger<FindBlewitTrigger.FindBlewitTriggerInstance>{


    @Override
    public Codec<FindBlewitTrigger.FindBlewitTriggerInstance> codec() {
        return FindBlewitTrigger.FindBlewitTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, FindBlewitTrigger.FindBlewitTriggerInstance::test);
    }

    public static record FindBlewitTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<FindBlewitTrigger.FindBlewitTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(FindBlewitTrigger.FindBlewitTriggerInstance::player))
                        .apply(builder, FindBlewitTrigger.FindBlewitTriggerInstance::new)
        );

        public static Criterion<FindBlewitTrigger.FindBlewitTriggerInstance> findBlewit() {
            return FIAdvancements.FIND_BLEWIT_TRIGGER.get().createCriterion(
                    new FindBlewitTrigger.FindBlewitTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}
