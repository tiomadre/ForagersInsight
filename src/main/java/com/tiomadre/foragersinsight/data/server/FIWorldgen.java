package com.tiomadre.foragersinsight.data.server;

import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import com.tiomadre.foragersinsight.common.worldgen.*;
import com.tiomadre.foragersinsight.common.worldgen.FIBiomeSlices;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class FIWorldgen extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, FIBiomes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, FIConfiguredFeatures::bootstap)
            .add(Registries.PLACED_FEATURE, FIPlacedFeatures::bootstap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, FIBiomeModifiers::bootstap)
            .add(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, FIBiomeSlices::bootstrap);

    public FIWorldgen(GatherDataEvent event) {
        super(
                event.getGenerator().getPackOutput(),
                event.getLookupProvider(),
                BUILDER,
                Set.of(ForagersInsight.MOD_ID)
        );
    }
}