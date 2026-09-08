package com.tiomadre.foragersinsight.common.block.entity;

import com.tiomadre.foragersinsight.common.block.DiffuserBlock;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.data.server.recipes.FIDiffusingRecipes;
import com.tiomadre.foragersinsight.common.gui.DiffuserMenu;
import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class DiffuserBlockEntity extends BaseContainerBlockEntity {
    private static final String TAG_ACTIVE_SCENT = "ActiveScent";
    private static final String TAG_ACTIVE_SCENT_ID = "ActiveScentId";
    private static final String TAG_ACTIVE_ENHANCEMENT = "ActiveEnhancement";
    private static final String TAG_ACTIVE_INGREDIENTS = "ActiveIngredients";
    private static final String TAG_LIT_TIME = "LitTime";
    private static final String TAG_LIT_DURATION = "LitDuration";
    private static final String TAG_CRAFT_PROGRESS = "CraftProgress";
    private static final String TAG_CRAFT_TIME_TOTAL = "CraftTimeTotal";

    public static final int INPUT_SLOT_COUNT = 3;
    public static final int ENHANCEMENT_SLOT_INDEX = INPUT_SLOT_COUNT;
    public static final int RESULT_SLOT_INDEX = ENHANCEMENT_SLOT_INDEX + 1;
    private static final int SLOT_COUNT = RESULT_SLOT_INDEX + 1;
    private static final int DATA_COUNT = 4;

    private static final int DEFAULT_DIFFUSION_TIME = FIDiffusingRecipes.STANDARD_DURATION;
    private static final int EFFECT_APPLY_INTERVAL = 40;

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private NonNullList<ItemStack> activeIngredients = NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
    private int litTime;
    private int litDuration;
    private int craftProgress;
    private int craftTimeTotal = DEFAULT_DIFFUSION_TIME;
    private FIDiffusingRecipes activeScent;
    private int effectTickCounter;
    private Enhancement activeEnhancement = Enhancement.NONE;
    private int respirationLevel;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> DiffuserBlockEntity.this.litTime;
                case 1 -> DiffuserBlockEntity.this.litDuration;
                case 2 -> DiffuserBlockEntity.this.craftProgress;
                case 3 -> DiffuserBlockEntity.this.craftTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> DiffuserBlockEntity.this.litTime = value;
                case 1 -> DiffuserBlockEntity.this.litDuration = value;
                case 2 -> DiffuserBlockEntity.this.craftProgress = value;
                case 3 -> DiffuserBlockEntity.this.craftTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public DiffuserBlockEntity(BlockPos pos, BlockState state) {
        super(FIBlockEntityTypes.DIFFUSER.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DiffuserBlockEntity blockEntity) {
        boolean wasLit = blockEntity.isLit();
        boolean changed = false;

        if (blockEntity.isLit()) {
            if (blockEntity.litTime > 0) {
                blockEntity.litTime--;
            }
            blockEntity.craftProgress = Mth.clamp(blockEntity.craftProgress + 1, 0, blockEntity.craftTimeTotal);
            if (!level.isClientSide) {
                blockEntity.effectTickCounter++;
                if (blockEntity.effectTickCounter >= EFFECT_APPLY_INTERVAL) {
                    blockEntity.effectTickCounter = 0;
                    blockEntity.applyActiveScentEffects();
                }
            }
        } else if (blockEntity.craftProgress != 0) {
            blockEntity.craftProgress = 0;
            changed = true;
            blockEntity.effectTickCounter = 0;
        }

        if (!level.isClientSide && !blockEntity.isLit() && blockEntity.activeScent != null && blockEntity.litTime <= 0) {
            blockEntity.clearActiveScent();
            changed = true;
        }

        if (wasLit != blockEntity.isLit()) {
            state = state.setValue(DiffuserBlock.LIT, blockEntity.isLit());
            level.setBlock(pos, state, Block.UPDATE_ALL);
            changed = true;
        }

        if (changed) {
            blockEntity.setChanged();
        }
    }

    private void startCycle(FIDiffusingRecipes scent) {
        this.activeScent = scent;
        this.activeEnhancement = consumeEnhancement();
        int enhancedDuration = (int) Math.round(DEFAULT_DIFFUSION_TIME * this.activeEnhancement.durationMultiplier());
        this.litDuration = enhancedDuration;
        this.craftTimeTotal = enhancedDuration;
        this.litTime = this.litDuration;
        this.craftProgress = 0;
        this.effectTickCounter = 0;
    }
    private void storeActiveIngredients() {
        NonNullList<ItemStack> stored = NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            ItemStack stack = this.items.get(slot);
            if (!stack.isEmpty()) {
                stored.set(slot, stack.copyWithCount(1));
            }
        }
        this.activeIngredients = stored;
    }


    private void consumeIngredients(FIDiffusingRecipes scent) {
        for (FIDiffusingRecipes.IngredientCount ingredient : scent.ingredients()) {
            int remaining = ingredient.count();
            for (int slot = 0; slot < INPUT_SLOT_COUNT && remaining > 0; slot++) {
                ItemStack stack = this.items.get(slot);
                if (stack.isEmpty()) {
                    continue;
                }

                if (!ingredient.ingredient().test(stack)) {
                    continue;
                }

                int removed = Math.min(stack.getCount(), remaining);
                stack.shrink(removed);
                remaining -= removed;
                if (stack.isEmpty()) {
                    this.items.set(slot, ItemStack.EMPTY);
                }
            }
        }
    }

    private Optional<FIDiffusingRecipes> findMatchingScent() {
        return FIDiffusingRecipes.findMatch(this.items.subList(0, INPUT_SLOT_COUNT));
    }

    private void clearActiveScent() {
        this.activeScent = null;
        this.effectTickCounter = 0;
        this.activeEnhancement = Enhancement.NONE;
        this.activeIngredients = NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
    }


    public Optional<FIDiffusingRecipes> getActiveScent() {
        if (this.activeScent != null) {
            return Optional.of(this.activeScent);
        }
        return findMatchingScent();
    }

    public boolean tryStartDiffusion() {
        if (this.level == null || this.isLit()) {
            return false;
        }
        if (this.activeScent != null && this.litTime > 0) {
            this.effectTickCounter = 0;
            BlockState state = this.getBlockState();
            if (!state.getValue(DiffuserBlock.LIT)) {
                this.level.setBlock(this.worldPosition, state.setValue(DiffuserBlock.LIT, true), Block.UPDATE_ALL);
            } else {
                this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
            }
            this.setChanged();
            return true;
        }
        Optional<FIDiffusingRecipes> match = findMatchingScent();
        if (match.isEmpty()) {
            return false;
        }

        FIDiffusingRecipes scent = match.get();
        storeActiveIngredients();
        consumeIngredients(scent);
        startCycle(scent);
        this.setChanged();
        BlockState state = this.getBlockState();
        if (!state.getValue(DiffuserBlock.LIT)) {
            this.level.setBlock(this.worldPosition, state.setValue(DiffuserBlock.LIT, true), Block.UPDATE_ALL);
        } else {
            this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
        }
        return true;
    }

    public boolean isLit() {
        if (this.level == null) {
            return this.litTime > 0;
        }
        return this.litTime > 0 && this.getBlockState().getValue(DiffuserBlock.LIT);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.foragersinsight.diffuser");
    }



    @Override
    protected @NotNull AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory) {
        return new DiffuserMenu(id, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            if (!this.items.get(slot).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }



    @Override
    public @NotNull ItemStack getItem(int pSlot) {
        return this.items.get(pSlot);
    }

    @Override
    public @NotNull ItemStack removeItem(int pSlot, int pAmount) {
        if (this.hasActiveScent()) {
            return ItemStack.EMPTY;
        }
        ItemStack result = ContainerHelper.removeItem(this.items, pSlot, pAmount);
        if (!result.isEmpty()) {
            if (this.activeScent != null && !this.isLit()) {
                this.clearActiveScent();
            }
            this.setChanged();
        }
        return result;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int pSlot) {
        if (this.hasActiveScent()) {
            return ItemStack.EMPTY;
        }
        ItemStack result = ContainerHelper.takeItem(this.items, pSlot);
        if (!result.isEmpty()) {
            if (this.activeScent != null && !this.isLit()) {
                this.clearActiveScent();
            }
            this.setChanged();
        }
        return result;
    }

    @Override
    public void setItem(int pSlot, @NotNull ItemStack pStack) {
        if (pSlot < 0 || pSlot >= this.items.size()) {
            return;
        }
        if (pSlot == RESULT_SLOT_INDEX) {
            return;
        }
        if (this.hasActiveScent()) {
            return;
        }

        this.items.set(pSlot, pStack);
        if ((pSlot < INPUT_SLOT_COUNT || pSlot == ENHANCEMENT_SLOT_INDEX) && pStack.getCount() > 1) {
            pStack.setCount(1);
        } else if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
        if (this.activeScent != null && !this.isLit()) {
            this.clearActiveScent();
        }
        this.setChanged();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = items;
    }

    public void extinguish() {
        boolean wasLit = this.isLit();
        boolean hadActiveScent = this.activeScent != null;

        this.litTime = 0;
        this.litDuration = 0;
        this.craftProgress = 0;
        this.effectTickCounter = 0;

        if (this.activeScent != null) {
            clearActiveScent();
        }

        if (this.level != null) {
            if (!this.level.isClientSide && (wasLit || hadActiveScent)) {
                this.level.playSound(null, this.worldPosition, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.3F, .0F);
            }
            BlockState state = this.getBlockState();
            boolean blockLit = state.getValue(DiffuserBlock.LIT);
            if (blockLit != this.isLit()) {
                this.level.setBlock(this.worldPosition, state.setValue(DiffuserBlock.LIT, false), Block.UPDATE_ALL);
            } else if (wasLit || hadActiveScent) {
                this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
            }
        }

        this.setChanged();
    }
    public static int getRemainingDurationFromItem(ItemStack stack) {
        if (stack == null) {
            return 0;
        }
        CompoundTag tag = stack.;
        if (tag == null) {
            return 0;
        }
        return Math.max(tag.getInt(TAG_LIT_TIME), 0);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        if (this.level == null) {
            return false;
        }
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return pPlayer.distanceToSqr(
                this.worldPosition.getX() + 0.5D,
                this.worldPosition.getY() + 0.5D,
                this.worldPosition.getZ() + 0.5D
        ) <= 64.0D;
    }
    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        if (slot < INPUT_SLOT_COUNT) {
            return !this.hasActiveScent();
        }
        if (slot == ENHANCEMENT_SLOT_INDEX) {
            return isEnhancementItem(stack) && !this.hasActiveScent();
        }
        return false;
    }
    @Override
    public void clearContent() {
        this.items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        this.clearActiveScent();
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            ItemStack stack = this.items.get(slot);
            if (!stack.isEmpty() && stack.getCount() > 1) {
                stack.setCount(1);
            }
        }
        ItemStack enhancement = this.items.get(ENHANCEMENT_SLOT_INDEX);
        if (!enhancement.isEmpty() && enhancement.getCount() > 1) {
            enhancement.setCount(1);

        }
        int storedLitTime = tag.getInt(TAG_LIT_TIME);
        int storedLitDuration = tag.getInt(TAG_LIT_DURATION);
        int storedCraftTotal = tag.getInt(TAG_CRAFT_TIME_TOTAL);
        this.craftTimeTotal = storedCraftTotal > 0 ? storedCraftTotal : DEFAULT_DIFFUSION_TIME;
        this.litDuration = storedLitDuration > 0 ? storedLitDuration : this.craftTimeTotal;
        this.craftProgress = tag.getInt(TAG_CRAFT_PROGRESS);
        this.effectTickCounter = 0;
        this.activeScent = null;
        this.activeEnhancement = Enhancement.NONE;
        if (tag.contains(TAG_ACTIVE_SCENT, CompoundTag.TAG_STRING)) {
            FIDiffusingRecipes.byId(ResourceLocation.fromNamespaceAndPath("foragersinsight",tag.getString(TAG_ACTIVE_SCENT)))
                    .ifPresent(scent -> this.activeScent = scent);
        } else if (tag.contains(TAG_ACTIVE_SCENT_ID, CompoundTag.TAG_INT)) {
            FIDiffusingRecipes.byNetworkId(tag.getInt(TAG_ACTIVE_SCENT_ID)).ifPresent(scent -> this.activeScent = scent);
        }
        if (tag.contains(TAG_ACTIVE_ENHANCEMENT, CompoundTag.TAG_STRING)) {
            this.activeEnhancement = Enhancement.byName(tag.getString(TAG_ACTIVE_ENHANCEMENT));
        }
        this.respirationLevel = Mth.clamp(tag.getInt("RespirationLevel"), 0, 3);
        this.activeIngredients = loadActiveIngredients(tag);
        if (this.activeScent != null) {
            this.litTime = Math.min(storedLitTime, this.litDuration);
        } else {
            this.litTime = 0;
            this.craftProgress = 0;
        }
    }


    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putInt(TAG_LIT_TIME, this.litTime);
        tag.putInt(TAG_LIT_DURATION, this.litDuration);
        tag.putInt(TAG_CRAFT_PROGRESS, this.craftProgress);
        tag.putInt(TAG_CRAFT_TIME_TOTAL, this.craftTimeTotal);
        if (this.activeScent != null) {
            tag.putString(TAG_ACTIVE_SCENT, this.activeScent.id().toString());
            tag.putInt(TAG_ACTIVE_SCENT_ID, this.activeScent.networkId());
        }
        tag.putString(TAG_ACTIVE_ENHANCEMENT, this.activeEnhancement.getSerializedName());
        tag.putInt("RespirationLevel", this.respirationLevel);
        if (this.activeScent != null) {
            tag.put(TAG_ACTIVE_INGREDIENTS, saveActiveIngredients(this.activeIngredients));
        }
    }

    public boolean hasActiveScent() {
        return this.activeScent != null;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }

    private void applyActiveScentEffects() {
        if (this.level == null || this.activeScent == null) {
            return;
        }
        AABB area = new AABB(this.worldPosition).inflate(this.getEffectiveRadius());
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, area);

        this.activeScent.createEffectInstance().ifPresent(template -> {
            for (LivingEntity entity : entities) {
                MobEffectInstance instance = new MobEffectInstance(template.getEffect(), template.getDuration(),
                        template.getAmplifier(), template.isAmbient(), template.isVisible(), template.showIcon());
                entity.addEffect(instance);
            }
        });

        if (shouldRestoreBreath()) {
            restoreBreath(entities);
        }
    }

    private void restoreBreath(List<LivingEntity> entities) {
        for (LivingEntity entity : entities) {
            if (needsBreath(entity)) {
                slowlyRestoreBreath(entity);
            }
        }
    }

    private void slowlyRestoreBreath(LivingEntity entity) {
        int maxAir = entity.getMaxAirSupply();
        int currentAir = entity.getAirSupply();
        int restoreAmount = Math.max(1, maxAir / 10);
        entity.setAirSupply(Math.min(currentAir + restoreAmount, maxAir));
    }


    private boolean needsBreath(LivingEntity entity) {
        return entity.getAirSupply() < entity.getMaxAirSupply() && entity.isEyeInFluid(FluidTags.WATER);
    }

    private boolean shouldRestoreBreath() {
        return isSubmergedInWater();
    }

    private boolean isSubmergedInWater() {
        if (this.level == null) {
            return false;
        }
        return this.level.getFluidState(this.worldPosition).is(FluidTags.WATER)
                || this.level.getFluidState(this.worldPosition.above()).is(FluidTags.WATER);
    }
    public Enhancement getActiveEnhancement() {
        return this.activeEnhancement;
    }

    public double getEffectiveRadius() {
        if (this.activeScent == null) {
            return 0.0D;
        }
        return this.activeScent.radius() * this.activeEnhancement.radiusMultiplier();
    }

    public int getRemainingDuration() {
        return this.litTime;
    }
    public NonNullList<ItemStack> getActiveIngredients() {
        return this.activeIngredients;
    }

    public @NotNull ItemStack getDiffuserStack() {
        ItemStack stack = new ItemStack(FIBlocks.DIFFUSER.get());
        if (this.activeScent == null) {
            return stack;
        }
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(TAG_ACTIVE_SCENT, this.activeScent.id().toString());
        tag.putInt(TAG_ACTIVE_SCENT_ID, this.activeScent.networkId());
        tag.putInt(TAG_LIT_TIME, this.litTime);
        tag.putInt(TAG_LIT_DURATION, this.litDuration);
        tag.putInt(TAG_CRAFT_PROGRESS, this.craftProgress);
        tag.putInt(TAG_CRAFT_TIME_TOTAL, this.craftTimeTotal);
        tag.putString(TAG_ACTIVE_ENHANCEMENT, this.activeEnhancement.getSerializedName());
        tag.put(TAG_ACTIVE_INGREDIENTS, saveActiveIngredients(this.activeIngredients));
        return stack;
    }

    public boolean applyItemData(CompoundTag tag) {
        if (tag == null) {
            return false;
        }
        if (tag.contains(TAG_ACTIVE_SCENT, CompoundTag.TAG_STRING)) {
            FIDiffusingRecipes.byId(ResourceLocation.fromNamespaceAndPath("foragersinsight", tag.getString(TAG_ACTIVE_SCENT)))
                    .ifPresent(scent -> this.activeScent = scent);
        } else if (tag.contains(TAG_ACTIVE_SCENT_ID, CompoundTag.TAG_INT)) {
            FIDiffusingRecipes.byNetworkId(tag.getInt(TAG_ACTIVE_SCENT_ID)).ifPresent(scent -> this.activeScent = scent);
        }
        if (this.activeScent == null) {
            return false;
        }
        int storedLitTime = tag.getInt(TAG_LIT_TIME);
        int storedLitDuration = tag.getInt(TAG_LIT_DURATION);
        int storedCraftTotal = tag.getInt(TAG_CRAFT_TIME_TOTAL);
        this.craftTimeTotal = storedCraftTotal > 0 ? storedCraftTotal : DEFAULT_DIFFUSION_TIME;
        this.litDuration = storedLitDuration > 0 ? storedLitDuration : this.craftTimeTotal;
        this.litTime = Math.min(storedLitTime, this.litDuration);
        this.craftProgress = tag.getInt(TAG_CRAFT_PROGRESS);
        if (tag.contains(TAG_ACTIVE_ENHANCEMENT, CompoundTag.TAG_STRING)) {
            this.activeEnhancement = Enhancement.byName(tag.getString(TAG_ACTIVE_ENHANCEMENT));
        }
        this.activeIngredients = loadActiveIngredients(tag);
        this.effectTickCounter = 0;
        this.setChanged();
        return true;
    }

    public static Optional<FIDiffusingRecipes> getScentFromItem(ItemStack stack) {
        if (stack == null) {
            return Optional.empty();
        }
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return Optional.empty();
        }
        if (tag.contains(TAG_ACTIVE_SCENT, CompoundTag.TAG_STRING)) {
            return FIDiffusingRecipes.byId(new ResourceLocation(tag.getString(TAG_ACTIVE_SCENT)));
        }
        if (tag.contains(TAG_ACTIVE_SCENT_ID, CompoundTag.TAG_INT)) {
            return FIDiffusingRecipes.byNetworkId(tag.getInt(TAG_ACTIVE_SCENT_ID));
        }
        return Optional.empty();
    }

    public static NonNullList<ItemStack> getIngredientsFromItem(ItemStack stack) {
        if (stack == null) {
            return NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
        }
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
        }
        return loadActiveIngredients(tag);
    }

    private static ListTag saveActiveIngredients(List<ItemStack> ingredients) {
        ListTag list = new ListTag();
        for (ItemStack stack : ingredients) {
            list.add(stack.save(this.dataAccess.));
        }
        return list;
    }

    private static NonNullList<ItemStack> loadActiveIngredients(CompoundTag tag) {
        if (!tag.contains(TAG_ACTIVE_INGREDIENTS, Tag.TAG_LIST)) {
            return NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
        }
        ListTag list = tag.getList(TAG_ACTIVE_INGREDIENTS, Tag.TAG_COMPOUND);
        NonNullList<ItemStack> ingredients = NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
        int limit = Math.min(list.size(), INPUT_SLOT_COUNT);
        for (int i = 0; i < limit; i++) {
            ingredients.set(i, ItemStack.of(list.getCompound(i)));
        }
        return ingredients;
    }

    private Enhancement consumeEnhancement() {
        ItemStack stack = this.items.get(ENHANCEMENT_SLOT_INDEX);
        if (stack.isEmpty()) {
            return Enhancement.NONE;
        }

        Enhancement enhancement = Enhancement.fromStack(stack);
        if (enhancement == Enhancement.NONE) {
            return Enhancement.NONE;
        }

        stack.shrink(1);
        if (stack.isEmpty()) {
            this.items.set(ENHANCEMENT_SLOT_INDEX, ItemStack.EMPTY);
        }

        if (this.level != null && !this.level.isClientSide) {
            if (enhancement == Enhancement.DURATION) {
                ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
                Containers.dropItemStack(this.level,
                        this.worldPosition.getX() + 0.5D,
                        this.worldPosition.getY() + 1.0D,
                        this.worldPosition.getZ() + 0.5D,
                        emptyBottle);
            } else if (enhancement == Enhancement.DURATION_BUCKET) {
                ItemStack emptyBucket = new ItemStack(Items.BUCKET);
                Containers.dropItemStack(this.level,
                        this.worldPosition.getX() + 0.5D,
                        this.worldPosition.getY() + 1.0D,
                        this.worldPosition.getZ() + 0.5D,
                        emptyBucket);
            }
        }

        return enhancement;
    }

    public static boolean isEnhancementItem(ItemStack stack) {
        return Enhancement.fromStack(stack) != Enhancement.NONE;
    }

    public enum Enhancement {
        NONE(1.0D, 1.0D, "none"),
        RADIUS(1.2D, 1.0D, "honeycomb"),
        RADIUS_BLOCK(2.0D, 1.0D, "honeycomb_block"),
        DURATION(1.0D, 1.2D, "birch_sap_bottle"),
        DURATION_BUCKET(1.0D, 2.D, "birch_sap_bucket");

        private final double radiusMultiplier;
        private final double durationMultiplier;
        private final String serializedName;

        Enhancement(double radiusMultiplier, double durationMultiplier, String serializedName) {
            this.radiusMultiplier = radiusMultiplier;
            this.durationMultiplier = durationMultiplier;
            this.serializedName = serializedName;
        }

        public double radiusMultiplier() {
            return this.radiusMultiplier;
        }

        public double durationMultiplier() {
            return this.durationMultiplier;
        }

        public String getSerializedName() {
            return this.serializedName;
        }

        public static Enhancement fromStack(ItemStack stack) {
            if (stack.is(Items.HONEYCOMB)) {
                return RADIUS;
            }
            if (stack.is(Items.HONEYCOMB_BLOCK)) {
                return RADIUS_BLOCK;
            }
            if (stack.is(FIItems.BIRCH_SAP_BOTTLE.get())) {
                return DURATION;
            }
            if (stack.is(FIItems.BIRCH_SAP_BUCKET.get())) {
                return DURATION_BUCKET;
            }
            return NONE;
        }

        public static Enhancement byName(String name) {
            for (Enhancement enhancement : values()) {
                if (enhancement.serializedName.equals(name)) {
                    return enhancement;
                }
            }
            return NONE;
        }
    }
}
