package com.doltandtio.naturesdelight.common.worldgen;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.data.server.tags.NDBiomeTags;
import com.doltandtio.naturesdelight.data.server.tags.NDTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class NDBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_APPLE_TREES = registerKey("add_apple_trees");

    public static void bootstap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_APPLE_TREES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(NDTags.BiomeTag.HAS_APPLE_TREES),
                HolderSet.direct(placedFeatures.getOrThrow(NDPlacedFeatures.APPLE_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );

    }
    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, NaturesDelight.rl(name));
    }


}
