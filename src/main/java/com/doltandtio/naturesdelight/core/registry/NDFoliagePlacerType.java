package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.common.worldgen.trees.foliage.AppleTreeFoliagePlacer;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NDFoliagePlacerType {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE =
            DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, NaturesDelight.MOD_ID);

    public static final RegistryObject<FoliagePlacerType<AppleTreeFoliagePlacer>> APPLE_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPE.register("apple_tree_foliage_placer", () ->
                    new FoliagePlacerType<>(AppleTreeFoliagePlacer.CODEC));
}
