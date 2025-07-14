package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.common.block.TapperBlock;
import com.doltandtio.foragersinsight.core.registry.FIEnchantments;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid=ForagersInsight.MOD_ID)
public class FIEvents {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockHitResult hit = event.getHitVec();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        BlockPos clickedPos = event.getPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        ItemStack heldStack = player.getItemInHand(hand);

        if (heldStack.is(FIItems.TAPPER.get())) {
            if (clickedState.is(FIBlocks.SAPPY_BIRCH_LOG.get())) {
                BlockPos placePos = clickedPos.relative(hit.getDirection());

                if (level.getBlockState(placePos).canBeReplaced()) {
                    BlockState tapperState = FIBlocks.TAPPER.get().defaultBlockState()
                            .setValue(TapperBlock.HAS_TAPPER, false)
                            .setValue(TapperBlock.FILL, 0);

                    if (!level.isClientSide) {
                        level.setBlock(placePos, tapperState, 3);
                        level.playSound(null, placePos,
                                clickedState.getSoundType().getPlaceSound(),
                                SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    }

                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                    event.setCanceled(true);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        ItemStack tool = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(FIEnchantments.FARMHAND.get(), tool);
        if (level <= 0) return;

        ServerLevel levelWorld = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        BlockEntity blockEntity = levelWorld.getBlockEntity(pos);

        event.setCanceled(true);
        levelWorld.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);

        List<ItemStack> drops = Block.getDrops(state, levelWorld, pos, blockEntity, player, tool);
        for (ItemStack drop : drops) {
            if (!player.getInventory().add(drop)) {
                player.drop(drop, false);
            }
        }

        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
    }

}
