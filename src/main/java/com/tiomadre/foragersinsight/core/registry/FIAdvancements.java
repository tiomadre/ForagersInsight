package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.advancement.*;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class FIAdvancements {



    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, ForagersInsight.MOD_ID);

    public static final Supplier<BrushLitterTrigger> BRUSH_LITTER_TRIGGER = TRIGGERS.register("brush_litter", BrushLitterTrigger::new);

    public static final Supplier<FindBlewitTrigger> FIND_BLEWIT_TRIGGER = TRIGGERS.register("find_blewit", FindBlewitTrigger::new);

    public static final Supplier<LightDiffuserTrigger> LIGHT_DIFFUSER_TRIGGER = TRIGGERS.register("light_diffuser", LightDiffuserTrigger::new);

    public static final Supplier<StinkySmellTrigger> STINKY_SMELL_TRIGGER = TRIGGERS.register("stinky_smell", StinkySmellTrigger::new);

    public static final Supplier<HarvestBountyTrigger> HARVEST_BOUNTY_TRIGGER = TRIGGERS.register("harvest_bounty", HarvestBountyTrigger::new);

    public static final Supplier<ShearBountyTrigger> SHEAR_BOUNTY_TRIGGER = TRIGGERS.register("shear_bounty", ShearBountyTrigger::new);

    public static final Supplier<CrushItemTrigger> CRUSH_ITEM_TRIGGER = TRIGGERS.register("crush_item", CrushItemTrigger::new);

    public static final Supplier<CrackBlockTrigger> CRACK_BLOCK_TRIGGER = TRIGGERS.register("crack_block", CrackBlockTrigger::new);

    public static final Supplier<FixAnvilTrigger> FIX_ANVIL_TRIGGER = TRIGGERS.register("fix_anvil", FixAnvilTrigger::new);

}