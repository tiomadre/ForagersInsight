package com.doltandtio.foragersinsight.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SunflowerCropBlock {
    private static final VoxelShape[] SHAPE_BY_AGE = {
            Block.box(8, 0, 0, 8, 16, 16),  // First element of the model
            Block.box(0, 0, 8, 16, 16, 8), // Second element of the model
            Block.box(8, 16, 0, 8, 32, 16), // Third
    };

}
