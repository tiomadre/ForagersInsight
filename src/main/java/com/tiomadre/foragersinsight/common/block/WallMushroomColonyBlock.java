package com.tiomadre.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;

public class WallMushroomColonyBlock extends MushroomColonyBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape[][] SHAPES_BY_FACING_AND_AGE = WMCShapes();

    public WallMushroomColonyBlock(Properties properties, Holder<Item> mushroomType) {
        super(mushroomType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(COLONY_AGE, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing.getOpposite());
        BlockState supportState = level.getBlockState(supportPos);
        return WallMushroomBlock.canGrowColonyOn(supportState, facing)
                || supportState.canSustainPlant(level, supportPos, facing, level.getBlockState(pos)).isTrue();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_FACING_AND_AGE[state.getValue(FACING).get2DDataValue()][state.getValue(getAgeProperty())];
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(COLONY_AGE);
        Direction facing = state.getValue(FACING);
        BlockState supportState = level.getBlockState(pos.relative(facing.getOpposite()));
        if (age < getMaxAge() && supportState.is(ModTags.Blocks.MUSHROOM_COLONY_GROWABLE_ON) && CommonHooks.canCropGrow(level, pos, state, random.nextInt(4) == 0)) {
            level.setBlock(pos, state.setValue(COLONY_AGE, age + 1), 2);
            CommonHooks.fireCropGrowPost(level, pos, state);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLONY_AGE, FACING);
    }

    private static VoxelShape[][] WMCShapes() {
        VoxelShape[][] shapes = new VoxelShape[4][4];
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int index = direction.get2DDataValue();
            for (int age = 0; age <= 3; age++) {
                double inset = 4.0D - age;
                double minY = 4.0D - age;
                double maxY = 12.0D + age * 0.5D;
                shapes[index][age] = switch (direction) {
                    case SOUTH -> Block.box(inset, minY, 0.0D, 16.0D - inset, maxY, 8.0D + age * 2.0D);
                    case WEST -> Block.box(8.0D - age * 2.0D, minY, inset, 16.0D, maxY, 16.0D - inset);
                    case EAST -> Block.box(0.0D, minY, inset, 8.0D + age * 2.0D, maxY, 16.0D - inset);
                    default -> Block.box(inset, minY, 8.0D - age * 2.0D, 16.0D - inset, maxY, 16.0D);
                };
            }
        }
        return shapes;
    }
}