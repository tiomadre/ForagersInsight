package com.doltandtio.naturesdelight.data.server;

import com.doltandtio.naturesdelight.common.block.BountifulLeavesBlock;
import com.doltandtio.naturesdelight.common.block.DoubleCropBlock;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.ROSE_PETALS;

public class NDLoot extends LootTableProvider {
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    protected static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    protected static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};


    public NDLoot(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), BuiltInLootTables.all(), ImmutableList.of(
                new LootTableProvider.SubProviderEntry(NDBlockLoot::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext context) {
    }

    private static class NDBlockLoot extends BlockLootSubProvider {
        private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());

        protected NDBlockLoot() {
            super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
        }

        private static final LootItemCondition.Builder HAS_KNIFE = MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModTags.KNIVES));


        @Override
        protected void generate() {
            this.add(ROSE_HIP.get(), this.applyExplosionDecay(ROSE_HIP.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().when(stateCond(ROSE_HIP, DoubleCropBlock.HALF, DoubleBlockHalf.LOWER.toString()))
                                .add(LootItem.lootTableItem(ROSE_HIP.get())))
                        .withPool(LootPool.lootPool().when(stateCond(ROSE_HIP, DoubleCropBlock.HALF, DoubleBlockHalf.LOWER.toString()))
                                .add(LootItem.lootTableItem(ROSE_HIP.get()))
                                        .when(stateCond(ROSE_HIP, DoubleCropBlock.HALF, DoubleBlockHalf.LOWER.toString()))
                                        .when(stateCond(ROSE_HIP, DoubleCropBlock.AGE, ROSE_HIP.get().getMaxAge())))
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714285f, 3)))
                        .withPool(LootPool.lootPool()
                                .when(stateCond(ROSE_HIP, DoubleCropBlock.HALF, DoubleBlockHalf.LOWER.toString()))
                                .when(stateCond(ROSE_HIP, DoubleCropBlock.AGE, ROSE_HIP.get().getMaxAge()))
                                .add(LootItem.lootTableItem(Items.ROSE_BUSH)))
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ROSE_PETALS.get()).when(HAS_KNIFE))));

            this.dropSelf(ROSE_HIP_CRATE.get());
            this.dropSelf(ROSE_PETALS_SACK.get());

            this.add(BOUNTIFUL_OAK_LEAVES.get(), this.createBountifulLeavesDrops(BOUNTIFUL_OAK_LEAVES, Items.APPLE, Items.OAK_SAPLING));
        }

        private LootTable.Builder createBountifulLeavesDrops(RegistryObject<? extends BountifulLeavesBlock> leafBlock, Item bounty, Item sapling) {
            BountifulLeavesBlock block = leafBlock.get();
            return createSilkTouchOrShearsDispatchTable(block,
                    this.applyExplosionCondition(block,
                            LootItem.lootTableItem(sapling))
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, VanillaBlockLoot.NORMAL_LEAVES_SAPLING_CHANCES)))
                    .withPool(LootPool.lootPool()
                            .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                            .add(this.applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))))
                    .withPool(LootPool.lootPool()
                            .when(HAS_NO_SHEARS_OR_SILK_TOUCH).when(stateCond(leafBlock, BountifulLeavesBlock.AGE, BountifulLeavesBlock.MAX_AGE))
                            .add(this.applyExplosionCondition(block, LootItem.lootTableItem(bounty))));
        }

        private <T extends Comparable<T>> LootItemCondition.Builder stateCond(RegistryObject<? extends Block> block, Property<T> property, String value) {
            return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
        }


        private <T extends Comparable<T>> LootItemCondition.Builder stateCond(RegistryObject<? extends Block> block, Property<Integer> property, int value) {
            return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
        }

        @Override
        public Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(NaturesDelight.MOD_ID)).collect(Collectors.toSet());
        }
    }
}

