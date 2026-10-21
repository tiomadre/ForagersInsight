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

public class ShearBountyTrigger extends SimpleCriterionTrigger<ShearBountyTrigger.ShearBountyTriggerInstance> {


    @Override
    public Codec<ShearBountyTrigger.ShearBountyTriggerInstance> codec() {
        return ShearBountyTrigger.ShearBountyTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, ShearBountyTrigger.ShearBountyTriggerInstance::test);
    }

    public static record ShearBountyTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<ShearBountyTrigger.ShearBountyTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(ShearBountyTrigger.ShearBountyTriggerInstance::player))
                        .apply(builder, ShearBountyTrigger.ShearBountyTriggerInstance::new)
        );

        public static Criterion<ShearBountyTrigger.ShearBountyTriggerInstance> shearBounty() {
            return FIAdvancements.SHEAR_BOUNTY_TRIGGER.get().createCriterion(
                    new ShearBountyTrigger.ShearBountyTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}
