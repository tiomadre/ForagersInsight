package com.doltandtio.foragersinsight.common.worldgen;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class FIBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_APPLE_TREES = registerKey("add_apple_trees");
    public static final ResourceKey<BiomeModifier> ADD_ACORN_TREES = registerKey("add_acorn_trees");
    public static final ResourceKey<BiomeModifier> ADD_SPRUCE_TIP_TREES = registerKey("add_spruce_tip_trees");
    public static final ResourceKey<BiomeModifier> ADD_SAPPY_BIRCH_TREES = registerKey("add_sappy_birch_trees");

    public static void bootstap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_APPLE_TREES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FITags.BiomeTag.HAS_APPLE_TREES),
                HolderSet.direct(placedFeatures.getOrThrow(FIPlacedFeatures.APPLE_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );

        context.register(ADD_ACORN_TREES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FITags.BiomeTag.HAS_ACORN_TREES),
                HolderSet.direct(placedFeatures.getOrThrow(FIPlacedFeatures.ACORN_DARK_OAK_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );

        context.register(ADD_SPRUCE_TIP_TREES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FITags.BiomeTag.HAS_SPRUCE_TIP_TREES),
                HolderSet.direct(placedFeatures.getOrThrow(FIPlacedFeatures.SPRUCE_TIP_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );

        context.register(ADD_SAPPY_BIRCH_TREES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(FITags.BiomeTag.HAS_SAPPY_BIRCH_TREES),
                HolderSet.direct(placedFeatures.getOrThrow(FIPlacedFeatures.SAPPY_BIRCH_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ForagersInsight.rl(name));
    }


}