package com.doltandtio.foragersinsight.common.item;

import com.doltandtio.foragersinsight.common.gui.HandbasketMenu;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.core.Direction;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HandbasketItem extends Item {
    public HandbasketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ICapabilitySerializable<CompoundTag> initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilitySerializable<>() {
            private final ItemStackHandler handler = new ItemStackHandler(10) {
                {
                    // maintains handbasket inv
                    if (nbt != null && nbt.contains("HandbasketInv")) {
                        deserializeNBT(nbt.getCompound("HandbasketInv"));
                    }
                }

                @Override
                public @NotNull ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                    // no basketception
                    if (stack.getItem() instanceof HandbasketItem) {
                        return stack;
                    }

                    //checks if its like so totally like allowed in the like handbasket!
                    if (!isItemAllowed(stack)) {
                        return stack;
                    }

                    return super.insertItem(slot, stack, simulate);
                }

                private boolean isItemAllowed(ItemStack stack) {
                    // reference dee tag
                    return stack.is(FITags.ItemTag.HANDBASKET_ALLOWED);
                }
            };

            private final LazyOptional<IItemHandler> optional = LazyOptional.of(() -> handler);

            @Override
            public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return cap == ForgeCapabilities.ITEM_HANDLER
                        ? optional.cast()
                        : LazyOptional.empty();
            }

            @Override
            public CompoundTag serializeNBT() {
                CompoundTag tag = new CompoundTag();
                tag.put("HandbasketInv", handler.serializeNBT());
                return tag;
            }

            @Override
            public void deserializeNBT(CompoundTag tag) {
                handler.deserializeNBT(tag.getCompound("HandbasketInv"));
            }
        };
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = super.getShareTag(stack);
        if (tag == null) tag = new CompoundTag();
        CompoundTag finalTag = tag;
        stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
            if (cap instanceof ItemStackHandler handler) {
                finalTag.put("HandbasketInv", handler.serializeNBT());
            }
        });
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag tag) {
        super.readShareTag(stack, tag);
        if (tag != null && tag.contains("HandbasketInv")) {
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
                if (cap instanceof ItemStackHandler handler) {
                    handler.deserializeNBT(tag.getCompound("HandbasketInv"));
                }
            });
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!world.isClientSide() && player instanceof ServerPlayer server) {
            int slotIndex = hand == InteractionHand.MAIN_HAND
                    ? player.getInventory().selected
                    : Inventory.SLOT_OFFHAND;

            NetworkHooks.openScreen(
                    server,
                    new SimpleMenuProvider(
                            (id, inv, p) -> new HandbasketMenu(id, inv, held, slotIndex),
                            Component.translatable("container.foragersinsight.handbasket")
                    ),
                    buf -> {
                        buf.writeItem(held);
                        buf.writeVarInt(slotIndex);
                    }
            );
        }
        return InteractionResultHolder.sidedSuccess(held, world.isClientSide());
    }
}
