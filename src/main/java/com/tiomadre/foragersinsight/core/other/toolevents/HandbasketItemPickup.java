package com.tiomadre.foragersinsight.core.other.toolevents;

import com.tiomadre.foragersinsight.common.item.HandbasketItem;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandbasketItemPickup {
    //@SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent ev) {
        Player player = ev.getPlayer();
        if (!(player instanceof ServerPlayer server)) return;

        IItemHandler selectedHandler = null;
        int selectedUsedSlots = -1;

        for (ItemStack stack : server.getInventory().items) {
            if (!(stack.getItem() instanceof HandbasketItem)) continue;

            IItemHandler handler = stack.getCapability(Capabilities.ItemHandler.ITEM);
            if (handler == null) continue;

            int usedSlots = countUsedSlots(handler);
            if (usedSlots >= handler.getSlots()) continue;

            if (isBetterBasket(usedSlots, selectedUsedSlots)) {
                selectedHandler = handler;
                selectedUsedSlots = usedSlots;
            }
        }

        if (selectedHandler == null) return;
        ItemEntity entity = ev.getItemEntity();
        ItemStack stack = entity.getItem();
        if (!stack.is(FITags.ItemTag.HANDBASKET_ALLOWED)) return;

        ItemStack remainder = ItemHandlerHelper.insertItem(selectedHandler, stack.copy(), false);
        if (remainder.getCount() == stack.getCount()) return;

        //come back to this if there is a problem

        //ev.setCanceled(true);
        playRustleSound(player);
        if (remainder.isEmpty()) {
            entity.discard();
        } else {
            entity.setItem(remainder);
        }
    }

    private static int countUsedSlots(IItemHandler handler) {
        int usedSlots = 0;
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            if (!handler.getStackInSlot(slot).isEmpty()) {
                usedSlots++;
            }
        }
        return usedSlots;
    }

    private static boolean isBetterBasket(int usedSlots, int selectedUsedSlots) {
        if (selectedUsedSlots < 0) return true;

        boolean hasItems = usedSlots > 0;
        boolean selectedHasItems = selectedUsedSlots > 0;
        return (hasItems && !selectedHasItems)
                || (hasItems == selectedHasItems && usedSlots > selectedUsedSlots);
    }

    private static void playRustleSound(Player player) {
        player.getCommandSenderWorld().playSound(null, player.blockPosition(),
                SoundEvents.AZALEA_LEAVES_STEP,
                SoundSource.PLAYERS,
                1.0F, 1.0F
        );
    }
}