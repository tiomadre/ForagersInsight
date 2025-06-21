package com.doltandtio.foragersinsight.common.worldgen.trees.grower;

import com.doltandtio.foragersinsight.common.worldgen.FIConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BountifulSpruceTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean pHasFlowers) {
        return FIConfiguredFeatures.SPRUCE_TIP_TREE_KEY;
    }
}