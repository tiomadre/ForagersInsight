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

public class CrackBlockTrigger extends SimpleCriterionTrigger<CrackBlockTrigger.CrackBlockTriggerInstance> {


    @Override
    public Codec<CrackBlockTrigger.CrackBlockTriggerInstance> codec() {
        return CrackBlockTrigger.CrackBlockTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, CrackBlockTrigger.CrackBlockTriggerInstance::test);
    }

    public static record CrackBlockTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<CrackBlockTrigger.CrackBlockTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CrackBlockTrigger.CrackBlockTriggerInstance::player))
                        .apply(builder, CrackBlockTrigger.CrackBlockTriggerInstance::new)
        );

        public static Criterion<CrackBlockTrigger.CrackBlockTriggerInstance> crackBlock() {
            return FIAdvancements.CRACK_BLOCK_TRIGGER.get().createCriterion(
                    new CrackBlockTrigger.CrackBlockTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}