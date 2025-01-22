package com.doltandtio.naturesdelight.common.worldgen.trees.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;

public class AppleTreeFoliagePlacer extends BlobFoliagePlacer {
    public static final Codec<BlobFoliagePlacer> CODEC = RecordCodecBuilder.create(p_68427_ -> blobParts(p_68427_)
            .apply(p_68427_, BlobFoliagePlacer::new));

    public AppleTreeFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset, height);
    }
}
