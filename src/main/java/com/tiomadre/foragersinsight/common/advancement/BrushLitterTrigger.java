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

public class BrushLitterTrigger extends SimpleCriterionTrigger<BrushLitterTrigger.BrushLitterTriggerInstance>{


    @Override
    public Codec<BrushLitterTriggerInstance> codec() {
        return BrushLitterTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, BrushLitterTriggerInstance::test);
    }

    public static record BrushLitterTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<BrushLitterTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(BrushLitterTriggerInstance::player))
                        .apply(builder, BrushLitterTriggerInstance::new)
        );

        public static Criterion<BrushLitterTriggerInstance> brushLitter() {
            return FIAdvancements.BRUSH_LITTER_TRIGGER.get().createCriterion(
                    new BrushLitterTriggerInstance(Optional.empty())
            );
        }
        

        public boolean test() {
            return true;
        }

    }
}