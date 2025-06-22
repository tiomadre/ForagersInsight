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

public class SpruceTipTreeFoliagePlacer extends FoliagePlacer {
    protected final int height;
    private transient int bottomRowY = Integer.MAX_VALUE;

    public static final Codec<SpruceTipTreeFoliagePlacer> CODEC = RecordCodecBuilder
            .create(instance -> foliagePlacerParts(instance)
                    .and(Codec.intRange(0, 16).fieldOf("height").forGetter(fp -> fp.height)).apply(instance, SpruceTipTreeFoliagePlacer::new));

    public SpruceTipTreeFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return FIFoliagePlacerType.SPRUCE_TIP_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter blockSetter, @NotNull RandomSource rand, @NotNull TreeConfiguration config,
                                 int pMaxFreeTreeHeight, @NotNull FoliageAttachment attachment, int height, int radius, int offset) {
        int bottom = offset - height;
        if (bottom < this.bottomRowY) {
            this.bottomRowY = bottom;
        }
        for (int i = offset; i >= bottom; --i) {
            int j = Math.max(radius + attachment.radiusOffset() - 1 - i / 2, 0);
            this.placeLeavesRow(level, blockSetter, rand, config, attachment.pos(), j, i, attachment.doubleTrunk());
        }
    }

    @Override
    protected void placeLeavesRow(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter setter, @NotNull RandomSource rand, @NotNull TreeConfiguration config, @NotNull BlockPos pos, int range, int localY, boolean large) {
        int i = large ? 1 : 0;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int j = -range; j <= range + i; ++j) {
            for (int k = -range; k <= range + i; ++k) {
                if (!this.shouldSkipLocationSigned(rand, j, localY, k, range, large)) {
                    mutable.setWithOffset(pos, j, localY, k);
                    boolean bottom = localY == this.bottomRowY;
                    tryPlaceLeaf(level, setter, rand, config, mutable, bottom);
                }
            }
        }
    }

    @Override
    public int foliageHeight(@NotNull RandomSource rand, int height, @NotNull TreeConfiguration config) {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource rand, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range && (rand.nextInt(2) == 0 || localY == 0);
    }

    protected static boolean tryPlaceLeaf(LevelSimulatedReader level, @NotNull FoliageSetter setter, @NotNull RandomSource rand, @NotNull TreeConfiguration config, @NotNull BlockPos pos, boolean bottom) {
        if (!TreeFeature.validTreePos(level, pos)) {
            return false;
        } else {
            BlockState blockstate;
            if (bottom) {
                blockstate = rand.nextBoolean()
                        ? FIBlocks.BOUNTIFUL_SPRUCE_LEAVES.get().defaultBlockState()
                        : Blocks.SPRUCE_LEAVES.defaultBlockState();
            } else {
                blockstate = Blocks.SPRUCE_LEAVES.defaultBlockState();
            }
            if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED)) {
                blockstate = blockstate.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, f -> f.isSourceOfType(Fluids.WATER)));
            }
            setter.set(pos, blockstate);
            return true;
        }
    }
}