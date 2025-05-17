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
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class MalletItem extends PickaxeItem {

    private static final Map<Block, ItemLike> CRUSH_RESULTS = new HashMap<>();

    static {
    // Crushable
        // Ices
        CRUSH_RESULTS.put(Blocks.BLUE_ICE, Blocks.PACKED_ICE);
        CRUSH_RESULTS.put(Blocks.PACKED_ICE, Blocks.ICE);
        CRUSH_RESULTS.put(Blocks.ICE, new ItemStack(FIItems.CRUSHED_ICE.get(), 2).getItem());
        // Flint and Gravel
        CRUSH_RESULTS.put(Blocks.COBBLESTONE, Blocks.GRAVEL);
        CRUSH_RESULTS.put(Blocks.GRAVEL, Items.FLINT);
        // Sand and Glass
        CRUSH_RESULTS.put(Blocks.GLASS, Blocks.SAND);
        CRUSH_RESULTS.put(Blocks.SANDSTONE, Blocks.SAND);
        // Cracked Blocks
        CRUSH_RESULTS.put(Blocks.STONE, Blocks.COBBLESTONE);
        CRUSH_RESULTS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        CRUSH_RESULTS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        CRUSH_RESULTS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        CRUSH_RESULTS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        // Fruit
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

        // Only crush fully-grown cocoa pods (age 2)
        if (block == Blocks.COCOA) {
            int age = state.getValue(CocoaBlock.AGE);
            if (age < 2) {
                return super.useOn(context);
            }
        }

        if (CRUSH_RESULTS.containsKey(block)) {
            // damage item
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            // apply cooldown equal to 75% of normal break time
            float hardness  = state.getDestroySpeed(level, pos);
            int baseTicks   = (int)(hardness * 1.5f * 20f);
            int crushTicks  = Math.max(1, (int)(baseTicks * 0.75f));
            player.getCooldowns().addCooldown(this, crushTicks);

            // destroy block
            level.destroyBlock(pos, false);

            // determine drop count (2 for cocoa, 1 for others)
            ItemLike result = CRUSH_RESULTS.get(block);
            int amount = (block == Blocks.COCOA) ? 2 : 1;
            Block.popResource(level, pos, new ItemStack(result, amount));

            // play slime sound for pumpkins, melons, carved pumpkins
            if (block == Blocks.PUMPKIN || block == Blocks.MELON || block == Blocks.CARVED_PUMPKIN) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            }

            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }
}
