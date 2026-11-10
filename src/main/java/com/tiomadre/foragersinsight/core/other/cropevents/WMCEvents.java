package com.tiomadre.foragersinsight.core.other.cropevents;

import com.tiomadre.foragersinsight.common.block.WallMushroomBlock;
import com.tiomadre.foragersinsight.common.block.WallMushroomColonyBlock;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@EventBusSubscriber(modid = ForagersInsight.MOD_ID)
public class WMCEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlaceMushroomOnWall(PlayerInteractEvent.RightClickBlock event) {
        Direction face = event.getFace();
        if (face == null || !face.getAxis().isHorizontal()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        Level level = event.getLevel();
        Player player = event.getEntity();
        BlockPos placePos = event.getPos().relative(face);
        BlockState stateAtPlacePos = level.getBlockState(placePos);
        if (!stateAtPlacePos.canBeReplaced()) {
            return;
        }

        BlockState wallState = getWallPlacementState(stack.getItem(), face);
        if (wallState == null || !wallState.canSurvive(level, placePos) || !level.mayInteract(player, placePos)) {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));

        if (level.isClientSide) {
            return;
        }

        level.setBlock(placePos, wallState, Block.UPDATE_ALL);
        SoundType sound = wallState.getSoundType(level, placePos, player);
        level.playSound(null, placePos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    private static BlockState getWallPlacementState(Item item, Direction face) {
        DeferredBlock<Block> wallMushroom = getWallMushroom(item);
        if (wallMushroom != null) {
            return wallMushroom.get().defaultBlockState().setValue(WallMushroomBlock.FACING, face);
        }

        DeferredBlock<Block> wallColony = getWallColony(item);
        if (wallColony != null) {
            return wallColony.get().defaultBlockState()
                    .setValue(WallMushroomColonyBlock.FACING, face)
                    .setValue(MushroomColonyBlock.COLONY_AGE, MushroomColonyBlock.COLONY_AGE.getPossibleValues().size() - 1);
        }

        return null;
    }

    private static DeferredBlock<Block> getWallMushroom(Item item) {
        if (item == Items.RED_MUSHROOM) {
            return FIBlocks.WALL_RED_MUSHROOM;
        }
        if (item == Items.BROWN_MUSHROOM) {
            return FIBlocks.WALL_BROWN_MUSHROOM;
        }
        if (item == FIItems.BLEWIT_MUSHROOM.get()) {
            return FIBlocks.WALL_BLEWIT_MUSHROOM;
        }
        return null;
    }

    private static DeferredBlock<Block> getWallColony(Item item) {
        if (item == ModBlocks.RED_MUSHROOM_COLONY.get().asItem()) {
            return FIBlocks.WALL_RED_MUSHROOM_COLONY;
        }
        if (item == ModBlocks.BROWN_MUSHROOM_COLONY.get().asItem()) {
            return FIBlocks.WALL_BROWN_MUSHROOM_COLONY;
        }
        if (item == FIBlocks.BLEWIT_MUSHROOM_COLONY.get().asItem()) {
            return FIBlocks.WALL_BLEWIT_MUSHROOM_COLONY;
        }
        return null;
    }
}