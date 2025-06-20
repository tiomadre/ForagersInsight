package com.doltandtio.foragersinsight.common.item;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class MalletItem extends PickaxeItem {
    // Drop items in front of player
    private static void dropItemInFront(Level level, Player player, ItemStack stack) {
        Vec3 look = player.getLookAngle().normalize();
        double distance = 2.5;
        double x = player.getX() + look.x * distance;
        double y = player.getY() + 0.5;
        double z = player.getZ() + look.z * distance;
        ItemEntity drop = new ItemEntity(level, x, y, z, stack);
        level.addFreshEntity(drop);
    }
    // Crush Interactions (right-click)
    private static final Map<Block, ItemLike> CRUSH_RESULTS = new HashMap<>(); static {
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
        // Crack Stone and Bricks
        CRUSH_RESULTS.put(Blocks.STONE, Blocks.COBBLESTONE);
        CRUSH_RESULTS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        CRUSH_RESULTS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        CRUSH_RESULTS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        CRUSH_RESULTS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        CRUSH_RESULTS.put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);
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
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        // Mending (Repair Interactions)
        if (block == Blocks.ANVIL || block == Blocks.CHIPPED_ANVIL || block == Blocks.DAMAGED_ANVIL) {
            if (player != null && player.isShiftKeyDown()) {
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack) > 0) {
                    if (block == Blocks.ANVIL) {
                        player.displayClientMessage(
                                TextUtils.getTranslation("tool_interaction.mallet.anvil_no_repairs"), true);
                        return InteractionResult.FAIL;
                    }
                    stack.hurtAndBreak(20, player, p -> p.broadcastBreakEvent(context.getHand()));
                    BlockState repairedState = (block == Blocks.CHIPPED_ANVIL)
                            ? Blocks.ANVIL.defaultBlockState()
                            : Blocks.CHIPPED_ANVIL.defaultBlockState();
                    repairedState = repairedState.setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING));
                    level.setBlock(pos, repairedState, 3);
                    level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        // Cracking Interactions, 2 durability damage on use
        if (player != null) {
            if (block == Blocks.CRACKED_STONE_BRICKS) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, Blocks.STONE_BRICKS.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            } else if (block == Blocks.CRACKED_DEEPSLATE_BRICKS) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, Blocks.DEEPSLATE_BRICKS.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            } else if (block == Blocks.CRACKED_NETHER_BRICKS) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, Blocks.NETHER_BRICKS.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            } else if (block == Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            } else if (block == Blocks.CRACKED_DEEPSLATE_TILES) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, Blocks.DEEPSLATE_TILES.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }

        // Crushing Behavior and Logic
        // Sugar Cane
        if (block == Blocks.SUGAR_CANE) {
            while (level.getBlockState(pos.above()).getBlock() == Blocks.SUGAR_CANE) {
                pos = pos.above();
            }
            if (level.getBlockState(pos.below()).getBlock() != Blocks.SUGAR_CANE) {
                return super.useOn(context);
            }
            assert player != null;
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            dropItemInFront(level, player, new ItemStack(Items.SUGAR, 2));
            level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        // Cocoa
        if (block == Blocks.COCOA) {
            int age = state.getValue(CocoaBlock.AGE);
            if (age < 2) return super.useOn(context);
        }
        // Wheat
        if (block == Blocks.WHEAT) {
            int age = state.getValue(CropBlock.AGE);
            if (age < 7) return super.useOn(context);
        }
        // Squish Noises & Crushing
        if (CRUSH_RESULTS.containsKey(block)) {
            assert player != null;
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            if (block == Blocks.PUMPKIN || block == Blocks.MELON || block == Blocks.CARVED_PUMPKIN || block == Blocks.COCOA) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (block == Blocks.WHEAT) {
                level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }

            float hardness = state.getDestroySpeed(level, pos);
            int baseTicks = (int) (hardness * 1.5f * 20f);
            int crushTicks = Math.max(1, (int) (baseTicks * 0.6f));
            player.getCooldowns().addCooldown(this, crushTicks);

            level.destroyBlock(pos, false);

            ItemLike result = CRUSH_RESULTS.get(block);
            int baseAmount = (block == Blocks.COCOA) ? 2 : 1;

            // Fortune (increase crush drops)
            int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack);
            int extraAmount = calculateFortuneBonus(fortuneLevel, level.getRandom());
            int totalAmount = baseAmount + extraAmount;

            dropItemInFront(level, player, new ItemStack(result, totalAmount));
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    private int calculateFortuneBonus(int fortuneLevel, RandomSource random) {
        if (fortuneLevel <= 0) return 0;
        int bonus = 0;
        for (int i = 0; i < fortuneLevel; i++) {
            if (random.nextFloat() < 0.2f) bonus++;
        }
        return bonus;
    }
}
