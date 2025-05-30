package com.doltandtio.foragersinsight.core.other.toolevents;

import com.doltandtio.foragersinsight.common.item.HandbasketItem;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.*;
import net.minecraft.sounds.SoundSource;

@Mod.EventBusSubscriber(modid = "foragersinsight", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandbasketItemPickup {
    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent ev) {
        Player player = ev.getEntity();
        if (!(player instanceof ServerPlayer server)) return;

        // check inv for handbasket
        ItemStack basketStack = null;
        for (ItemStack stack : server.getInventory().items) {
            if (stack.getItem() instanceof HandbasketItem) {
                basketStack = stack;
                break;
            }
        }
        if (basketStack == null) {
            return;
        }
        IItemHandler inv = basketStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .orElse(null);
        if (inv == null) return;

        ItemEntity entity = ev.getItem();
        ItemStack stack = entity.getItem();
        if (!stack.is(FITags.ItemTag.HANDBASKET_ALLOWED)) return;

        ItemStack rem = ItemHandlerHelper.insertItem(inv, stack.copy(), false);
        if (rem.isEmpty()) {
            ev.setCanceled(true);
            entity.discard();
            player.getCommandSenderWorld().playSound(null, player.blockPosition(),
                    SoundEvents.AZALEA_LEAVES_PLACE,
                    SoundSource.PLAYERS,
                    1.0F,1.0F
            );
        } else if (rem.getCount() < stack.getCount()) {
            entity.setItem(rem);
            ev.setCanceled(true);
            player.getCommandSenderWorld().playSound(null, player.blockPosition(),
                    SoundEvents.AZALEA_LEAVES_PLACE,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }
    }
}