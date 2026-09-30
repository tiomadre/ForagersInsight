package com.tiomadre.foragersinsight.core.mixin;

import com.tiomadre.foragersinsight.common.block.HollowLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MushroomBlock.class)
public class MushroomOnHollowLogMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void foragersInsight$canSurviveOnHollowLogs(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (foragersInsight$canUseHollowLogSupport(level, pos)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "performBonemeal", at = @At("HEAD"), cancellable = true)
    private void foragersInsight$growColonyOnHollowLogs(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!foragersInsight$canUseHollowLogSupport(level, pos)) {
            return;
        }

        BlockState colonyState = foragersInsight$getColonyState(state);
        if (colonyState == null) {
            return;
        }

        level.setBlock(pos, colonyState, Block.UPDATE_ALL);
        ci.cancel();
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void foragersInsight$naturalColonyProgressionOnHollowLogs(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!foragersInsight$canUseHollowLogSupport(level, pos)) {
            return;
        }

        BlockState colonyState = foragersInsight$getColonyState(state);
        if (colonyState == null || !colonyState.canSurvive(level, pos)) {
            return;
        }

        level.setBlock(pos, colonyState, Block.UPDATE_ALL);
    }

    private static boolean foragersInsight$canUseHollowLogSupport(LevelReader level, BlockPos pos) {
        return HollowLogBlock.canSupportMushroomAt(level, pos);
    }

    private static BlockState foragersInsight$getColonyState(BlockState mushroomState) {
        ResourceLocation mushroomId = BuiltInRegistries.BLOCK.getKey(mushroomState.getBlock());
        if (mushroomId == null) {
            return null;
        }

        ResourceLocation colonyId;
        if (mushroomState.is(Blocks.RED_MUSHROOM)) {
            colonyId = ResourceLocation.fromNamespaceAndPath("farmersdelight", "red_mushroom_colony");
        } else if (mushroomState.is(Blocks.BROWN_MUSHROOM)) {
            colonyId = ResourceLocation.fromNamespaceAndPath("farmersdelight", "brown_mushroom_colony");
        } else if (mushroomId.getPath().endsWith("_mushroom")) {
            colonyId = ResourceLocation.fromNamespaceAndPath(mushroomId.getNamespace(), mushroomId.getPath() + "_colony");
        } else {
            return null;
        }

        return BuiltInRegistries.BLOCK.getOptional(colonyId)
                .map(Block::defaultBlockState)
                .orElse(null);
    }
}