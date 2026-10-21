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

public class FixAnvilTrigger extends SimpleCriterionTrigger<FixAnvilTrigger.FixAnvilTriggerInstance> {


    @Override
    public Codec<FixAnvilTrigger.FixAnvilTriggerInstance> codec() {
        return FixAnvilTrigger.FixAnvilTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, FixAnvilTrigger.FixAnvilTriggerInstance::test);
    }

    public static record FixAnvilTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<FixAnvilTrigger.FixAnvilTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(FixAnvilTrigger.FixAnvilTriggerInstance::player))
                        .apply(builder, FixAnvilTrigger.FixAnvilTriggerInstance::new)
        );

        public static Criterion<FixAnvilTrigger.FixAnvilTriggerInstance> fixAnvil() {
            return FIAdvancements.FIX_ANVIL_TRIGGER.get().createCriterion(
                    new FixAnvilTrigger.FixAnvilTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}