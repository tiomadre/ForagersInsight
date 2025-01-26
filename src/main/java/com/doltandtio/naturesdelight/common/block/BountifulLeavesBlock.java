package com.doltandtio.naturesdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BountifulLeavesBlock extends LeavesBlock implements BonemealableBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    public BountifulLeavesBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(super.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return super.isRandomlyTicking(state) || canGrow(state);
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    public int getAge(@NotNull BlockState state) {
        return state.getValue(AGE);
    }

    public boolean canGrow(BlockState state) {
        if (getAge(state) >= MAX_AGE) {
            return false;
        }

        return state.getValue(LeavesBlock.DISTANCE) < DECAY_DISTANCE;
    }


    @Override
    public @NotNull InteractionResult use(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        int age = pState.getValue(AGE);
        boolean isFullGrown = age == MAX_AGE;

        if (!isFullGrown && pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        }
        else if (isFullGrown) {

            popResource(pLevel, pPos, new ItemStack(Items.APPLE, 1));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            BlockState blockstate = pState.setValue(AGE, 0);
            pLevel.setBlock(pPos, blockstate, Block.UPDATE_CLIENTS);

            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }


    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
    }


    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader pLevel, @NotNull BlockPos pPos, @NotNull BlockState state, boolean pIsClient) {
        return this.getAge(state) != MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pPos, @NotNull BlockState pState) {
        return random.nextInt(2) == 0;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource pRandom, @NotNull BlockPos pos, @NotNull BlockState state) {
        int aNewAgeArrives = getAge(state) + 1;
        level.setBlock(pos, state.setValue(AGE, aNewAgeArrives), Block.UPDATE_ALL);
    }
}
