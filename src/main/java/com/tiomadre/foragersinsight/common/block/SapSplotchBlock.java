package com.tiomadre.foragersinsight.common.block;

import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SapSplotchBlock extends FoliageMatBlock {
    private static final int STUCK_DURATION = 60;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

    public SapSplotchBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.0F)
                .sound(SoundType.SLIME_BLOCK)
                .noOcclusion()
                .noCollission()
                .mapColor(MapColor.COLOR_LIGHT_GREEN)
                .isValidSpawn((state, level, pos, entityType) -> false));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull net.minecraft.world.level.BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        this.triggerSapSplotch(level, pos, entity);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        this.triggerSapSplotch(level, pos, entity);
        super.entityInside(state, level, pos, entity);
    }

    private void triggerSapSplotch(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (level.isClientSide || !(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        livingEntity.addEffect(new MobEffectInstance(FIMobEffects.STUCK, STUCK_DURATION, 0, false, true, true));
        level.destroyBlock(pos, false);
    }


    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!heldStack.is(Items.GLASS_BOTTLE)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            if (!player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }

            ItemStack sapBottle = new ItemStack(FIItems.BIRCH_SAP_BOTTLE.get());
            if (!player.addItem(sapBottle)) {
                player.drop(sapBottle, false);
            }

            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.playSound(null, pos, SoundEvents.SLIME_SQUISH_SMALL, SoundSource.BLOCKS, 0.8F, 1.0F);
            level.destroyBlock(pos, false);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}