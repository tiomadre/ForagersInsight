package com.tiomadre.foragersinsight.data.server;

import com.teamabnormals.blueprint.core.data.server.BlueprintDatapackBuiltinEntriesProvider;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import com.tiomadre.foragersinsight.common.worldgen.*;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


import java.util.Set;
import java.util.concurrent.CompletableFuture;


public class FIWorldgen extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, FIBiomes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, FIConfiguredFeatures::bootstap)
            .add(Registries.PLACED_FEATURE, FIPlacedFeatures::bootstap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FIBiomeModifiers::bootstap)
            .add(Registries.BIOME, BlueprintDatapackBuiltinEntriesProvider::bootstrapBiomes)
            .add(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, FIBiomeSlices::bootstrapSlices);;



    public FIWorldgen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of(ForagersInsight.MOD_ID));
    }
}