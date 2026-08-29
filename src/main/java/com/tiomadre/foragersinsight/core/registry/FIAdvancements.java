package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.advancement.SimpleTrigger;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class FIAdvancements {



    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, ForagersInsight.MOD_ID);

    public static final Supplier<SimpleTrigger> SIMPLE_TRIGGER = TRIGGERS.register("simple_trigger", SimpleTrigger::new);




}