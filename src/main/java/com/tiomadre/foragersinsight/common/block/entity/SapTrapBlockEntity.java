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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SapTrapBlockEntity extends BlockEntity {
    private static final String TAG_BAIT = "Bait";
    private ItemStack bait = ItemStack.EMPTY;


    public SapTrapBlockEntity(BlockPos pos, BlockState state) {
        super(FIBlockEntityTypes.SAP_TRAP.get(), pos, state);
    }

    public ItemStack getBait() {
        return this.bait;
    }

    public boolean hasBait() {
        return !this.bait.isEmpty();
    }

    public void setBait(ItemStack bait) {
        this.bait = bait.copyWithCount(1);
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public ItemStack removeBait() {
        ItemStack removed = this.bait;
        this.bait = ItemStack.EMPTY;
        this.setChanged();
        return removed;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.bait.isEmpty()) {
            tag.put( );
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.bait = tag.contains(TAG_BAIT) ? ItemStack. : ItemStack.EMPTY;
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