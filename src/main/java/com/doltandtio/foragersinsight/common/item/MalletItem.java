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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.utility.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class MalletItem extends PickaxeItem {
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
        // Sneak + Right Click for anvil
            if (player != null && player.isShiftKeyDown()) {
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack) > 0) {
                    // no fix, if no need
                    if (state.getBlock() == Blocks.ANVIL) {
                        if (player != null) {
                            // display message
                            player.displayClientMessage(
                                    TextUtils.getTranslation("tool_interaction.mallet.anvil_no_repairs"), true
                            );}
                        return InteractionResult.FAIL;
                    }
                    // 20 durability damage on repair
                    stack.hurtAndBreak(20, player, p -> p.broadcastBreakEvent(context.getHand()));
                    BlockState repairedState = Blocks.ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING));
                    if (state.getBlock() == Blocks.CHIPPED_ANVIL) {
                        repairedState = Blocks.ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING));
                    } else if (state.getBlock() == Blocks.DAMAGED_ANVIL) {
                        repairedState = Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING));
                    }
                    level.setBlock(pos, repairedState, 3);
                    level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
                }}}
    // Right Click for Cracked Bricks, 2 durability damage on repair
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
            } else if (state.getBlock() == Blocks.CRACKED_DEEPSLATE_TILES) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, Blocks.DEEPSLATE_TILES.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
           }}
    // Crushing Behavior and Logic
        //Sugar Cane
        if (block == Blocks.SUGAR_CANE) {
            while (level.getBlockState(pos.above()).getBlock() == Blocks.SUGAR_CANE) {
                pos = pos.above();
            }
            if (level.getBlockState(pos.below()).getBlock() != Blocks.SUGAR_CANE) {
                return super.useOn(context);
            }
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); // Update server and client
            Block.popResource(level, pos, new ItemStack(Items.SUGAR, 2)); // Drop items

            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        // Cocoa
        if (block == Blocks.COCOA) {
            int age = state.getValue(CocoaBlock.AGE);
            if (age < 2) {
                return super.useOn(context);}}
        // Wheat
        if (block == Blocks.WHEAT) {
            int age = state.getValue(CropBlock.AGE);
            if (age < 7) {
                return super.useOn(context);}}
        // Squish Noises
        if (CRUSH_RESULTS.containsKey(block)) {
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
            if (block == Blocks.PUMPKIN || block == Blocks.MELON || block == Blocks.CARVED_PUMPKIN || block == Blocks.COCOA) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (block == Blocks.SUGAR_CANE || block == Blocks.WHEAT) {
                level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);}
    // Crush Cooldown = 60% of normal break time
            float hardness = state.getDestroySpeed(level, pos);
            int baseTicks = (int) (hardness * 1.5f * 20f);
            int crushTicks = Math.max(1, (int) (baseTicks * 0.6f));
            player.getCooldowns().addCooldown(this, crushTicks);
            level.destroyBlock(pos, false);
            ItemLike result = CRUSH_RESULTS.get(block);
            int baseAmount = (block == Blocks.COCOA) ? 2 : 1;
    // Fortune (increase crush drops)
            int fortuneLevel = player != null ? EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack) : 0;
            int extraAmount = calculateFortuneBonus(fortuneLevel, level.getRandom());
            int totalAmount = baseAmount + extraAmount;
            Block.popResource(level, pos, new ItemStack(result, totalAmount));
        }return super.useOn(context);}
            private int calculateFortuneBonus(int fortuneLevel, RandomSource random) {
             if (fortuneLevel <= 0) {
            return 0;}
            int bonus = 0;
            for (int i = 0; i < fortuneLevel; i++) {
            if (random.nextFloat() < 0.2f) {
                bonus++;
            }}return bonus;}}