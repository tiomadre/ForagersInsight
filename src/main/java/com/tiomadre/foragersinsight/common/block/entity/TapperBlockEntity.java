package com.tiomadre.foragersinsight.common.block.entity;

import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TapperBlockEntity extends BlockEntity {
    private static final String TAPPER_KEY = "Tapper";

    private ItemStackHandler tapperStack = new ItemStackHandler(1) {
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

    public TapperBlockEntity(BlockPos pos, BlockState state) {
        super(FIBlockEntityTypes.TAPPER.get(), pos, state);
    }

    public void setTapperStack(ItemStack stack) {
        tapperStack.setStackInSlot(1, stack);
        setChanged();
    }

    public @NotNull ItemStack getTapperStack() {
        if (tapperStack.getStackInSlot(1).isEmpty()) {
            return new ItemStack(FIItems.TAPPER.get());
        }
        return tapperStack.getStackInSlot(1).copy();
    }

    public int getFireAspectLevel(Level level) {
        return getTapperStack().getEnchantmentLevel(level.holderOrThrow(Enchantments.FIRE_ASPECT));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(TAPPER_KEY,tapperStack.serializeNBT(registries));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tapperStack.deserializeNBT(registries, tag.getCompound(TAPPER_KEY));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}