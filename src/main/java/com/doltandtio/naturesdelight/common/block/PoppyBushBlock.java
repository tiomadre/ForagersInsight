package com.doltandtio.naturesdelight.common.block;

import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class PoppyBushBlock extends CropBlock {
    public PoppyBushBlock(Properties pProperties) {
        super(pProperties);
    }

    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return NDBlocks.POPPY_BUSH.get().defaultBlockState();
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.CABBAGE_SEEDS.get();
    }

}
