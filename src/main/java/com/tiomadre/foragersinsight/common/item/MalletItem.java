package com.tiomadre.foragersinsight.common.item;

import com.tiomadre.foragersinsight.common.utility.TextUtils;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.tiomadre.foragersinsight.core.other.toolevents.ShearsSnipInteractions.dropItemInFront;

public class MalletItem extends PickaxeItem {
    private static final Map<Block, CrushResult> CRUSH_RESULTS = new HashMap<>();
    private static final Map<Block, Block> CRUSH_TRANSFORM_RESULTS = new HashMap<>();
    private static final Map<Block, Block> CRACKED_TO_NORMAL_BLOCKS = Map.of(

            Blocks.CRACKED_STONE_BRICKS, Blocks.STONE_BRICKS,
            Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICKS,
            Blocks.CRACKED_NETHER_BRICKS, Blocks.NETHER_BRICKS,
            Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS,
            Blocks.CRACKED_DEEPSLATE_TILES, Blocks.DEEPSLATE_TILES
    );
    private static final Set<Block> CRACK_IT_SOURCE_BLOCKS = Set.of(
            Blocks.STONE_BRICKS,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.NETHER_BRICKS,
            Blocks.POLISHED_BLACKSTONE_BRICKS,
            Blocks.DEEPSLATE_TILES
    );

