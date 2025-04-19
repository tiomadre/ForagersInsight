package com.doltandtio.naturesdelight.common.block;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Supplier;

public class SliceableCakeBlock extends Block {

    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 6);
    protected static final VoxelShape[] SHAPE_BY_BITE;

    public final Supplier<Item> pieSlice;

    public SliceableCakeBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties);
        this.pieSlice = pieSlice;
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }
    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE_BY_BITE[state.getValue(BITES)];
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack(this.pieSlice.get());
    }

    public int getMaxBites() {
        return 7;
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.is(ModTags.KNIVES)) {
            return cutSlice(level, pos, state, player);
        }
        return InteractionResult.PASS;
    }

    protected InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
        int bites = state.getValue(BITES);
        if (bites < getMaxBites() - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Direction direction = player.getDirection().getOpposite();
        ItemStack sliceItem = this.getPieSliceItem();
        ItemEntity sliceEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, sliceItem,
                direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
        level.addFreshEntity(sliceEntity);

        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, @NotNull Level level, @NotNull BlockPos pos) {
        return getMaxBites() - blockState.getValue(BITES);
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    static {
        SHAPE_BY_BITE = new VoxelShape[]{
                Block.box(1.0F, 0.0F, 1.0F, 15.0F, 8.0F, 15.0F),
                Block.box(3.0F, 0.0F, 1.0F, 15.0F, 8.0F, 15.0F),
                Block.box(5.0F, 0.0F, 1.0F, 15.0F, 8.0F, 15.0F),
                Block.box(7.0F, 0.0F, 1.0F, 15.0F, 8.0F, 15.0F),
                Block.box(9.0F, 0.0F, 1.0F, 15.0F, 8.0F, 15.0F),
                Block.box(11.0F, 0.0F, 1.0F, 15.0F, 8.0F, 15.0F),
                Block.box(13.0F, 0.0F, 1.0F, 15.0F, 8.0F, 15.0F)
        };
    }
}