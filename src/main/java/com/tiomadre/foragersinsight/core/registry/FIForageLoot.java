package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.block.SuspiciousLitterBlock;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.data.loot.LootTableSubProvider;

import java.util.function.BiConsumer;

import static com.tiomadre.foragersinsight.core.registry.FIItems.*;

public final class FIForageLoot {

    public static final ResourceKey<LootTable> SUSPICIOUS_LEAF_LITTER_OAK = ResourceKey.create(Registries.LOOT_TABLE,ForagersInsight.rl("archaeology/suspicious_leaf_litter/oak"));
    public static final ResourceKey<LootTable> SUSPICIOUS_LEAF_LITTER_BIRCH = ResourceKey.create(Registries.LOOT_TABLE,ForagersInsight.rl("archaeology/suspicious_leaf_litter/birch"));
    public static final ResourceKey<LootTable> SUSPICIOUS_LEAF_LITTER_SPRUCE = ResourceKey.create(Registries.LOOT_TABLE,ForagersInsight.rl("archaeology/suspicious_leaf_litter/spruce"));
    public static final ResourceKey<LootTable> SUSPICIOUS_LEAF_LITTER_DARK_OAK = ResourceKey.create(Registries.LOOT_TABLE,ForagersInsight.rl("archaeology/suspicious_leaf_litter/dark_oak"));
    public static final ResourceKey<LootTable> SUSPICIOUS_LEAF_LITTER_FLOWER = ResourceKey.create(Registries.LOOT_TABLE,ForagersInsight.rl("archaeology/suspicious_leaf_litter/flower"));

    private FIForageLoot() {
    }

    public static ResourceLocation suspiciousLeafLitterByFoliage(SuspiciousLitterBlock.FoliageType foliageType) {
        return switch (foliageType) {
            case BIRCH -> ForagersInsight.rl("archaeology/suspicious_leaf_litter/birch");
            case SPRUCE -> ForagersInsight.rl("archaeology/suspicious_leaf_litter/spruce");
            case DARK_OAK -> ForagersInsight.rl("archaeology/suspicious_leaf_litter/dark_oak");
            case FLOWER -> ForagersInsight.rl("archaeology/suspicious_leaf_litter/flower");
            case OAK -> ForagersInsight.rl("archaeology/suspicious_leaf_litter/oak");
        };
    }



}
