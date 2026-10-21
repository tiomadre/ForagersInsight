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

public class HarvestBountyTrigger extends SimpleCriterionTrigger<HarvestBountyTrigger.HarvestBountyTriggerInstance> {


    @Override
    public Codec<HarvestBountyTrigger.HarvestBountyTriggerInstance> codec() {
        return HarvestBountyTrigger.HarvestBountyTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, HarvestBountyTrigger.HarvestBountyTriggerInstance::test);
    }

    public static record HarvestBountyTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<HarvestBountyTrigger.HarvestBountyTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(HarvestBountyTrigger.HarvestBountyTriggerInstance::player))
                        .apply(builder, HarvestBountyTrigger.HarvestBountyTriggerInstance::new)
        );

        public static Criterion<HarvestBountyTrigger.HarvestBountyTriggerInstance> havestBounty() {
            return FIAdvancements.HARVEST_BOUNTY_TRIGGER.get().createCriterion(
                    new HarvestBountyTrigger.HarvestBountyTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}