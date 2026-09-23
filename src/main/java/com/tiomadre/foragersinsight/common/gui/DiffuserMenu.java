package com.tiomadre.foragersinsight.common.gui;

import com.tiomadre.foragersinsight.common.block.entity.DiffuserBlockEntity;
import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.core.registry.FIAdvancements;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIMenuTypes;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class DiffuserMenu extends AbstractContainerMenu {
    private static final int INPUT_SLOT_COUNT = DiffuserBlockEntity.INPUT_SLOT_COUNT;
    private static final int ENHANCEMENT_SLOT_INDEX = DiffuserBlockEntity.ENHANCEMENT_SLOT_INDEX;
    private static final int RESULT_SLOT_INDEX = DiffuserBlockEntity.RESULT_SLOT_INDEX;
    private static final int DIFFUSER_SLOT_COUNT = RESULT_SLOT_INDEX + 1;
    private static final int PLAYER_INVENTORY_ROWS = 3;
    private static final int PLAYER_INVENTORY_COLUMNS = 9;
    private static final int DATA_PROGRESS = 2;
    private static final int DATA_TOTAL = 3;
    public static final int BUTTON_POWER = 0;

    private static final int SLOT_SIZE = 18;
    private static final int SLOT_SPACING = SLOT_SIZE;
    private static final int INPUT_SLOT_Y = 41;
    private static final int INPUT_SLOT_START_X = 34;
    private static final int ENHANCEMENT_SLOT_X = INPUT_SLOT_START_X + SLOT_SPACING;
    private static final int ENHANCEMENT_SLOT_Y = 21;
    private static final int RESULT_SLOT_X = 126;
    private static final int RESULT_SLOT_Y = 37;
    private static final int INV_START_X = 8;
    private static final int INV_START_Y = 89;
    private static final int HOTBAR_Y = INV_START_Y + PLAYER_INVENTORY_ROWS * SLOT_SIZE + 4;
    private static final int ARROW_PROGRESS_PIXELS = 22;


    public final DiffuserBlockEntity diffuser;
    private final Level level;
    private final ContainerData data;
    private final ContainerLevelAccess access;


    public DiffuserMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(containerId, playerInventory, playerInventory.player.level().getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(4));
    }

    public DiffuserMenu(int containerId, Inventory playerInventory, BlockEntity entity, ContainerData data) {
        super(FIMenuTypes.DIFFUSER_MENU.get(), containerId);
        this.diffuser = ((DiffuserBlockEntity) entity);
        this.level = playerInventory.player.level();
        this.access = ContainerLevelAccess.create(level, this.diffuser.getBlockPos());
        this.data = data;


        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addDiffuserSlots();

//        checkContainerSize(this.diffuserContainer, DIFFUSER_SLOT_COUNT);
//        this.diffuserContainer.startOpen(playerInventory.player);
//

//
//        this.addDataSlots(this.dataAccess);
    }


    private void addDiffuserSlots() {
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            int x = INPUT_SLOT_START_X + slot * SLOT_SPACING;
            this.addSlot(new SlotItemHandler(diffuser.itemHandler, slot, x, INPUT_SLOT_Y) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return !DiffuserMenu.this.diffuser.isBurning() && super.mayPlace(stack);
                }
                @Override
                public int getMaxStackSize() {
                    return 1;
                }
            });
        }
        this.addSlot(new SlotItemHandler(diffuser.itemHandler, ENHANCEMENT_SLOT_INDEX, ENHANCEMENT_SLOT_X, ENHANCEMENT_SLOT_Y) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return !DiffuserMenu.this.diffuser.isBurning()
                        && DiffuserBlockEntity.Enhancement.fromStack(stack) != DiffuserBlockEntity.Enhancement.NONE;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new SlotItemHandler(diffuser.itemHandler, RESULT_SLOT_INDEX, RESULT_SLOT_X, RESULT_SLOT_Y) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(@NotNull Player player) {
                return false;
            }
        });
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        if (index < DIFFUSER_SLOT_COUNT) {
            if (!this.moveItemStackTo(sourceStack, DIFFUSER_SLOT_COUNT, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (DiffuserBlockEntity.Enhancement.fromStack(sourceStack) != DiffuserBlockEntity.Enhancement.NONE) {
                if (!this.moveItemStackTo(sourceStack, ENHANCEMENT_SLOT_INDEX, ENHANCEMENT_SLOT_INDEX + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(sourceStack, 0, INPUT_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        return copy;
    }

    public int getCraftProgress() {
        int progress = this.data.get(DATA_PROGRESS);
        int total = this.data.get(DATA_TOTAL);
        if (total <= 0) {
            return 0;
        }
        return Mth.clamp((int) ((long) progress * ARROW_PROGRESS_PIXELS / total), 0, ARROW_PROGRESS_PIXELS);
    }

    public Optional<FIDiffuserRecipe> getActiveScent() {
        return this.diffuser.getActiveScent();
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    public double getEffectiveRadius() {
        return this.diffuser.getEffectiveRadius();
    }
    public int getRemainingDuration() {
        return this.diffuser.getRemainingDuration();
    }

    public DiffuserBlockEntity.Enhancement getActiveEnhancement() {
        return this.diffuser.getActiveEnhancement();
    }



    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, FIBlocks.DIFFUSER.get());
    }
    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (id == BUTTON_POWER) {
            this.access.execute((level, pos) -> {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof DiffuserBlockEntity diffuser) {
                    if (diffuser.isLit()) {
                        diffuser.endScentEffect();
                    }
                    else if (diffuser.tryStartDiffusion()) {
                        level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F,
                                level.random.nextFloat() * 0.4F + 0.8F);
                        if (player instanceof ServerPlayer serverPlayer) {
                            FIAdvancements.SIMPLE_TRIGGER.get().trigger(serverPlayer);
                            diffuser.getActiveScent()
                                    .filter(scent -> scent.getMobEffect().equals(FIMobEffects.ODOROUS))
                                    .ifPresent(scent -> FIAdvancements.SIMPLE_TRIGGER.get().trigger(serverPlayer));
                        }

                    }
                }
            });
            return true;
        }
        return super.clickMenuButton(player, id);
    }

}