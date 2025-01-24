package com.doltandtio.naturesdelight.data.server;

import com.doltandtio.naturesdelight.common.worldgen.NDBiomeModifiers;
import com.doltandtio.naturesdelight.common.worldgen.NDConfiguredFeatures;
import com.doltandtio.naturesdelight.common.worldgen.NDPlacedFeatures;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModPlacementModifiers;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NDWorldgen extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, NDConfiguredFeatures::bootstap)
            .add(Registries.PLACED_FEATURE, NDPlacedFeatures::bootstap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, NDBiomeModifiers::bootstap);


    public NDWorldgen(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), BUILDER, Set.of(NaturesDelight.MOD_ID));
    }
}
