package com.doltandtio.naturesdelight.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.*;

public class NDDataUtil {
    public static void registerCompat() {
        registerCompostable();
    }

    private static void registerCompostable() {
        DataUtil.registerCompostable(BOUNTIFUL_OAK_LEAVES.get(), 0.3f);
        DataUtil.registerCompostable(BOUNTIFUL_OAK_SAPLING.get(), 0.3f);
        DataUtil.registerCompostable(POPPY_SEEDS.get(), 0.3f);
        DataUtil.registerCompostable(DANDELION_ROOT.get(), 0.3f);

        DataUtil.registerCompostable(BLACK_ACORN.get(), 0.5f);
        DataUtil.registerCompostable(APPLE_SLICE.get(), 0.3f);

        DataUtil.registerCompostable(ROSE_HIP.get(), 0.3f);
        DataUtil.registerCompostable(ROSE_HIP_CRATE.get(), 1.0f);
        DataUtil.registerCompostable(ROSE_PETALS.get(), 0.3f);
        DataUtil.registerCompostable(ROSE_PETALS_SACK.get(), 1.0f);

        DataUtil.registerCompostable(ROSE_COOKIE.get(), 0.85f);
    }
}
