package com.tiomadre.foragersinsight.data.server;

import com.tiomadre.foragersinsight.common.advancement.*;
import com.tiomadre.foragersinsight.common.utility.TextUtils;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.function.Consumer;

public class FIAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        AdvancementHolder foragersInsight = Advancement.Builder.advancement()
                .display(FIItems.HANDBASKET.get(),
                        TextUtils.advancement("root.title"),
                        TextUtils.advancement("root.description"),
                        ResourceLocation.parse("foragersinsight:textures/gui/advancements/backgrounds/foraging.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("foragers_insight", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FITags.ItemTag.FORAGERS_INSIGHT_ITEMS)))
                .save(saver,  getNameId( "main/root"));


        AdvancementHolder springCleaning = getAdvancement(foragersInsight, Items.BRUSH, "spring_cleaning",AdvancementType.TASK, true, true, false)
                .addCriterion("spring_cleaning",InventoryChangeTrigger.TriggerInstance.hasItems(Items.BRUSH)
                ).save(saver, getNameId("main/spring_cleaning"));

                AdvancementHolder brushItOff = getAdvancement(springCleaning, FIBlocks.SUSPICIOUS_LEAF_LITTER.get(), "brush_it_off",AdvancementType.TASK, true, true, false)
                .addCriterion("brush_it_off", BrushLitterTrigger.BrushLitterTriggerInstance.brushLitter())
                 .save(saver, getNameId("main/brush_it_off"));

        AdvancementHolder rareFind = getAdvancement(springCleaning, FIItems.BLEWIT_MUSHROOM.get(), "rare_find",AdvancementType.GOAL, true, true, false)
                .addCriterion("rare_find", FindBlewitTrigger.FindBlewitTriggerInstance.findBlewit())
                .save(saver, getNameId("main/rare_find"));

        AdvancementHolder wildFlowers = getAdvancement(foragersInsight, FIItems.BLEWIT_MUSHROOM.get(), "wild_flowers",AdvancementType.TASK, true, true, false)
                .addCriterion("wild_flowers",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FITags.ItemTag.WILD_FLOWER_DROPS))
                ).save(saver, getNameId("main/wild_flowers"));

        AdvancementHolder petalToTheMetal= getAdvancement(wildFlowers, Items.ROSE_BUSH, "petal_to_the_metal", AdvancementType.GOAL, true, true, false)
                .addCriterion("rose_petals", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.ROSE_PETALS.get()))
                .addCriterion("roselle_petals", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.ROSELLE_PETALS.get()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, getNameId("main/petal_to_the_metal"));

        AdvancementHolder scentsational = getAdvancement(wildFlowers, FIBlocks.DIFFUSER.get(), "scentsational",AdvancementType.TASK, true, true, false)
                .addCriterion("scentsational", LightDiffuserTrigger.LightDiffuserTriggerInstance.lightDiffuser())
                .save(saver, getNameId("main/scentsational"));

        AdvancementHolder stinkySituation = getAdvancement(scentsational, ModBlocks.ORGANIC_COMPOST.get(), "stinky_situation",AdvancementType.CHALLENGE, true, true, false)
                .addCriterion("stinky_situation", StinkySmellTrigger.StinkySmellTriggerInstance.stinkySmell())
                .save(saver, getNameId("main/stinky_situation"));

        AdvancementHolder givingTrees = getAdvancement(foragersInsight, Items.APPLE, "giving_trees", AdvancementType.TASK, true, true, false)
                .addCriterion("giving_trees", HarvestBountyTrigger.HarvestBountyTriggerInstance.havestBounty())
                .save(saver, getNameId("main/giving_trees"));

        AdvancementHolder shearingIsCaring = getAdvancement(givingTrees, FIItems.FLINT_SHEARS, "shear",AdvancementType.TASK, true, true, false)
                .addCriterion("shear", ShearBountyTrigger.ShearBountyTriggerInstance.shearBounty())
                .save(saver, getNameId("main/shearing_is_caring"));

        AdvancementHolder tapThat = getAdvancement(givingTrees, FIItems.TAPPER, "tap_that",AdvancementType.TASK, true, true, false)
                .addCriterion("tap_that", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.TAPPER))
                .save(saver, getNameId("main/tap_that"));

        AdvancementHolder birchPlease = getAdvancement(tapThat, FIItems.BIRCH_SAP_BUCKET, "birch_please", AdvancementType.TASK, true, true, false)
                .addCriterion("birch_please", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.BIRCH_SAP_BUCKET))
                .save(saver, getNameId("main/birch_please"));

        AdvancementHolder stopHammerTime = getAdvancement(foragersInsight, FIItems.FLINT_MALLET,"stop_hammer_time", AdvancementType.TASK, true, true, false)
                .addCriterion("stop_hammer_time", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FITags.ItemTag.MALLETS)))
                .save(saver, getNameId("main/stop_hammer_time"));

        AdvancementHolder willItCrush = getAdvancement(stopHammerTime, FIItems.WHEAT_FLOUR, "will_it_crush", AdvancementType.TASK, true, true, false)
                .addCriterion("will_it_crush", CrushItemTrigger.CrushItemTriggerInstance.crushItem())
                .save(saver, getNameId("main/will_it_crush"));

        AdvancementHolder crackIt = getAdvancement(stopHammerTime, Blocks.CRACKED_STONE_BRICKS, "crack_it", AdvancementType.TASK, true, true,false)
                .addCriterion("crack_brick", CrackBlockTrigger.CrackBlockTriggerInstance.crackBlock())
                .save(saver, getNameId("main/crack_it"));
        AdvancementHolder fixIt = getAdvancement(crackIt, Blocks.ANVIL, "uh_fix_it", AdvancementType.CHALLENGE, true, true, false)
                .addCriterion("uh_fix_it", FixAnvilTrigger.FixAnvilTriggerInstance.fixAnvil())
                .save(saver, getNameId("main/uh_fix_it"));

        AdvancementHolder tasteTheRainbowMotha= getAdvancement(foragersInsight, FIItems.SLICE_OF_RAINBOW_SANDWICH, "taste_the_rainbow_motha", AdvancementType.GOAL, true, true, false)
                .addCriterion("taste_the_rainbow_motha", ConsumeItemTrigger.TriggerInstance.usedItem(FIItems.SLICE_OF_RAINBOW_SANDWICH))
                .save(saver, getNameId("main/taste_the_rainbow_motha"));
    }




    protected static Advancement.Builder getAdvancement(AdvancementHolder parent, ItemLike display, String name, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
                TextUtils.advancement(name + ".title"),
                TextUtils.advancement(name + ".description"),
                null, frame, showToast, announceToChat, hidden);
    }

    private String getNameId(String id) {
        return ForagersInsight.MOD_ID + ":" + id;
    }
}

