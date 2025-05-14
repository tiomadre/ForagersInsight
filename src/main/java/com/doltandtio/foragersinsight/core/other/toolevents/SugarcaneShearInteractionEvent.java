package com.doltandtio.foragersinsight.core.other.toolevents;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "foragersinsight", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SugarcaneShearInteractionEvent {
    @SubscribeEvent
    public static void onShearCrop(RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack tool = player.getItemInHand(hand);
        if (!(tool.getItem() instanceof ShearsItem)) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ServerLevel server = (ServerLevel) level;

        // Sugar Cane
        if (state.is(Blocks.SUGAR_CANE)) {
            int count = 1;
            while (level.getBlockState(pos.above(count)).is(Blocks.SUGAR_CANE)) {
                count++;
            }
            if (count >= 2) {
                event.setCanceled(true);
                BlockPos top = pos.above(count - 1);
                server.setBlock(top, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                ItemStack drop = new ItemStack(Items.SUGAR_CANE);
                if (!player.getInventory().add(drop)) player.drop(drop, false);
                level.playSound(null, top, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }

        }
    }
}
