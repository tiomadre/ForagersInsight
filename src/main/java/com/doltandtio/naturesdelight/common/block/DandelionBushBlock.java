package com.doltandtio.naturesdelight.common.block;

import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DandelionBushBlock extends CropBlock {
    public DandelionBushBlock(Properties props) {
        super(props);
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return NDBlocks.DANDELION_BUSH.get().defaultBlockState();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return NDItems.DANDELION_ROOT.get();
    }
}
