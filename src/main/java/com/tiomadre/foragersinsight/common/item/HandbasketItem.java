package com.tiomadre.foragersinsight.common.item;

import com.tiomadre.foragersinsight.common.gui.HandbasketMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;


public class HandbasketItem extends Item {
    public HandbasketItem(Properties properties) {
        super(properties);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!world.isClientSide() && player instanceof ServerPlayer server) {
            int slotIndex = hand == InteractionHand.MAIN_HAND
                    ? server.getInventory().selected
                    : Inventory.SLOT_OFFHAND;

            server.openMenu(new SimpleMenuProvider(
                            (id, inv, p) -> new HandbasketMenu(id, inv),
                            Component.translatable("container.foragersinsight.handbasket")
                    ),
                    buf -> {
//                        held.OP;
                        buf.writeVarInt(slotIndex);
                    }
            );
        }
        return InteractionResultHolder.sidedSuccess(held, world.isClientSide());
    }
}
