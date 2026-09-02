package com.tiomadre.foragersinsight.common.block;

import com.tiomadre.foragersinsight.common.block.entity.SapTrapBlockEntity;
import com.tiomadre.foragersinsight.common.item.BaitItem;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import com.tiomadre.foragersinsight.core.registry.FISapTrapBaits;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class SapTrapBlock extends FoliageMatBlock implements EntityBlock {
    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
    private static final int STUCK_DURATION = 150 ;

    private static final int BREAK_DELAY_TICKS = 5;
    private static final int SLOW_DURATION_TICKS = 5;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    public SapTrapBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.1F)
                .sound(SoundType.SLIME_BLOCK)
                .noOcclusion()
                .mapColor(MapColor.COLOR_LIGHT_GREEN)
                .isValidSpawn((state, level, pos, entityType) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(TRIGGERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.foragersinsight.sap_trap.baits").withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.foragersinsight.sap_trap.potent_baits").withStyle(net.minecraft.ChatFormatting.DARK_GREEN));
    }


    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!(level.getBlockEntity(pos) instanceof SapTrapBlockEntity sapTrap)) {
            return InteractionResult.PASS;
        }

        if (sapTrap.hasBait()) {
            if (!level.isClientSide) {
                ItemStack bait = sapTrap.removeBait();
                if (!player.addItem(bait)) {
                    player.drop(bait, false);
                }
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!BaitItem.isBait(heldStack)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            sapTrap.setBait(heldStack);
            if (!player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.8F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        this.triggerTrap(level, pos, state, entity);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        this.triggerTrap(level, pos, state, entity);
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(TRIGGERED)) {
            return;
        }

        AABB trapArea = new AABB(pos).inflate(0.05D, 0.0D, 0.05D);
        boolean baitWasEaten = false;
        for (LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, trapArea)) {
            int duration = getStuckDuration(level, pos, livingEntity);
            livingEntity.addEffect(new MobEffectInstance(FIMobEffects.STUCK, duration, 0, false, true, true));
            baitWasEaten = baitWasEaten || isEatingBait(level, pos, livingEntity);
        }

        if (baitWasEaten && level.getBlockEntity(pos) instanceof SapTrapBlockEntity sapTrap) {
            sapTrap.removeBait();
            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 0.8F, 1.0F);
        }


        level.playSound(null, pos, SoundType.BAMBOO.getBreakSound(), SoundSource.BLOCKS, SoundType.BAMBOO.getVolume(), SoundType.BAMBOO.getPitch());
        level.destroyBlock(pos, false);
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (level instanceof Level realLevel && !realLevel.isClientSide && realLevel.getBlockEntity(pos) instanceof SapTrapBlockEntity sapTrap && sapTrap.hasBait()) {
            ItemStack bait = sapTrap.removeBait();
            realLevel.addFreshEntity(new ItemEntity(realLevel, pos.getX() + 0.5D, pos.getY() + 0.15D, pos.getZ() + 0.5D, bait));
        }
        super.destroy(level, pos, state);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new SapTrapBlockEntity(pos, state);
    }

    private int getStuckDuration(@NotNull Level level, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
        if (level.getBlockEntity(pos) instanceof SapTrapBlockEntity sapTrap) {
            return Math.round(STUCK_DURATION * FISapTrapBaits.stuckDurationMultiplier(sapTrap.getBait(), livingEntity));

        }
        return STUCK_DURATION;
    }
    private boolean isEatingBait(@NotNull Level level, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
        return level.getBlockEntity(pos) instanceof SapTrapBlockEntity sapTrap
                && BaitItem.attracts(sapTrap.getBait(), livingEntity);
    }

    private void triggerTrap(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if (level.isClientSide || !(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOW_DURATION_TICKS, 0, false, true, true));

        if (!state.getValue(TRIGGERED)) {
            level.setBlock(pos, state.setValue(TRIGGERED, true), Block.UPDATE_CLIENTS);
            level.scheduleTick(pos, this, BREAK_DELAY_TICKS);
            level.playSound(null, pos, SoundType.SLIME_BLOCK.getStepSound(), SoundSource.BLOCKS, SoundType.SLIME_BLOCK.getVolume(), SoundType.SLIME_BLOCK.getPitch());
            level.playSound(null, pos, SoundType.BAMBOO.getStepSound(), SoundSource.BLOCKS, SoundType.BAMBOO.getVolume(), SoundType.BAMBOO.getPitch());
        }
    }
}