package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.core.registry.FIEnchantments;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid=ForagersInsight.MOD_ID)
public class FIEvents {
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
