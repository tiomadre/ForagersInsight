package com.doltandtio.naturesdelight.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.ROSE_HIP;
import static com.doltandtio.naturesdelight.core.registry.NDItems.ROSE_COOKIE;
import static com.doltandtio.naturesdelight.core.registry.NDItems.ROSE_PETALS;

public class NaDDataUtil {
    public static void registerCompat() {
        registerCompostable();

    }

    private static void registerCompostable() {
        DataUtil.registerCompostable(ROSE_HIP.get(), 0.3f);
        DataUtil.registerCompostable(ROSE_PETALS.get(), 0.3f);

        DataUtil.registerCompostable(ROSE_COOKIE.get(), 0.85f);
    }
}
