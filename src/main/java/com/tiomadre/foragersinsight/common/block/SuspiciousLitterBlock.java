package com.tiomadre.foragersinsight.common.block;

import com.tiomadre.foragersinsight.common.block.entity.suspiciouslitter.SuspiciousLitterBlockEntity;
import com.tiomadre.foragersinsight.common.worldgen.FIBiomes;
import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SuspiciousLitterBlock extends CarpetBlock implements EntityBlock {

    public static final EnumProperty<FoliageType> FOLIAGE = EnumProperty.create("foliage", FoliageType.class);
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 10, 16);

    public SuspiciousLitterBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.AZALEA_LEAVES)
                .sound(SoundType.ROOTED_DIRT)
                .noOcclusion()
                .mapColor(MapColor.PLANT)
                .isValidSpawn((state, level, pos, entityType) -> false)
                .pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any().setValue(FOLIAGE, FoliageType.OAK));
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
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos,
                                        @NotNull CollisionContext context) {
        return SHAPE;
    }
    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                                 @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }


    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level,
                                  @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, @NotNull LootParams.Builder builder) {
        return Collections.emptyList();
    }


    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level,
                                          @NotNull BlockPos pos, @NotNull Player player,
                                          @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!(player.getItemInHand(hand).getItem() instanceof BrushItem)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide) {
            return InteractionResult.sidedSuccess(true);
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SuspiciousLitterBlockEntity litterBlockEntity) {
            litterBlockEntity.startBrushing(player, player.getItemInHand(hand));
        }
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new SuspiciousLitterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }

        return type == FIBlockEntityTypes.SUSPICIOUS_LEAF_LITTER.get()
                ? (lvl, pos, st, be) -> SuspiciousLitterBlockEntity.serverTick(
                lvl, pos, st, (SuspiciousLitterBlockEntity) be)
                : null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return adjustFoliage(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                        @NotNull BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            BlockState adjusted = adjustFoliage(state, level, pos);
            if (!adjusted.equals(state)) {
                level.setBlock(pos, adjusted, Block.UPDATE_ALL);
            }
        }
    }

    private BlockState adjustFoliage(@Nullable BlockState state, LevelAccessor level, BlockPos pos) {
        if (state == null) {
            return null;
        }
        FoliageType foliageType = FoliageType.fromBiome(level.getBiome(pos).unwrapKey().orElse(null));
        return state.setValue(FOLIAGE, foliageType);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FOLIAGE);
    }

    public enum FoliageType implements StringRepresentable {
        OAK(Biomes.FOREST),
        FLOWER(Biomes.FLOWER_FOREST),
        BIRCH(Biomes.BIRCH_FOREST),
        SPRUCE(Biomes.TAIGA),
        DARK_OAK(Biomes.DARK_FOREST);

        private final ResourceKey<Biome> biome;

        FoliageType(ResourceKey<Biome> biome) {
            this.biome = biome;
        }

        public ResourceKey<Biome> biome() {
            return biome;
        }

        public static FoliageType fromBiome(@Nullable ResourceKey<Biome> biome) {
            if (biome == null) return OAK;
            if (biome.equals(FIBiomes.DARK_WOODLANDS)) {
                return DARK_OAK;
            }
            for (FoliageType type : values()) {
                if (type.biome().equals(biome)) return type;
            }
            return OAK;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name().toLowerCase();
        }
    }
}
