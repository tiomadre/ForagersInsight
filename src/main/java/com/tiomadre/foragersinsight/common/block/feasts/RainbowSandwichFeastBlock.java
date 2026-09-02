package com.tiomadre.foragersinsight.common.block.feasts;

import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.CommonTags;

import java.util.function.Supplier;

public class RainbowSandwichFeastBlock extends Block {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 3);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape[] NORTH_SHAPES = new VoxelShape[]{
            Block.box(1.5D, 0.0D, 1.0D, 14.5D, 6.0D, 15.0D),
            Block.box(1.5D, 0.0D, 1.0D, 14.5D, 6.0D, 12.0D),
            Block.box(1.5D, 0.0D, 1.0D, 14.5D, 6.0D, 9.0D),
            Block.box(1.5D, 0.0D, 1.0D, 14.5D, 6.0D, 6.0D)
    };
    private static final VoxelShape[] EAST_SHAPES = new VoxelShape[]{
            Block.box(1.0D, 0.0D, 1.5D, 15.0D, 6.0D, 14.5D),
            Block.box(4.0D, 0.0D, 1.5D, 15.0D, 6.0D, 14.5D),
            Block.box(7.0D, 0.0D, 1.5D, 15.0D, 6.0D, 14.5D),
            Block.box(10.0D, 0.0D, 1.5D, 15.0D, 6.0D, 14.5D)
    };
    private static final VoxelShape[] SOUTH_SHAPES = new VoxelShape[]{
            Block.box(1.5D, 0.0D, 1.0D, 14.5D, 6.0D, 15.0D),
            Block.box(1.5D, 0.0D, 4.0D, 14.5D, 6.0D, 15.0D),
            Block.box(1.5D, 0.0D, 7.0D, 14.5D, 6.0D, 15.0D),
            Block.box(1.5D, 0.0D, 10.0D, 14.5D, 6.0D, 15.0D)
    };
    private static final VoxelShape[] WEST_SHAPES = new VoxelShape[]{
            Block.box(1.0D, 0.0D, 1.5D, 15.0D, 6.0D, 14.5D),
            Block.box(1.0D, 0.0D, 1.5D, 12.0D, 6.0D, 14.5D),
            Block.box(1.0D, 0.0D, 1.5D, 9.0D, 6.0D, 14.5D),
            Block.box(1.0D, 0.0D, 1.5D, 6.0D, 6.0D, 14.5D)
    };

    private final Supplier<Item> servingItem;

    public RainbowSandwichFeastBlock(Properties properties, Supplier<Item> servingItem) {
        super(properties);
        this.servingItem = servingItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape[] shapes = switch (state.getValue(FACING)) {
            case EAST -> EAST_SHAPES;
            case SOUTH -> SOUTH_SHAPES;
            case WEST -> WEST_SHAPES;
            default -> NORTH_SHAPES;
        };
        return shapes[state.getValue(BITES)];
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }


    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!player.getItemInHand(hand).is(CommonTags.Items.TOOLS_KNIFE)) {
            return InteractionResult.PASS;
        }

        int bites = state.getValue(BITES);
        if (bites < 3) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Direction direction = player.getDirection().getOpposite();
        ItemStack serving = new ItemStack(this.servingItem.get());
        ItemEntity servingEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, serving,
                direction.getStepX() * 0.15D, 0.05D, direction.getStepZ() * 0.15D);
        level.addFreshEntity(servingEntity);

        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState,
                                           @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return facing == Direction.DOWN && !state.canSurvive(level, currentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES, FACING);
    }
}