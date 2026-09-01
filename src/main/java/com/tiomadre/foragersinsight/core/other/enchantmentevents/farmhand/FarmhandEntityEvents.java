package com.tiomadre.foragersinsight.core.other.enchantmentevents.farmhand;

import com.tiomadre.foragersinsight.common.item.HandbasketItem;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIEnchantments;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FarmhandEntityEvents {

    @SubscribeEvent
    public static void onEntityShear(EntityInteractSpecific event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack tool = player.getItemInHand(hand);
        if (!(tool.getItem() instanceof ShearsItem)) return;
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND.get()) <= 0) return;

        Entity target = event.getTarget();
        if (!(target instanceof Shearable shearable)) return;
        if (!shearable.readyForShearing()) return;

        event.setCanceled(true);

        shearable.shear(SoundSource.PLAYERS);

        for (ItemEntity dropEntity : level.getEntitiesOfClass(ItemEntity.class, target.getBoundingBox().inflate(1.5D))) {
            if (!dropEntity.isAlive()) continue;
            ItemStack drop = dropEntity.getItem().copy();
            if (!tryInsertToHandbasket(player, drop)) {
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }
            }
            dropEntity.discard();
        }

        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }

    //Insert into Handbasket
    private static boolean tryInsertToHandbasket(Player player, ItemStack drop) {
        IItemHandler selectedHandler = null;
        int selectedUsedSlots = -1;
        boolean selectedHasItems = false;

        for (ItemStack invStack : player.getInventory().items) {
            if (!(invStack.getItem() instanceof HandbasketItem)) {
                continue;
            }
            LazyOptional<IItemHandler> cap = invStack.getCapability(ForgeCapabilities.ITEM_HANDLER);
            Optional<IItemHandler> resolved = cap.resolve();
            if (resolved.isEmpty()) {
                continue;
            }
            IItemHandler handler = resolved.get();
            int totalSlots = handler.getSlots();
            int usedSlots = 0;
            for (int slot = 0; slot < totalSlots; slot++) {
                if (!handler.getStackInSlot(slot).isEmpty()) {
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
                selectedHandler = handler;
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
}