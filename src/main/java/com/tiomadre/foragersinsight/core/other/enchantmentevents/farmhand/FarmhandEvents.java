package com.tiomadre.foragersinsight.core.other.enchantmentevents.farmhand;

import com.tiomadre.foragersinsight.common.block.BountifulLeavesBlock;
import com.tiomadre.foragersinsight.common.block.RoseCropBlock;
import com.tiomadre.foragersinsight.common.item.HandbasketItem;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.items.IItemHandler;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FarmhandEvents {
    //checks if player has a handbasket and if item can go in
    private static boolean tryInsertToHandbasket(Player player, ItemStack drop) {
        IItemHandler selectedHandler = null;
        int selectedUsedSlots = -1;
        boolean selectedHasItems = false;

        for (ItemStack invStack : player.getInventory().items) {
            if (!(invStack.getItem() instanceof HandbasketItem)) {
                continue;
            }
            IItemHandler cap = invStack.getCapability(Capabilities.ItemHandler.ITEM);
            int totalSlots = cap.getSlots();
            int usedSlots = 0;
            for (int slot = 0; slot < totalSlots; slot++) {
                if (!cap.getStackInSlot(slot).isEmpty()) {
                    usedSlots++;
                }
            }
            if (usedSlots >= totalSlots) {
                continue;
            }

            boolean hasItems = usedSlots > 0;
            if (selectedHandler == null
                    || (hasItems && !selectedHasItems)
                    || (hasItems == selectedHasItems && usedSlots > selectedUsedSlots)) {
                selectedHandler = cap;
                selectedUsedSlots = usedSlots;
                selectedHasItems = hasItems;
            }
        }

        if (selectedHandler == null) {
            return false;
        }

        ItemStack remainder = drop.copy();
        for (int slot = 0; slot < selectedHandler.getSlots(); slot++) {
            remainder = selectedHandler.insertItem(slot, remainder, true);
            if (remainder.isEmpty()) break;
        }
        if (!remainder.isEmpty()) {
            return false;
        }

        ItemStack toInsert = drop.copy();
        for (int slot = 0; slot < selectedHandler.getSlots(); slot++) {
            toInsert = selectedHandler.insertItem(slot, toInsert, false);
            if (toInsert.isEmpty()) break;
        }
        return true;
    }
    @SubscribeEvent
    public static void onCropBreak(BlockEvent.BreakEvent event) {
        Level level = event.getLevel() instanceof Level ? (Level) event.getLevel() : null;
        Player player = event.getPlayer();
        if (level == null || level.isClientSide() || player == null) return;

        ItemStack tool = player.getMainHandItem();
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND) <= 0) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        Optional<IntegerProperty> agePropOpt = state.getProperties().stream()
                .filter(p -> p.getName().equals("age") && p instanceof IntegerProperty)
                .map(p -> (IntegerProperty) p)
                .findFirst();
        if (agePropOpt.isEmpty()) return;
        IntegerProperty ageProp = agePropOpt.get();

        int currentAge = state.getValue(ageProp);
        Set<Integer> possibleAges = (Set<Integer>) ageProp.getPossibleValues();
        int maxAge = possibleAges.stream().max(Integer::compareTo).orElse(currentAge);
        if (currentAge < maxAge) return;

        event.setCanceled(true);

        ServerLevel server = (ServerLevel) level;
        List<ItemStack> drops = Block.getDrops(state, server, pos, server.getBlockEntity(pos), player, tool);

        // *yoinks drops directly into inventory* 👌😊👉🎒
        for (ItemStack drop : drops) {
            if (!tryInsertToHandbasket(player, drop)) {
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }
            }
        }

        // crops like become babies again
        BlockState replanted;
        Block block = state.getBlock();

        // checks if it's a rose crop (bc double crop is weird)
        if (block instanceof RoseCropBlock) {
            replanted = ((RoseCropBlock) block)
                    .getStateForAge(0)
                    .setValue(RoseCropBlock.HALF, DoubleBlockHalf.LOWER);
        } else {
            replanted = state.setValue(ageProp, 0);
            if (replanted.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
                replanted = replanted.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER);
            }
        }
        level.setBlock(pos, replanted, Block.UPDATE_ALL);

        // hurts the tool 😞
        tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
    }
    // Shearable on Left Click
    @SubscribeEvent
    public static void onShearLeft(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof ShearsItem)) return;
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND) <= 0) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof IShearable shearable)) return;
        if (!shearable.isShearable(player, tool, level, pos)) return;

        event.setCanceled(true);
        ServerLevel server = (ServerLevel) level;
        Collection<ItemStack> drops = shearable.onSheared(player, tool, server, pos);

        server.destroyBlock(pos, false);
        for (ItemStack drop : drops) {
            if (!tryInsertToHandbasket(player, drop)) {
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }
            }
        }
        level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0f, 1.0f);
        tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
    }
    // Shearable on Right Click
    @SubscribeEvent
    public static void onShearRight(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack tool = player.getMainHandItem();
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND) <= 0) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (!(tool.getItem() instanceof ShearsItem)) {
            if (HarvestAndReplant(level, player, tool, pos, state, block)) {
                event.setCanceled(true);
            }
            return;
        }

        // Bountiful Leaves
        if (block instanceof BountifulLeavesBlock bountiful && state.getValue(BountifulLeavesBlock.AGE) >= BountifulLeavesBlock.MAX_AGE) {
            event.setCanceled(true);
            ItemStack drop = new ItemStack(bountiful.getBounty());
            if (!tryInsertToHandbasket(player, drop)) player.drop(drop, false);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.setValue(BountifulLeavesBlock.AGE, 0), Block.UPDATE_ALL);
            tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
            return;
        }
        // Pumpkin
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
        if (id != null && id.getPath().equals("pumpkin")) {
            event.setCanceled(true);
            Direction face = event.getFace() != null ? event.getFace() : Direction.NORTH;
            level.setBlock(pos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, face), Block.UPDATE_ALL);
            ItemStack seeds = new ItemStack(Items.PUMPKIN_SEEDS, 4);
            if (!tryInsertToHandbasket(player, seeds)) player.drop(seeds, false);
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
            tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
            return;
        }

        // Beehive/nest
        if (state.hasProperty(BeehiveBlock.HONEY_LEVEL) && state.getValue(BeehiveBlock.HONEY_LEVEL) >= 5) {
            event.setCanceled(true);
            ItemStack honey = new ItemStack(Items.HONEYCOMB, 3);
            if (!tryInsertToHandbasket(player, honey)) player.drop(honey, false);
            level.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.setValue(BeehiveBlock.HONEY_LEVEL, 0), Block.UPDATE_ALL);
            tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
            return;
        }
        // Mushroom Colony
        if (block instanceof MushroomColonyBlock colony && state.getValue(MushroomColonyBlock.COLONY_AGE) > 0) {
            event.setCanceled(true);
            ItemStack drop = colony.getCloneItemStack(level, pos, state);
            if (!tryInsertToHandbasket(player, drop)) player.drop(drop, false);
            level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.setValue(MushroomColonyBlock.COLONY_AGE, state.getValue(MushroomColonyBlock.COLONY_AGE) - 1), Block.UPDATE_ALL);
            tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
        }
    }
    //replant logic
    private static boolean HarvestAndReplant(Level level, Player player, ItemStack tool, BlockPos pos, BlockState state, Block block) {
        Optional<IntegerProperty> agePropOpt = state.getProperties().stream()
                .filter(p -> p.getName().equals("age") && p instanceof IntegerProperty)
                .map(p -> (IntegerProperty) p)
                .findFirst();
        if (agePropOpt.isEmpty()) return false;

        IntegerProperty ageProp = agePropOpt.get();
        int currentAge = state.getValue(ageProp);
        int maxAge = ageProp.getPossibleValues().stream().max(Integer::compareTo).orElse(currentAge);
        if (currentAge < maxAge) return false;

        ServerLevel server = (ServerLevel) level;
        List<ItemStack> drops = Block.getDrops(state, server, pos, server.getBlockEntity(pos), player, tool);
        for (ItemStack drop : drops) {
            if (!tryInsertToHandbasket(player, drop)) {
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }
            }
        }

        BlockState replanted;
        if (block instanceof RoseCropBlock roseCropBlock) {
            replanted = roseCropBlock.getStateForAge(0).setValue(RoseCropBlock.HALF, DoubleBlockHalf.LOWER);
        } else {
            replanted = state.setValue(ageProp, 0);
            if (replanted.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
                replanted = replanted.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER);
            }
        }

        level.setBlock(pos, replanted, Block.UPDATE_ALL);
        level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 0.8f, 1.0f);
        tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
        return true;
    }

}
