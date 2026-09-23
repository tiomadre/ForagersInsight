package com.tiomadre.foragersinsight.core.mixin;

import com.tiomadre.foragersinsight.common.worldgen.LilacTreeGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.RichSoilBlock;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilTreeMixin {
    @Inject(method = "randomTick", at = @At("TAIL"))
    private void foragersInsight$growLilacTree(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        BlockState aboveState = level.getBlockState(pos.above());
        if (!aboveState.is(Blocks.LILAC) || aboveState.getValue(DoublePlantBlock.HALF) != DoubleBlockHalf.LOWER) {
            return;
        }

        if (LilacTreeGrowth.shouldGrow(random)) {
            LilacTreeGrowth.tryGrowLilacTree(level, pos, random);
        }
    }
}

@Mixin(SaplingBlock.class)
class RichSoilSaplingMixin {
//    private static final BountifulOakTreeGrower BOUNTIFUL_OAK_GROWER = new BountifulOakTreeGrower();
//    private static final BountifulDarkOakTreeGrower BOUNTIFUL_DARK_OAK_GROWER = new BountifulDarkOakTreeGrower();
//    private static final BountifulSpruceTreeGrower BOUNTIFUL_SPRUCE_GROWER = new BountifulSpruceTreeGrower();
//    private static final SappyBirchTreeGrower SAPPY_BIRCH_GROWER = new SappyBirchTreeGrower();

    @Inject(method = "advanceTree(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;)V", at = @At("HEAD"), cancellable = true)
    private void foragersInsight$growBountifulTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random, CallbackInfo ci) {
        if (state.getValue(SaplingBlock.STAGE) == 0) {
            return;
        }

        BlockState belowState = level.getBlockState(pos.below());
        if (!(belowState.getBlock() instanceof RichSoilBlock) && !(belowState.getBlock() instanceof RichSoilFarmlandBlock)) {
            return;
        }

        boolean grew = false;
//        if (state.is(Blocks.OAK_SAPLING)) {
//            grew = BOUNTIFUL_OAK_GROWER.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
//        } else if (state.is(Blocks.DARK_OAK_SAPLING)) {
//            grew = BOUNTIFUL_DARK_OAK_GROWER.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
//        } else if (state.is(Blocks.SPRUCE_SAPLING)) {
//            grew = BOUNTIFUL_SPRUCE_GROWER.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
//        } else if (state.is(Blocks.BIRCH_SAPLING)) {
//            grew = SAPPY_BIRCH_GROWER.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
//        }

        if (grew) {
            level.setBlock(pos.below(), Blocks.ROOTED_DIRT.defaultBlockState(), 2);
            ci.cancel();
        }
    }
}