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

public class CrushItemTrigger extends SimpleCriterionTrigger<CrushItemTrigger.CrushItemTriggerInstance> {


    @Override
    public Codec<CrushItemTrigger.CrushItemTriggerInstance> codec() {
        return CrushItemTrigger.CrushItemTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, CrushItemTrigger.CrushItemTriggerInstance::test);
    }

    public static record CrushItemTriggerInstance(
            Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<CrushItemTrigger.CrushItemTriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CrushItemTrigger.CrushItemTriggerInstance::player))
                        .apply(builder, CrushItemTrigger.CrushItemTriggerInstance::new)
        );

        public static Criterion<CrushItemTrigger.CrushItemTriggerInstance> crushItem() {
            return FIAdvancements.CRUSH_ITEM_TRIGGER.get().createCriterion(
                    new CrushItemTrigger.CrushItemTriggerInstance(Optional.empty())
            );
        }


        public boolean test() {
            return true;
        }

    }
}
