package com.tiomadre.foragersinsight.common.block.entity;

import com.tiomadre.foragersinsight.common.block.DiffuserBlock;
import com.tiomadre.foragersinsight.common.gui.DiffuserMenu;
import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.data.server.recipes.FIDiffusingRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.DiffHelper;

import java.util.List;
import java.util.Optional;

public class DiffuserBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public static final int INPUT_SLOT_COUNT = 3;
    public static final int ENHANCEMENT_SLOT_INDEX = INPUT_SLOT_COUNT;
    public static final int RESULT_SLOT_INDEX = ENHANCEMENT_SLOT_INDEX + 1;
    protected final ContainerData data;
    private int scentDuration= 1200;
    private FIDiffusingRecipes activeScent;
    private Enhancement activeEnhancement = Enhancement.NONE;


    public DiffuserBlockEntity( BlockPos pos, BlockState blockState) {
        super(FIBlockEntityTypes.DIFFUSER.get(), pos, blockState);
        data= new ContainerData() {
            @Override
            public int get(int i) {
                return DiffuserBlockEntity.this.scentDuration;

            }

            @Override
            public void set(int i, int value) {
                DiffuserBlockEntity.this.scentDuration = value;
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.foragersinsight.diffuser");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new DiffuserMenu(i, inventory, this, this.data);
    }


    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("diffuser.scentduration", scentDuration);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        scentDuration = tag.getInt("diffuser.scentduration");
    }


    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
        if(tryStartDiffusion()){
            emitScentEffect();
            decreaseLitTime();
            setChanged();

            if(hasTimeEnded()){
                endScentEffect();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }


    private void decreaseLitTime() {
       scentDuration--;
    }

    private boolean hasTimeEnded() {
        return this.scentDuration <= 0;
    }

    private void resetProgress() {
        scentDuration= 1200;
    }

    public boolean isLit(){
        if (this.level == null) {
            return this.scentDuration > 0;
        }
        return this.scentDuration > 0 && this.getBlockState().getValue(DiffuserBlock.LIT);
    }


    private boolean hasRecipe() {
        boolean slotsFull = false;
        int fullCount = 0;

        for( int i=0; i < INPUT_SLOT_COUNT; i++){
            if(!itemHandler.getStackInSlot(i).isEmpty()){
                fullCount++;
            }
        }

        if(fullCount==3){
            slotsFull= true;
        }
        //code that checks the recipe
        //add something that if true saves the active scent as the recipe
        return false;
    }

    private void getEmitInfo(FIDiffusingRecipes scent){
        this.activeScent = scent;
    }

    private void emitScentEffect() {
        if (this.level == null || this.activeScent == null) {
            return;
        }

        for( int i=0;i<INPUT_SLOT_COUNT;i++){
            itemHandler.extractItem(i,1,false);
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

    public double getEffectiveRadius() {
        if (this.activeScent == null) {
            return 0.0D;
        }
        return this.activeScent.radius() * this.activeEnhancement.radiusMultiplier();
    }

    public boolean isBurning() {
        return this.activeScent != null;
    }


    public void endScentEffect() {
        boolean wasLit = this.isLit();
        boolean hadActiveScent = this.activeScent != null;

        if (this.activeScent != null) {
            clearActiveScent();
        }

        if (this.level != null){
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
    }


    private void clearActiveScent() {
        this.activeScent = null;
        this.activeEnhancement = Enhancement.NONE;
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

    public Optional<FIDiffusingRecipes> getActiveScent() {
        if (this.activeScent != null) {
            return Optional.of(this.activeScent);
        }else{
            return null;
        }
    }

    public boolean tryStartDiffusion(){
        if (this.level == null || this.isLit()) {
            return false;
        }

        if (this.activeScent != null && this.scentDuration > 0){
            BlockState state = this.getBlockState();
            if (!state.getValue(DiffuserBlock.LIT)) {
                this.level.setBlock(this.worldPosition, state.setValue(DiffuserBlock.LIT, true), Block.UPDATE_ALL);
            } else {
                this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
            }
            this.setChanged();
            return true;
        }
        if (!hasRecipe()) {
            return false;
        }


        this.setChanged();
        BlockState state = this.getBlockState();
        if (!state.getValue(DiffuserBlock.LIT)) {
            this.level.setBlock(this.worldPosition, state.setValue(DiffuserBlock.LIT, true), Block.UPDATE_ALL);
        } else {
            this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
        }
        return true;

    }
    
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public Enhancement getActiveEnhancement() {
        return this.activeEnhancement;
    }

    public int getRemainingDuration() {
        return this.scentDuration;
    }

    private Enhancement consumeEnhancement() {
        ItemStack stack = this.itemHandler.getStackInSlot(ENHANCEMENT_SLOT_INDEX);
        if (stack.isEmpty()) {
            return Enhancement.NONE;
        }

        Enhancement enhancement = Enhancement.fromStack(stack);
        if (enhancement == Enhancement.NONE) {
            return Enhancement.NONE;
        }

        stack.shrink(1);
        if (stack.isEmpty()) {
            this.itemHandler.setStackInSlot(ENHANCEMENT_SLOT_INDEX, ItemStack.EMPTY);
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

        public static DiffuserBlockEntity.Enhancement fromStack(ItemStack stack) {
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

        public static DiffuserBlockEntity.Enhancement byName(String name) {
            for (DiffuserBlockEntity.Enhancement enhancement : values()) {
                if (enhancement.serializedName.equals(name)) {
                    return enhancement;
                }
            }
            return NONE;
        }
    }




}
