package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.common.block.TapperBlock;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModItems;

@Mod.EventBusSubscriber(modid=ForagersInsight.MOD_ID)
public class FIEvents {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockHitResult result = event.getHitVec();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = player.getItemInHand(hand);

        if (player.getItemInHand(hand).is(ModItems.IRON_KNIFE.get())) {
            BlockPlaceContext placeContext = new BlockPlaceContext(player, hand, stack, result);
            if (((TapperBlock) FIBlocks.TAPPER.get()).placeTapper(level, placeContext, player, state)) {
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                event.setCanceled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
