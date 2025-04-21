package com.doltandtio.foragersinsight.data.server;

import com.doltandtio.foragersinsight.common.worldgen.FIBiomeModifiers;
import com.doltandtio.foragersinsight.common.worldgen.FIConfiguredFeatures;
import com.doltandtio.foragersinsight.common.worldgen.FIPlacedFeatures;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class FIWorldgen extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, FIConfiguredFeatures::bootstap)
            .add(Registries.PLACED_FEATURE, FIPlacedFeatures::bootstap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, FIBiomeModifiers::bootstap);


    public FIWorldgen(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), BUILDER, Set.of(ForagersInsight.MOD_ID));
    }
}
