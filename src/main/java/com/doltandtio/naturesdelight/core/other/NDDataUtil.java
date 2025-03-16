package com.doltandtio.naturesdelight.core.other;

import com.doltandtio.naturesdelight.core.registry.NDItems;
import com.teamabnormals.blueprint.core.util.DataUtil;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.*;

public class NDDataUtil {
    public static void registerCompat() {
        registerCompostable();
    }

    private static void registerCompostable() {
       //Leaves
        DataUtil.registerCompostable(BOUNTIFUL_OAK_LEAVES.get(), 0.3f);
        DataUtil.registerCompostable(BOUNTIFUL_OAK_SAPLING.get(), 0.3f);
        DataUtil.registerCompostable(BOUNTIFUL_DARK_OAK_LEAVES.get(), 0.3f);
        DataUtil.registerCompostable(BOUNTIFUL_DARK_OAK_SAPLING.get(), 0.3f);
       //Non-Edible Crop Drops
        DataUtil.registerCompostable(POPPY_SEEDS.get(), 0.3f);
        DataUtil.registerCompostable(ROSE_PETALS.get(), 0.3f);

       //Storage
        DataUtil.registerCompostable(BLACK_ACORN_SACK.get(), 1.0f);
        DataUtil.registerCompostable(DANDELION_ROOTS_CRATE.get(), 1.0f);

        DataUtil.registerCompostable(POPPY_SEEDS_SACK.get(), 1.0f);
        DataUtil.registerCompostable(ROSE_HIP_SACK.get(), 1.0f);
        DataUtil.registerCompostable(SPRUCE_TIPS_SACK.get(), 1.0f);
       //Food
        DataUtil.registerCompostable(ACORN_COOKIE.get(), 0.85f);
        DataUtil.registerCompostable(APPLE_SLICE.get(), 0.3f);
        DataUtil.registerCompostable(BLACK_ACORN.get(), 0.65f);
        DataUtil.registerCompostable(DANDELION_ROOT.get(), 0.3f);

        DataUtil.registerCompostable(ROSE_COOKIE.get(), 0.85f);
        DataUtil.registerCompostable(NDItems.ROSE_HIP.get(), 0.3f);
        DataUtil.registerCompostable(SPRUCE_TIPS.get(), 0.65f);
    }
}
