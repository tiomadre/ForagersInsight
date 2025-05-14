// com.doltandtio.foragersinsight.common.item.MalletItem.java

package com.doltandtio.foragersinsight.common.item;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class MalletItem extends PickaxeItem {

    private static final Map<Block, ItemLike> CRUSH_RESULTS = new HashMap<>();

    static {
     // Crushable Blocks

        //Ices
        CRUSH_RESULTS.put(Blocks.BLUE_ICE, Blocks.PACKED_ICE);
        CRUSH_RESULTS.put(Blocks.PACKED_ICE, Blocks.ICE);
        CRUSH_RESULTS.put(Blocks.ICE, new ItemStack(FIItems.CRUSHED_ICE.get(), 2).getItem());
        //Stone, Flint, Gravel
        CRUSH_RESULTS.put(Blocks.STONE, Blocks.COBBLESTONE);
        CRUSH_RESULTS.put(Blocks.COBBLESTONE, Blocks.GRAVEL);
        CRUSH_RESULTS.put(Blocks.GRAVEL, Items.FLINT);
        //Sand and Glass
        CRUSH_RESULTS.put(Blocks.GLASS, Blocks.SAND);
        CRUSH_RESULTS.put(Blocks.SANDSTONE, Blocks.SAND);
        //Cracked Blocks
        CRUSH_RESULTS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        CRUSH_RESULTS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        CRUSH_RESULTS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        CRUSH_RESULTS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        //Fruit
        CRUSH_RESULTS.put(Blocks.PUMPKIN, Items.PUMPKIN_SEEDS);
        CRUSH_RESULTS.put(Blocks.MELON, Items.MELON_SEEDS);
        CRUSH_RESULTS.put(Blocks.CARVED_PUMPKIN, Items.PUMPKIN_SEEDS);
        CRUSH_RESULTS.put(Blocks.COCOA, new ItemStack(FIItems.COCOA_POWDER.get(), 2).getItem());


    }

    public MalletItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (CRUSH_RESULTS.containsKey(block)) {
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            // Crushing triggers a CD equal to 75% of the normal break time
            float hardness     = state.getDestroySpeed(level, pos);
            int baseTicks      = (int)(hardness * 1.5f * 20f);
            int longerTicks    = Math.max(1, (int)(baseTicks * 0.75f));
            player.getCooldowns().addCooldown(this, longerTicks);

            level.destroyBlock(pos, false);
            ItemLike result = CRUSH_RESULTS.get(block);
            Block.popResource(level, pos, new ItemStack(result));

            if (block == Blocks.PUMPKIN) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);}
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            if (block == Blocks.MELON) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);}
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            if (block == Blocks.CARVED_PUMPKIN) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);}
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }
}
