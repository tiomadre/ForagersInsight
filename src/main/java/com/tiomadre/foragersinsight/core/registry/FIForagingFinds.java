package com.tiomadre.foragersinsight.core.registry;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

import static com.tiomadre.foragersinsight.core.registry.FIItems.*;
import static com.tiomadre.foragersinsight.core.registry.FIItems.BLACK_ACORN;
import static com.tiomadre.foragersinsight.core.registry.FIItems.BLEWIT_MUSHROOM;
import static com.tiomadre.foragersinsight.core.registry.FIItems.DANDELION_ROOT;
import static com.tiomadre.foragersinsight.core.registry.FIItems.POPPY_SEEDS;
import static com.tiomadre.foragersinsight.core.registry.FIItems.ROSE_HIP;

public class FIForagingFinds implements LootTableSubProvider {

    public FIForagingFinds(HolderLookup.Provider lookupProvider) {
        super();
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(FIForageLoot.SUSPICIOUS_LEAF_LITTER_OAK, suspiciousLitterOak());
        output.accept(FIForageLoot.SUSPICIOUS_LEAF_LITTER_BIRCH, suspiciousLitterBirch());
        output.accept(FIForageLoot.SUSPICIOUS_LEAF_LITTER_SPRUCE, suspiciousLitterSpruce());
        output.accept(FIForageLoot.SUSPICIOUS_LEAF_LITTER_DARK_OAK, suspiciousLitterDarkOak());
        output.accept(FIForageLoot.SUSPICIOUS_LEAF_LITTER_FLOWER, suspiciousLitterFlower());

    }

    private static LootTable.Builder suspiciousLitterOak() {
        return baseSuspiciousLitterBuilder()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(7).setQuality(-1))
                        .add(LootItem.lootTableItem(ROSE_HIP.get()).setWeight(6))
                        .add(LootItem.lootTableItem(Items.EGG).setWeight(4).setQuality(1)));
    }

    private static LootTable.Builder suspiciousLitterFlower() {
        return baseSuspiciousLitterBuilder()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(LILAC_BLOOM.get()).setWeight(7).setQuality(-1))
                        .add(LootItem.lootTableItem(ROSE_HIP.get()).setWeight(6))
                        .add(LootItem.lootTableItem(Items.HONEYCOMB).setWeight(5).setQuality(1)));
    }

    private static LootTable.Builder suspiciousLitterBirch() {
        return baseSuspiciousLitterBuilder()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ROSE_HIP.get()).setWeight(6))
                        .add(LootItem.lootTableItem(FIBlocks.TINDER_CONK.get()).setWeight(4))
                );
    }

    private static LootTable.Builder suspiciousLitterSpruce() {
        return baseSuspiciousLitterBuilder()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FERN).setWeight(8).setQuality(-1))
                        .add(LootItem.lootTableItem(SPRUCE_TIPS.get()).setWeight(7))
                        .add(LootItem.lootTableItem(Items.SWEET_BERRIES).setWeight(6)));
    }

    private static LootTable.Builder suspiciousLitterDarkOak() {
        return baseSuspiciousLitterBuilder()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BLACK_ACORN.get()).setWeight(7))
                        .add(LootItem.lootTableItem(ROSE_HIP.get()).setWeight(6))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(5).setQuality(1)));
    }

    private static LootTable.Builder baseSuspiciousLitterBuilder() {
        return LootTable.lootTable()
                .setParamSet(LootContextParamSets.ARCHAEOLOGY)
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(8).setQuality(-1))
                        .add(LootItem.lootTableItem(vectorwing.farmersdelight.common.registry.ModItems.TREE_BARK.get()).setWeight(7))
                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(6))
                        .add(LootItem.lootTableItem(DANDELION_ROOT.get()).setWeight(6))
                        .add(LootItem.lootTableItem(POPPY_SEEDS.get()).setWeight(6))
                        .add(LootItem.lootTableItem(Items.RED_MUSHROOM).setWeight(5).setQuality(1))
                        .add(LootItem.lootTableItem(Items.BROWN_MUSHROOM).setWeight(5).setQuality(1))
                        .add(LootItem.lootTableItem(BLEWIT_MUSHROOM.get()).setWeight(2).setQuality(2)));
    }


}