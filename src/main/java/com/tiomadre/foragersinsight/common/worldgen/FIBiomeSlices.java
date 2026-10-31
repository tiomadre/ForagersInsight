package com.tiomadre.foragersinsight.common.worldgen;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSlice;
import com.teamabnormals.blueprint.core.registry.BlueprintBiomes;
import com.teamabnormals.blueprint.core.util.BiomeUtil;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;

import java.util.ArrayList;
import java.util.List;

public class FIBiomeSlices {
    public static final ResourceKey<Biome> DARK_WOODLANDS_AREA = FIBiomes.registerKey("dark_woodlands");

    public static void bootstrapSlices(BootstrapContext<ModdedBiomeSlice> context) {

    }


}
