package com.doltandtio.foragersinsight.core.other.enchantmentevents.farmhand;

import com.doltandtio.foragersinsight.common.block.BountifulLeavesBlock;
import com.doltandtio.foragersinsight.common.block.RoseCropBlock;
import com.doltandtio.foragersinsight.common.item.HandbasketItem;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FarmhandEvents {
    //checks if player has a handbasket and if item can go in
    private static boolean tryInsertToHandbasket(Player player, ItemStack drop) {
        for (ItemStack invStack : player.getInventory().items) {
            if (invStack.getItem() instanceof HandbasketItem) {
                LazyOptional<IItemHandler> cap = invStack.getCapability(ForgeCapabilities.ITEM_HANDLER);
                Optional<IItemHandler> resolved = cap.resolve();
                if (resolved.isPresent()) {
                    IItemHandler handler = resolved.get();
                    ItemStack remainder = drop.copy();

                    for (int slot = 0; slot < handler.getSlots(); slot++) {
                        remainder = handler.insertItem(slot, remainder, true);
                        if (remainder.isEmpty()) break;
                    }
                    if (remainder.isEmpty()) {

                        ItemStack toInsert = drop.copy();
                        for (int slot = 0; slot < handler.getSlots(); slot++) {
                            toInsert = handler.insertItem(slot, toInsert, false);
                            if (toInsert.isEmpty()) break;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @SubscribeEvent
    public static void onCropBreak(BlockEvent.BreakEvent event) {
        Level level = event.getLevel() instanceof Level ? (Level) event.getLevel() : null;
        Player player = event.getPlayer();
        if (level == null || level.isClientSide() || player == null) return;

        ItemStack tool = player.getMainHandItem();
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND.get()) <= 0) return;

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
        List<ItemStack> drops = Block.getDrops(state, server, pos, server.getBlockEntity(pos));

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
        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
    }
    // Shearable on Left Click
    @SubscribeEvent
    public static void onShearLeft(LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof ShearsItem)) return;
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND.get()) <= 0) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof IForgeShearable shearable)) return;
        if (!shearable.isShearable(tool, level, pos)) return;

        event.setCanceled(true);
        ServerLevel server = (ServerLevel) level;
        Collection<ItemStack> drops = shearable.onSheared(player, tool, server, pos, 0);

        server.destroyBlock(pos, false);
        for (ItemStack drop : drops) {
            if (!tryInsertToHandbasket(player, drop)) {
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }
            }
        }
        level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0f, 1.0f);
        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
    }
    // Shearable on Right Click
    @SubscribeEvent
    public static void onShearRight(RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof ShearsItem)) return;
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND.get()) <= 0) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        // Bountiful Leaves
        if (block instanceof BountifulLeavesBlock bountiful && state.getValue(BountifulLeavesBlock.AGE) >= BountifulLeavesBlock.MAX_AGE) {
            event.setCanceled(true);
            ItemStack drop = new ItemStack(bountiful.getBounty());
            if (!tryInsertToHandbasket(player, drop)) player.drop(drop, false);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.setValue(BountifulLeavesBlock.AGE, 0), Block.UPDATE_ALL);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
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
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            return;
        }

        // Beehive/nest
        if (state.hasProperty(BeehiveBlock.HONEY_LEVEL) && state.getValue(BeehiveBlock.HONEY_LEVEL) >= 5) {
            event.setCanceled(true);
            ItemStack honey = new ItemStack(Items.HONEYCOMB, 3);
            if (!tryInsertToHandbasket(player, honey)) player.drop(honey, false);
            level.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.setValue(BeehiveBlock.HONEY_LEVEL, 0), Block.UPDATE_ALL);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            return;
        }
        // Mushroom Colony
        if (block instanceof MushroomColonyBlock colony && state.getValue(MushroomColonyBlock.COLONY_AGE) > 0) {
            event.setCanceled(true);
            ItemStack drop = colony.getCloneItemStack(level, pos, state);
            if (!tryInsertToHandbasket(player, drop)) player.drop(drop, false);
            level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.setValue(MushroomColonyBlock.COLONY_AGE, state.getValue(MushroomColonyBlock.COLONY_AGE) - 1), Block.UPDATE_ALL);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }
    }
}
