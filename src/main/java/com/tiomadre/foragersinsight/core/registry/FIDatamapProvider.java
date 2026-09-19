package com.tiomadre.foragersinsight.core.registry;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;


import static com.tiomadre.foragersinsight.core.registry.FIBlocks.*;
import static com.tiomadre.foragersinsight.core.registry.FIItems.*;

public class FIDatamapProvider extends DataMapProvider {
    public FIDatamapProvider(PackOutput output, CompletableFuture<Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void gather(Provider provider) {
        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                // Leaves and Saplings
                .add(BOUNTIFUL_OAK_LEAVES.getId(), new Compostable(0.3f), false)
                .add(BOUNTIFUL_DARK_OAK_LEAVES.getId(), new Compostable(0.3f), false)
                .add(BOUNTIFUL_SPRUCE_LEAVES.getId(), new Compostable(0.3f), false)
                .add(LILAC_LEAVES.getId(), new Compostable(0.3f), false)
                .add(BLOSSOMING_LILAC_LEAVES.getId(), new Compostable(0.3f), false)
                // Storage
                .add(APPLE_CRATE.getId(), new Compostable(1.0f), false)
                .add(BLACK_ACORN_SACK.getId(), new Compostable(1.0f), false)
                .add(BLEWIT_CRATE.getId(), new Compostable(1.0f), false)
                .add(DANDELION_ROOT_SACK.getId(), new Compostable(1.0f), false)
                .add(LILAC_BLOOM_CRATE.getId(), new Compostable(1.0f), false)
                .add(POPPY_SEEDS_SACK.getId(), new Compostable(1.0f), false)
                .add(ROSE_HIP_SACK.getId(), new Compostable(1.0f), false)
                .add(ROSELLE_CALYX_SACK.getId(), new Compostable(1.0f), false)
                .add(SPRUCE_TIPS_SACK.getId(), new Compostable(1.0f), false)
                //Decorative
                // Flowers & Petals
                .add(ROSELLE_BUSH.getId(), new Compostable(0.4f), false)
                .add(TALL_BEACH_ROSE_BUSH.getId(), new Compostable(0.4f), false)
                .add(STOUT_BEACH_ROSE_BUSH.getId(), new Compostable(0.3f), false)
                .add(ROSE_PETALS.getId(), new Compostable(0.2f), false)
                .add(ROSELLE_PETALS.getId(), new Compostable(0.2f), false)
                //Foliage Mats
                //Scattered
                .add(SCATTERED_ROSE_PETAL_MAT.getId(), new Compostable(0.35f), false)
                .add(SCATTERED_ROSELLE_PETAL_MAT.getId(), new Compostable(0.4f), false)
                .add(SCATTERED_LILAC_BLOOM_MAT.getId(), new Compostable(0.4f), false)
                .add(SCATTERED_SPRUCE_TIP_MAT.getId(), new Compostable(0.4f), false)
                .add(SCATTERED_STRAW_MAT.getId(), new Compostable(0.4f), false)
                //Dense
                .add(DENSE_ROSE_PETAL_MAT.getId(), new Compostable(0.6f), false)
                .add(DENSE_ROSELLE_PETAL_MAT.getId(), new Compostable(0.6f), false)
                .add(DENSE_LILAC_BLOOM_MAT.getId(), new Compostable(0.6f), false)
                .add(DENSE_SPRUCE_TIP_MAT.getId(), new Compostable(0.6f), false)
                .add(DENSE_STRAW_MAT.getId(), new Compostable(0.6f), false)
                //Crops
                .add(APPLE_SLICE.getId(), new Compostable(0.3f), false)
                .add(BLACK_ACORN.getId(), new Compostable(0.3f), false)
                .add(DANDELION_ROOT.getId(), new Compostable(0.3f), false)
                .add(LILAC_BLOOM.getId(), new Compostable(0.3f), false)
                .add(SPRUCE_TIPS.getId(), new Compostable(0.3f), false)
                .add(POPPY_SEEDS.getId(), new Compostable(0.3f), false)
                .add(ROSE_HIP.getId(), new Compostable(0.3f), false)
                .add(ROSELLE_CALYX.getId(), new Compostable(0.3f), false)
                .add(FIItems.BLEWIT_MUSHROOM.getId(), new Compostable(0.4f), false)
                //Food
                .add(ACORN_COOKIE.getId(), new Compostable(0.85f), false)
                .add(BLEWIT_BITES.getId(), new Compostable(0.85f), false)
                .add(ROSE_COOKIE.getId(), new Compostable(0.85f), false)
                .add(CANDIED_CALYCES.getId(), new Compostable(0.85f), false)
                .add(APPLE_DIPPERS.getId(), new Compostable(0.85f), false)
                .add(KELP_WRAP.getId(), new Compostable(0.85f), false)
                .add(JAMMY_BREAKFAST_SANDWICH.getId(), new Compostable(0.85f), false)
                .add(CREAMY_SALMON_BAGEL.getId(), new Compostable(0.85f), false)
                .add(WHEAT_FLOUR.getId(), new Compostable(0.3f), false)
                .add(ACORN_MEAL.getId(), new Compostable(0.3f), false)
                .add(POPPY_SEED_PASTE.getId(), new Compostable(0.3f), false)
                .add(COCOA_POWDER.getId(), new Compostable(0.3f), false);


}
}