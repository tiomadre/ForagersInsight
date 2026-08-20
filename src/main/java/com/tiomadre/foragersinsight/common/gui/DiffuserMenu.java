package com.tiomadre.foragersinsight.common.gui;

import com.tiomadre.foragersinsight.common.block.entity.DiffuserBlockEntity;
import com.tiomadre.foragersinsight.core.registry.FIAdvancementCriteria;
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
import org.jetbrains.annotations.NotNull;
import com.tiomadre.foragersinsight.data.server.recipes.FIDiffusingRecipes;

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


    private final Container diffuserContainer;
    private final DiffuserBlockEntity diffuser;
    private final ContainerLevelAccess access;
    private final ContainerData dataAccess;

    public DiffuserMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(containerId, playerInventory, resolveBlockEntity(playerInventory, buffer));
    }

    public DiffuserMenu(int containerId, Inventory playerInventory, DiffuserBlockEntity diffuser) {
        super(FIMenuTypes.DIFFUSER_MENU.get(), containerId);
        this.diffuser = Objects.requireNonNull(diffuser, "diffuser");
        this.diffuserContainer = this.diffuser;
        Level level = this.diffuser.getLevel();
        this.access = level != null ? ContainerLevelAccess.create(level, this.diffuser.getBlockPos()) : ContainerLevelAccess.NULL;
        this.dataAccess = this.diffuser.getDataAccess();

        checkContainerSize(this.diffuserContainer, DIFFUSER_SLOT_COUNT);
        this.diffuserContainer.startOpen(playerInventory.player);

        addDiffuserSlots();
        addPlayerInventorySlots(playerInventory);

        this.addDataSlots(this.dataAccess);
    }

    private static DiffuserBlockEntity resolveBlockEntity(Inventory playerInventory, FriendlyByteBuf buffer) {
        Objects.requireNonNull(playerInventory, "playerInventory");
        Objects.requireNonNull(buffer, "buffer");

        BlockPos pos = buffer.readBlockPos();
        Level level = playerInventory.player.level();

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DiffuserBlockEntity diffuser) {
            return diffuser;
        }
        return null;
    }
    private void addDiffuserSlots() {
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            int x = INPUT_SLOT_START_X + slot * SLOT_SPACING;
            this.addSlot(new Slot(this.diffuserContainer, slot, x, INPUT_SLOT_Y) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return !DiffuserMenu.this.diffuser.hasActiveScent() && super.mayPlace(stack);
                }
                @Override
                public int getMaxStackSize() {
                    return 1;
                }
            });
        }
        this.addSlot(new Slot(this.diffuserContainer, ENHANCEMENT_SLOT_INDEX, ENHANCEMENT_SLOT_X, ENHANCEMENT_SLOT_Y) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return !DiffuserMenu.this.diffuser.hasActiveScent()
                        && DiffuserBlockEntity.Enhancement.fromStack(stack) != DiffuserBlockEntity.Enhancement.NONE;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new Slot(this.diffuserContainer, RESULT_SLOT_INDEX, RESULT_SLOT_X, RESULT_SLOT_Y) {
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

    private void addPlayerInventorySlots(Inventory playerInventory) {
        for (int row = 0; row < PLAYER_INVENTORY_ROWS; row++) {
            for (int column = 0; column < PLAYER_INVENTORY_COLUMNS; column++) {
                int slotIndex = column + row * PLAYER_INVENTORY_COLUMNS + PLAYER_INVENTORY_COLUMNS;
                int x = INV_START_X + column * SLOT_SIZE;
                int y = INV_START_Y + row * SLOT_SIZE;
                this.addSlot(new Slot(playerInventory, slotIndex, x, y));
            }
        }

        for (int column = 0; column < PLAYER_INVENTORY_COLUMNS; column++) {
            int x = INV_START_X + column * SLOT_SIZE;
            this.addSlot(new Slot(playerInventory, column, x, HOTBAR_Y));
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
        int progress = this.dataAccess.get(DATA_PROGRESS);
        int total = this.dataAccess.get(DATA_TOTAL);
        if (total <= 0) {
            return 0;
        }
        return Mth.clamp((int) ((long) progress * ARROW_PROGRESS_PIXELS / total), 0, ARROW_PROGRESS_PIXELS);
    }

    public Optional<FIDiffusingRecipes> getActiveScent() {
        return this.diffuser.getActiveScent();
    }

    public boolean isLit() {
        return this.dataAccess.get(0) > 0;
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
                        diffuser.extinguish();
                    } else if (diffuser.tryStartDiffusion()) {
                        level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F,
                                level.random.nextFloat() * 0.4F + 0.8F);
                        if (player instanceof ServerPlayer serverPlayer) {
                            FIAdvancementCriteria.SCENTSATIONAL.trigger(serverPlayer);
                            diffuser.getActiveScent()
                                    .filter(scent -> scent.usesEffect(FIMobEffects.ODOROUS.get()))
                                    .ifPresent(scent -> FIAdvancementCriteria.STINKY_SITUATION.trigger(serverPlayer));
                        }
                    } }
            });
            return true;
        }
        return super.clickMenuButton(player, id);
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.diffuserContainer.stopOpen(player);
    }
}