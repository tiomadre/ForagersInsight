package com.tiomadre.foragersinsight.common.block;

import com.mojang.serialization.MapCodec;
import com.tiomadre.foragersinsight.common.block.entity.DiffuserBlockEntity;
import com.tiomadre.foragersinsight.data.server.recipes.FIDiffusingRecipes;
import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import com.tiomadre.foragersinsight.core.registry.FIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiffuserBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.box(9.0D, 12.0D, 6.0D, 10.0D, 14.0D, 10.0D),
            Block.box(6.0D, 12.0D, 6.0D, 7.0D, 14.0D, 10.0D),
            Block.box(7.0D, 12.0D, 6.0D, 9.0D, 14.0D, 7.0D),
            Block.box(7.0D, 12.0D, 9.0D, 9.0D, 14.0D, 10.0D)
    );


    public DiffuserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(WATERLOGGED, false));
    }



    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DiffuserBlockEntity(pos, state);
    }
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof DiffuserBlockEntity diffuser)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!level.isClientSide) {

            if (player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, diffuser, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
                            @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof DiffuserBlockEntity diffuser && stack.hasTag()) {
                if (diffuser.applyItemData(stack.getTag())) {
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                }
            }
        }
    }
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos,
                                        @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        return belowState.isFaceSturdy(level, belowPos, Direction.UP)
                || Block.isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP);
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction,
                                           @NotNull BlockState neighborState, @NotNull LevelAccessor level,
                                           @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (direction == Direction.DOWN && !canSurvive(state, level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }

        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.is(FluidTags.WATER));
    }


    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                         @NotNull BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof DiffuserBlockEntity diffuser) {
                Containers.dropContents(level, pos, diffuser);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public @NotNull java.util.List<ItemStack> getDrops(@NotNull BlockState state, @NotNull LootParams.Builder params) {
        BlockEntity entity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (entity instanceof DiffuserBlockEntity diffuser) {
            return java.util.Collections.singletonList(diffuser.getDiffuserStack());
        }
        return super.getDrops(state, params);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, FIBlockEntityTypes.DIFFUSER.get(), DiffuserBlockEntity::serverTick);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(LIT)) {
            return;
        }

        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 1.0D;
        double z = pos.getZ() + 0.5D;
        double offsetX = (random.nextDouble() - 0.5D) * 0.1D;
        double offsetZ = (random.nextDouble() - 0.5D) * 0.1D;

        boolean submerged = isSubmergedInWater(level, pos, state);

        if (!submerged && random.nextInt(4) == 0) {
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, offsetX * 0.6D, 0.07D, offsetZ * 0.6D);
        } else if (submerged && random.nextInt(4) == 0) {
            double bubbleX = x + (random.nextDouble() - 0.5D) * 0.3D;
            double bubbleY = y + random.nextDouble() * 0.2D;
            double bubbleZ = z + (random.nextDouble() - 0.5D) * 0.3D;
            level.addParticle(ParticleTypes.BUBBLE, bubbleX, bubbleY, bubbleZ, 0.0D, 0.05D + random.nextDouble() * 0.02D, 0.0D);
        }
        if (random.nextInt(10) == 0) {
            if (submerged) {
                level.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS,
                        0.4F, 0.6F, false);
            } else {
                level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
                        0.4F, 0.4F, false);
            }
        }
        if (random.nextInt(5) == 0) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof DiffuserBlockEntity diffuser) {
                diffuser.getActiveScent().ifPresent(scent -> {
                    double scentX = x + (random.nextDouble() - 0.5D) * 0.3D;
                    double scentY = y + 0.2D + random.nextDouble() * 0.2D;
                    double scentZ = z + (random.nextDouble() - 0.5D) * 0.3D;
                    spawnScentParticle(level, random, scentX, scentY, scentZ, scent);
                });
            }
        }

    }
    private void spawnScentParticle(Level level, RandomSource random, double x, double y, double z, FIDiffusingRecipes scent) {
        SimpleParticleType particle = getScentParticleType(scent);
        if (particle == null) {
            return;
        }
        double driftX = (random.nextDouble() - 0.5D) * 0.08D;
        double driftY = 0.03D + random.nextDouble() * 0.02D;
        double driftZ = (random.nextDouble() - 0.5D) * 0.08D;
        level.addParticle(particle, x, y, z, driftX, driftY, driftZ);
    }

    private @Nullable SimpleParticleType getScentParticleType(FIDiffusingRecipes scent) {
        if (scent == FIDiffusingRecipes.ROSEY.get() || scent == FIDiffusingRecipes.ROSEY_II.get()) {
            return FIParticleTypes.ROSE_SCENT.get();
        }
        if (scent == FIDiffusingRecipes.CONIFEROUS.get() || scent == FIDiffusingRecipes.CONIFEROUS_II.get()) {
            return FIParticleTypes.CONIFEROUS_SCENT.get();
        }
        if (scent == FIDiffusingRecipes.FLORAL.get()) {
            return FIParticleTypes.FLORAL_SCENT.get();
        }
        if(scent ==  FIDiffusingRecipes.FLORAL_II.get()) {
            return FIParticleTypes.FLORAL_II_SCENT.get();
        }
        if (scent == FIDiffusingRecipes.FOUL.get() || scent == FIDiffusingRecipes.FOUL_II.get()) {
            return FIParticleTypes.FOUL_SCENT.get();
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED);
    }

    private boolean isSubmergedInWater(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return true;
        }
        FluidState currentState = level.getFluidState(pos);
        if (currentState.is(FluidTags.WATER)) {
            return true;
        }
        FluidState aboveState = level.getFluidState(pos.above());
        return aboveState.is(FluidTags.WATER);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }
}
