package com.tiomadre.foragersinsight.core.registry;

import com.google.gson.JsonObject;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;


public final class FIAdvancementCriteria {
    public static final SimpleTrigger FIND_BLEWIT_MUSHROOM = register(new SimpleTrigger("find_blewit_mushroom"));
    public static final SimpleTrigger BRUSH_SUSPICIOUS_LITTER = register(new SimpleTrigger("brush_suspicious_litter"));
    public static final SimpleTrigger SCENTSATIONAL = register(new SimpleTrigger("scentsational"));
    public static final SimpleTrigger STINKY_SITUATION = register(new SimpleTrigger("stinky_situation"));
    public static final SimpleTrigger TAP_THAT = register(new SimpleTrigger("tap_that"));
    public static final SimpleTrigger BIRCH_PLEASE = register(new SimpleTrigger("birch_please"));
    public static final SimpleTrigger WILL_IT_CRUSH = register(new SimpleTrigger("will_it_crush"));
    public static final SimpleTrigger SHEAR_BOUNTIFUL_TREE = register(new SimpleTrigger("shear_bountiful_tree"));
    public static final SimpleTrigger PETAL_TO_THE_METAL = register(new SimpleTrigger("petal_to_the_metal"));
    public static final SimpleTrigger CRACK_IT = register(new SimpleTrigger("crack_it"));
    public static final SimpleTrigger UH_FIX_IT = register(new SimpleTrigger("uh_fix_it"));


    private FIAdvancementCriteria() {
    }

    private static <T extends CriterionTrigger<?>> T register(T trigger) {
        return CriteriaTriggers.register(trigger);
    }

    public static void register() {
    }



    public static class SimpleTrigger extends SimpleCriterionTrigger<SimpleTrigger.TriggerInstance> {
        private final ResourceLocation id;

        public SimpleTrigger(String id) {
            this.id = ForagersInsight.rl(id);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
            return new TriggerInstance(id, player);
        }

        public void trigger(ServerPlayer player) {
            this.trigger(player, TriggerInstance::matches);
        }

        public static class TriggerInstance extends AbstractCriterionTriggerInstance {
            public TriggerInstance(ResourceLocation id, ContextAwarePredicate player) {
                super(id, player);
            }

            public boolean matches() {
                return true;
            }
        }
    }
}