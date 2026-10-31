package com.tiomadre.foragersinsight.common.worldgen;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSlice;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import com.teamabnormals.blueprint.core.util.BiomeUtil;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.List;

public class FIBiomeSlices {
    public static final ResourceKey<ModdedBiomeSlice> DARK_WOODLANDS =
            ResourceKey.create(
                    BlueprintDataPackRegistries.MODDED_BIOME_SLICES,
                    ForagersInsight.rl("dark_woodlands")
            );

    public static void bootstrap(BootstapContext<ModdedBiomeSlice> context) {
        var biomes = context.lookup(Registries.BIOME);

        context.register(DARK_WOODLANDS, new ModdedBiomeSlice(
                100,
                new BiomeUtil.OverlayModdedBiomeProvider(List.of(
                        Pair.of(
                                HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST)),
                                new FixedBiomeSource(biomes.getOrThrow(FIBiomes.DARK_WOODLANDS))
                        )
                )),
                LevelStem.OVERWORLD
        ));
    }
}