    static {
        // Ices
        addCrushResult(Blocks.BLUE_ICE, Blocks.PACKED_ICE, 1);
        addCrushResult(Blocks.PACKED_ICE, Blocks.ICE, 1);
        addCrushResult(Blocks.ICE, FIItems.CRUSHED_ICE.get(), 2);
        // Flint and Gravel
        addCrushResult(Blocks.COBBLESTONE, Blocks.GRAVEL, 1);
        addCrushResult(Blocks.GRAVEL, Items.FLINT, 1);
        // Sand and Glass
        addCrushResult(Blocks.GLASS, Blocks.SAND, 1);
        addCrushResult(Blocks.SANDSTONE, Blocks.SAND, 1);
        //Condensed Blocks
        addCrushTransformResult(Blocks.DIRT, FIBlocks.CONDENSED_DIRT.get());
        addCrushTransformResult(Blocks.SAND, FIBlocks.CONDENSED_SAND.get());
        // Cracked Stone and Bricks
        addCrushTransformResult(Blocks.STONE, Blocks.COBBLESTONE);
        addCrushTransformResult(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        addCrushTransformResult(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        addCrushTransformResult(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        addCrushTransformResult(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        addCrushTransformResult(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);
        // Fruit and Grain
        addCrushResult(Blocks.PUMPKIN, Items.PUMPKIN_SEEDS, 2);
        addCrushResult(Blocks.MELON, Items.MELON_SEEDS, 2);
        addCrushResult(Blocks.CARVED_PUMPKIN, Items.PUMPKIN_SEEDS, 1);
        addCrushResult(Blocks.COCOA, FIItems.COCOA_POWDER.get(), 2);
        addCrushResult(Blocks.WHEAT, FIItems.WHEAT_FLOUR.get(), 2);
    }

    private static void addCrushResult(Block block, ItemLike item, int baseAmount) {
        CRUSH_RESULTS.put(block, new CrushResult(item, baseAmount));
    }
    private static void addCrushTransformResult(Block sourceBlock, Block targetBlock) {
        CRUSH_TRANSFORM_RESULTS.put(sourceBlock, targetBlock);
    }

    private record CrushResult(ItemLike item, int baseAmount) {
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
        int mendingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack);

        // Mending (Repair Interactions)
        if (block == Blocks.ANVIL || block == Blocks.CHIPPED_ANVIL || block == Blocks.DAMAGED_ANVIL) {
            if (player != null && player.isShiftKeyDown() && mendingLevel > 0) {
                if (block == Blocks.ANVIL) {
                    player.displayClientMessage(
                            TextUtils.getTranslation("tool_interaction.mallet.anvil_no_repairs"), true);
                    return InteractionResult.FAIL;
                }
                stack.hurtAndBreak(20, player, p -> p.broadcastBreakEvent(context.getHand()));
                BlockState repairedState = (block == Blocks.CHIPPED_ANVIL)
                        ? Blocks.ANVIL.defaultBlockState()
                        : Blocks.CHIPPED_ANVIL.defaultBlockState();
                repairedState = repairedState.setValue(HorizontalDirectionalBlock.FACING,
                        state.getValue(HorizontalDirectionalBlock.FACING));
                level.setBlock(pos, repairedState, 3);
                level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (player instanceof ServerPlayer serverPlayer) {
                    FIAdvancementCriteria.UH_FIX_IT.trigger(serverPlayer);
                }
                return InteractionResult.SUCCESS;
            }
        }

        // Cracked block repair interactions (require Mending)
        if (player != null && mendingLevel > 0) {
            Block repairedBlock = CRACKED_TO_NORMAL_BLOCKS.get(block);
            if (repairedBlock != null) {
                stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));
                level.setBlock(pos, repairedBlock.defaultBlockState(), 3);
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
            if (player == null) return InteractionResult.PASS;

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
        Block transformedBlock = CRUSH_TRANSFORM_RESULTS.get(block);
        if (transformedBlock != null) {
            if (player == null) return InteractionResult.PASS;

            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            float hardness = state.getDestroySpeed(level, pos);
            int baseTicks = (int) (hardness * 1.5f * 20f);
            int crushTicks = Math.max(1, (int) (baseTicks * 0.6f));
            player.getCooldowns().addCooldown(this, crushTicks);

            level.setBlock(pos, transformedBlock.defaultBlockState(), 3);
            level.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (player instanceof ServerPlayer serverPlayer) {
                FIAdvancementCriteria.WILL_IT_CRUSH.trigger(serverPlayer);
                if (CRACK_IT_SOURCE_BLOCKS.contains(block)) {
                    FIAdvancementCriteria.CRACK_IT.trigger(serverPlayer);
                }
            }
            return InteractionResult.SUCCESS;
        }
        CrushResult crushResult = CRUSH_RESULTS.get(block);
        if (crushResult != null) {
            if (player == null) return InteractionResult.PASS;

            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(context.getHand()));

            if (block == Blocks.PUMPKIN || block == Blocks.MELON || block == Blocks.CARVED_PUMPKIN || block == Blocks.COCOA) {
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (block == Blocks.WHEAT) {
                level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            //Crushing CD, Scales with hardness of block
            float hardness = state.getDestroySpeed(level, pos);
            int baseTicks = (int) (hardness * 1.5f * 20f);
            int crushTicks = Math.max(1, (int) (baseTicks * 0.6f));
            player.getCooldowns().addCooldown(this, crushTicks);

            level.destroyBlock(pos, false);

            ItemLike resultItem = crushResult.item();
            int baseAmount = crushResult.baseAmount();

            // Fortune (increase crush drops)
            int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack);
            int extraAmount = calculateFortuneBonus(fortuneLevel, level.getRandom());
            int totalAmount = baseAmount + extraAmount;

            dropItemInFront(level, player, new ItemStack(resultItem, totalAmount));
            if (player instanceof ServerPlayer serverPlayer) {
                FIAdvancementCriteria.WILL_IT_CRUSH.trigger(serverPlayer);
                if (CRACK_IT_SOURCE_BLOCKS.contains(block)) {
                    FIAdvancementCriteria.CRACK_IT.trigger(serverPlayer);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    private static int calculateFortuneBonus(int fortuneLevel, RandomSource random) {
        if (fortuneLevel <= 0) return 0;
        int bonus = 0;
        for (int i = 0; i < fortuneLevel; i++) {
            if (random.nextFloat() < 0.2f) bonus++;
        }
        return bonus;
    }
}
