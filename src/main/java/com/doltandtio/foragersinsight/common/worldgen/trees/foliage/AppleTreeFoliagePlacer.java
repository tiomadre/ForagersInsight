package com.doltandtio.foragersinsight.common.worldgen.trees.foliage;

import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIFoliagePlacerType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class AppleTreeFoliagePlacer extends FoliagePlacer {
    protected final int height;

    public static final Codec<AppleTreeFoliagePlacer> CODEC = RecordCodecBuilder
            .create(instance -> foliagePlacerParts(instance)
                    .and(Codec.intRange(0, 16).fieldOf("height").forGetter(fp -> fp.height)).apply(instance, AppleTreeFoliagePlacer::new));

    public AppleTreeFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int height) {
        super(pRadius, pOffset);
        this.height = height;
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return FIFoliagePlacerType.APPLE_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter blockSetter, @NotNull RandomSource rand, @NotNull TreeConfiguration config,
                                 int pMaxFreeTreeHeight, FoliageAttachment attachment, int height, int radius, int offset) {

        for (int i = offset; i >= offset - height; --i) {
            int j = Math.max(radius + attachment.radiusOffset() - 1 - i / 2, 0);
            this.placeLeavesRow(level, blockSetter, rand, config, attachment.pos(), j, i, attachment.doubleTrunk());
        }
    }

    @Override
    protected void placeLeavesRow(LevelSimulatedReader pLevel, FoliagePlacer.FoliageSetter pFoliageSetter, RandomSource pRandom, TreeConfiguration pTreeConfiguration, BlockPos pPos, int pRange, int pLocalY, boolean pLarge) {
        int i = pLarge ? 1 : 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int j = -pRange; j <= pRange + i; ++j) {
            for(int k = -pRange; k <= pRange + i; ++k) {
                if (!this.shouldSkipLocationSigned(pRandom, j, pLocalY, k, pRange, pLarge)) {
                    blockpos$mutableblockpos.setWithOffset(pPos, j, pLocalY, k);
                    tryPlaceLeaf(pLevel, pFoliageSetter, pRandom, pTreeConfiguration, blockpos$mutableblockpos);
                }
            }
        }

    }

    @Override
    public int foliageHeight(@NotNull RandomSource pRandom, int pHeight, @NotNull TreeConfiguration pConfig) {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return pLocalX == pRange && pLocalZ == pRange && (pRandom.nextInt(2) == 0 || pLocalY == 0);
    }

    protected static boolean tryPlaceLeaf(LevelSimulatedReader pLevel, FoliagePlacer.@NotNull FoliageSetter pFoliageSetter, @NotNull RandomSource pRandom, @NotNull TreeConfiguration pTreeConfiguration, @NotNull BlockPos pPos) {
        if (!TreeFeature.validTreePos(pLevel, pPos)) {
            return false;
        }
        else {
            BlockState blockstate = pRandom.nextBoolean() ? Blocks.OAK_LEAVES.defaultBlockState() : FIBlocks.BOUNTIFUL_OAK_LEAVES.get().defaultBlockState();
            if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED)) {
                blockstate = blockstate.setValue(BlockStateProperties.WATERLOGGED, pLevel.isFluidAtPosition(pPos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
            }

            pFoliageSetter.set(pPos, blockstate);
            return true;
        }
    }
}
