package com.doltandtio.naturesdelight.common.block;

import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import com.doltandtio.naturesdelight.core.registry.NDItems;
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

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return NDBlocks.POPPY_BUSH.get().defaultBlockState();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return NDItems.POPPY_SEEDS.get();
    }

}
