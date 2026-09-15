package com.tiomadre.foragersinsight.common.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import com.tiomadre.foragersinsight.core.registry.FISapTrapBaits;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SapTrapBlockEntity extends BlockEntity { ;
    public final ItemStackHandler bait = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };



    public SapTrapBlockEntity(BlockPos pos, BlockState state) {
        super(FIBlockEntityTypes.SAP_TRAP.get(), pos, state);
    }

    public ItemStack getBait() {
        return this.bait.getStackInSlot(1);
    }

    public boolean hasBait() {
        return !this.bait.getStackInSlot(1).isEmpty();
    }

    public void setBait(ItemStack bait) {
       this.bait.setStackInSlot(1,bait);
    }

    public ItemStack removeBait() {
        ItemStack removed = this.bait.getStackInSlot(1);
        this.bait.setStackInSlot(1, ItemStack.EMPTY);
        this.setChanged();
        return removed;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("bait", bait.serializeNBT(registries));

    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        bait.deserializeNBT(registries, tag.getCompound("bait"));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}