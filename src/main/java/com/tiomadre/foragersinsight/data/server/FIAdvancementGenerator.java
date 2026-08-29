package com.tiomadre.foragersinsight.data.server;

import com.tiomadre.foragersinsight.common.advancement.SimpleTrigger;
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
import net.minecraft.network.chat.Component;
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
                        Component.translatable("advancements.foragersinsight.adventure.foragers_insight.title"),
                        Component.translatable("advancements.foragersinsight.adventure.foragers_insight.description"),
                        ResourceLocation.parse("foragersinsight:textures/gui/advancements/backgrounds/foraging.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("has_foragers_insight_item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FITags.ItemTag.FORAGERS_INSIGHT_ITEMS)))
                .save(saver,  ForagersInsight.rl( "adventure/foragers_insight"),existingFileHelper);


        AdvancementHolder springCleaning = getAdvancement(foragersInsight, Items.BRUSH, "pick_up_brush",AdvancementType.TASK, true, true, false)
                .addCriterion("pick_up_brush",InventoryChangeTrigger.TriggerInstance.hasItems(Items.BRUSH)
                ).save(saver, getNameId("adventure/spring_cleaning"));
                AdvancementHolder brushItOff = getAdvancement(springCleaning, FIBlocks.SUSPICIOUS_LEAF_LITTER.get(), "brush_suspicious_litter",AdvancementType.TASK, true, true, false)
                .addCriterion("brush_suspicious_litter", SimpleTrigger.TriggerInstance.simple())
                 .save(saver, getNameId("adventure/brush_it_off"));

        AdvancementHolder rareFind = getAdvancement(springCleaning, FIItems.BLEWIT_MUSHROOM.get(), "find_blewit_mushroom",AdvancementType.GOAL, true, true, false)
                .addCriterion("find_blewit_mushroom",SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/rare_find"));

        AdvancementHolder wildFlowers = getAdvancement(foragersInsight, FIItems.BLEWIT_MUSHROOM.get(), "forage_wild_flower",AdvancementType.TASK, true, true, false)
                .addCriterion("forage_wild_flower",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FITags.ItemTag.WILD_FLOWER_DROPS))
                ).save(saver, getNameId("adventure/wild_flowers"));

        AdvancementHolder petalToTheMetal= getAdvancement(wildFlowers, Items.ROSE_BUSH, "has_petals", AdvancementType.GOAL, true, true, false)
                .addCriterion("rose_petals", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.ROSE_PETALS.get()))
                .addCriterion("roselle_petals", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.ROSELLE_PETALS.get()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, getNameId("adventure/petal_to_the_metal"));

        AdvancementHolder scentsational = getAdvancement(wildFlowers, FIBlocks.DIFFUSER.get(), "light_diffuser",AdvancementType.TASK, true, true, false)
                .addCriterion("light_diffuser",SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/scentsational"));

        AdvancementHolder stinkySituation = getAdvancement(scentsational, ModBlocks.ORGANIC_COMPOST.get(), "stinky_smell",AdvancementType.CHALLENGE, true, true, false)
                .addCriterion("stinky_smell",SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/stinky_situation"));

        AdvancementHolder givingTrees = getAdvancement(foragersInsight, Items.APPLE, "harvest_bountiful_tree", AdvancementType.TASK, true, true, false)
                .addCriterion("harvest_bountiful_tree", SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/giving_trees"));

        AdvancementHolder shearingIsCaring = getAdvancement(givingTrees, FIItems.FLINT_SHEARS, "shear_bountiful_tree",AdvancementType.TASK, true, true, false)
                .addCriterion("shear_bountiful_tree", SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/shearing_is_caring"));

        AdvancementHolder tapThat = getAdvancement(givingTrees, FIItems.TAPPER, "has_tapper",AdvancementType.TASK, true, true, false)
                .addCriterion("has_tapper", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.TAPPER))
                .save(saver, getNameId("adventure/tap_that"));

        AdvancementHolder birchPlease = getAdvancement(tapThat, FIItems.BIRCH_SAP_BUCKET, "has_birch_sap_bucket", AdvancementType.TASK, true, true, false)
                .addCriterion("has_birch_sap_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(FIItems.BIRCH_SAP_BUCKET))
                .save(saver, getNameId("adventure/birch_please"));

        AdvancementHolder stopHammerTime = getAdvancement(foragersInsight, FIItems.FLINT_MALLET,"pick_up_mallet", AdvancementType.TASK, true, true, false)
                .addCriterion("pick_up_mallet", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FITags.ItemTag.MALLETS)))
                .save(saver, getNameId("adventure/stop_hammer_time"));

        AdvancementHolder willItCrush = getAdvancement(stopHammerTime, FIItems.WHEAT_FLOUR, "crush_item", AdvancementType.TASK, true, true, false)
                .addCriterion("crush_item", SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/will_it_crush"));

        AdvancementHolder crackIt = getAdvancement(stopHammerTime, Blocks.CRACKED_STONE_BRICKS, "crack_brick", AdvancementType.TASK, true, true,false)
                .addCriterion("crack_brick", SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/crack_it"));
        AdvancementHolder fixIt = getAdvancement(crackIt, Blocks.ANVIL, "fix_anvil", AdvancementType.CHALLENGE, true, true, false)
                .addCriterion("fix_anvil", SimpleTrigger.TriggerInstance.simple())
                .save(saver, getNameId("adventure/uh_fix_it"));

        AdvancementHolder tasteTheRainbowMotha= getAdvancement(foragersInsight, FIItems.SLICE_OF_RAINBOW_SANDWICH, "eat_slice_of_rainbow_sandwich", AdvancementType.GOAL, true, true, false)
                .addCriterion("eat_slice_of_rainbow_sandwich", ConsumeItemTrigger.TriggerInstance.usedItem(FIItems.SLICE_OF_RAINBOW_SANDWICH))
                .save(saver, getNameId("adventure/taste_the_rainbow_motha"));
    }




    protected static Advancement.Builder getAdvancement(AdvancementHolder parent, ItemLike display, String name, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
                Component.translatable(name + ".title"),
                Component.translatable(name + ".description"),
                null, frame, showToast, announceToChat, hidden);
    }

    private String getNameId(String id) {
        return ForagersInsight.MOD_ID + ":" + id;
    }
}

