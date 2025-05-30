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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.util.RandomSource;

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
        // Cracked Brick
        CRUSH_RESULTS.put(Blocks.STONE, Blocks.COBBLESTONE);
        CRUSH_RESULTS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        CRUSH_RESULTS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        CRUSH_RESULTS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        CRUSH_RESULTS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        // Fruit and Grain
        CRUSH_RESULTS.put(Blocks.PUMPKIN, Items.PUMPKIN_SEEDS);
        CRUSH_RESULTS.put(Blocks.MELON, Items.MELON_SEEDS);
        CRUSH_RESULTS.put(Blocks.CARVED_PUMPKIN, Items.PUMPKIN_SEEDS);
        CRUSH_RESULTS.put(Blocks.COCOA, new ItemStack(FIItems.COCOA_POWDER.get(), 2).getItem());
        CRUSH_RESULTS.put(Blocks.SUGAR_CANE, new ItemStack(Items.SUGAR, 2).getItem());
        CRUSH_RESULTS.put(Blocks.WHEAT, new ItemStack(FIItems.WHEAT_FLOUR.get(), 2).getItem());
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

        // Mending (repair anvil and cracked bricks)
        if (state.getBlock() == Blocks.ANVIL || state.getBlock() == Blocks.CHIPPED_ANVIL || state.getBlock() == Blocks.DAMAGED_ANVIL) {
            // Sneak + Right Click
            if (player != null && player.isShiftKeyDown()) {
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack) > 0) {

                    stack.hurtAndBreak(20, player, p -> p.broadcastBreakEvent(context.getHand()));
                    BlockState repairedState = Blocks.ANVIL.defaultBlockState();
                    if (state.getBlock() == Blocks.CHIPPED_ANVIL) {
                        repairedState = Blocks.ANVIL.defaultBlockState();
                    } else if (state.getBlock() == Blocks.DAMAGED_ANVIL) {
                        repairedState = Blocks.CHIPPED_ANVIL.defaultBlockState();
                    }
                    level.setBlock(pos, repairedState, 3);
                    level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);

                    return InteractionResult.SUCCESS;
                }
            }
        }
        // Right Click for Cracked Bricks
        if (player != null) {
            if (state.getBlock() == Blocks.CRACKED_STONE_BRICKS) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, Blocks.STONE_BRICKS.defaultBlockState(), 3);
               level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
               return InteractionResult.SUCCESS;
           } else if (state.getBlock() == Blocks.CRACKED_DEEPSLATE_BRICKS) {
               stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
               level.setBlock(pos, Blocks.DEEPSLATE_BRICKS.defaultBlockState(), 3);
               level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
               return InteractionResult.SUCCESS;
           } else if (state.getBlock() == Blocks.CRACKED_NETHER_BRICKS) {
               stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
               level.setBlock(pos, Blocks.NETHER_BRICKS.defaultBlockState(), 3);
               level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
               return InteractionResult.SUCCESS;
           } else if (state.getBlock() == Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS) {
               stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
               level.setBlock(pos, Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), 3);
               level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
               return InteractionResult.SUCCESS;
           }
       }
        // Crushing Behavior and Logic
        if (block == Blocks.COCOA) {
            int age = state.getValue(CocoaBlock.AGE);
            if (age < 2) {
                return super.useOn(context);
            }
        }
        if (block == Blocks.WHEAT) {
            int age = state.getValue(CropBlock.AGE);
            if (age < 7) {
                return super.useOn(context);
            }
        }
        if (CRUSH_RESULTS.containsKey(block)) {
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            // Right-Click cooldown = 75% of normal break time
            float hardness = state.getDestroySpeed(level, pos);
            int baseTicks = (int) (hardness * 1.5f * 20f);
            int crushTicks = Math.max(1, (int) (baseTicks * 0.75f));
            player.getCooldowns().addCooldown(this, crushTicks);

            level.destroyBlock(pos, false);
            ItemLike result = CRUSH_RESULTS.get(block);

            // Calculate base amount of for Cocoa or other items
            int baseAmount = (block == Blocks.COCOA) ? 2 : 1;

            // Handles Fortune enchant
            int fortuneLevel = player != null ? EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack) : 0;
            int extraAmount = calculateFortuneBonus(fortuneLevel, level.getRandom());
            int totalAmount = baseAmount + extraAmount;

            Block.popResource(level, pos, new ItemStack(result, totalAmount));

            //squish noise when crushing fruits
            if (block == Blocks.PUMPKIN || block == Blocks.MELON || block == Blocks.CARVED_PUMPKIN || block == Blocks.COCOA) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            //crop noise for crushing grains
            } else if (block == Blocks.SUGAR_CANE || block == Blocks.WHEAT) {
                level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.CONSUME;
        }
        return super.useOn(context);
    }
    private int calculateFortuneBonus(int fortuneLevel, RandomSource random) {
        if (fortuneLevel <= 0) {
            return 0;
        }
        int bonus = 0;
        for (int i = 0; i < fortuneLevel; i++) {
            if (random.nextFloat() < 0.2f) {
                bonus++;
            }
        }
        return bonus;
    }
}