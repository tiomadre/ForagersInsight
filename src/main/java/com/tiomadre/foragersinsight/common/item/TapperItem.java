package com.tiomadre.foragersinsight.common.item;

import com.tiomadre.foragersinsight.common.block.TapperBlock;
import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FITappables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TapperItem extends Item {
    public TapperItem(Properties props) {
        super(props);
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.FIRE_ASPECT)) return true;
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos clicked = ctx.getClickedPos();
        Direction face = ctx.getClickedFace();
        BlockState clickedState = level.getBlockState(clicked);

        BlockPos logPos;
        if (clickedState.getBlock() instanceof TapperBlock) {
            Direction out = clickedState.getValue(TapperBlock.FACING);
            logPos = clicked.relative(out.getOpposite());
        } else {
            logPos = clicked;
        }

        BlockState logState = level.getBlockState(logPos);
        if (FITappables.find(logState).isEmpty() || face.getAxis().isVertical()) {
            return InteractionResult.PASS;
        }

        BlockPos targetPos = logPos.relative(face);
        if (!level.isEmptyBlock(targetPos)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            ItemStack stack = ctx.getItemInHand();
            // place the tapper block
            BlockState tapperState = FIBlocks.TAPPER.get().defaultBlockState()
                    .setValue(TapperBlock.FACING, face)
                    .setValue(TapperBlock.HAS_TAPPER, true)
                    .setValue(TapperBlock.FILL, 0)
                    .setValue(TapperBlock.ENCHANTED, stack.getEnchantmentLevel(level.holderOrThrow(Enchantments.FIRE_ASPECT)) > 0);
            level.setBlock(targetPos, tapperState, Block.UPDATE_ALL);

            level.getBlockEntity(targetPos, FIBlockEntityTypes.TAPPER.get())
                    .ifPresent(be -> be.setTapperStack(stack));

            //sound
            SoundType type = level.getBlockState(logPos).getSoundType();
            level.playSound(null, targetPos, type.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

            Player player = ctx.getPlayer();
            if (player != null && !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}