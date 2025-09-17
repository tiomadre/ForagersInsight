package com.doltandtio.foragersinsight.data.server;

import com.doltandtio.foragersinsight.common.block.BountifulLeavesBlock;
import com.doltandtio.foragersinsight.common.block.RoseCropBlock;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIItems;
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
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.doltandtio.foragersinsight.core.registry.FIBlocks.*;
import static com.doltandtio.foragersinsight.core.registry.FIItems.*;

public class FILoot extends LootTableProvider {
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    protected static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    protected static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};


    public FILoot(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), BuiltInLootTables.all(), ImmutableList.of(
                new LootTableProvider.SubProviderEntry(FIBlockLoot::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext context) {
    }

    private static class FIBlockLoot extends BlockLootSubProvider {
        private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());

        protected FIBlockLoot() {
            super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
        }

        private static final LootItemCondition.Builder HAS_KNIFE = MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModTags.KNIVES));

        //CROP LOOT STUFF
        @Override
        protected void generate() {
            //Rose
            this.add(ROSE_CROP.get(), this.applyExplosionDecay(FIItems.ROSE_HIP.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().when(isUpperOrLower(ROSE_CROP))
                                .add(LootItem.lootTableItem(FIItems.ROSE_HIP.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                                .when(stateCond(ROSE_CROP, RoseCropBlock.AGE, RoseCropBlock.MAX_AGE)))
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ROSE_PETALS.get())
                                .when(HAS_KNIFE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
            //Roselle
            this.add(ROSELLE_CROP.get(), this.applyExplosionDecay(ROSELLE_CALYX.get(),
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool().when(isUpperOrLower(ROSELLE_CROP))
                                    .add(LootItem.lootTableItem(ROSELLE_CALYX.get())
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))
                                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714285f, 3)))
                                    .when(stateCond(ROSELLE_CROP, RoseCropBlock.AGE, RoseCropBlock.MAX_AGE)))
                            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ROSELLE_PETALS.get())
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))
                                    .when(HAS_KNIFE)))));
            //Dandelion
            //Dandelion
            this.createFlowerBushDrops(DANDELION_BUSH, DANDELION_ROOT, Items.YELLOW_DYE);
            //Poppy
            this.createFlowerBushDrops(POPPY_BUSH, POPPY_SEEDS, Items.RED_DYE);

            //BLOCK LOOT STUFF
                //Storage
            this.dropSelf(ACORN_CARROT_CAKE.get());
            this.dropSelf(ROSE_HIP_SACK.get());
            this.dropSelf(BLACK_ACORN_SACK.get());
            this.dropSelf(SPRUCE_TIPS_SACK.get());
            this.dropSelf(ROSELLE_CALYX_SACK.get());
            this.dropSelf(DANDELION_ROOT_SACK.get());
            this.dropSelf(POPPY_SEEDS_SACK.get());
                //Wildflower + Plants
            this.add(FIBlocks.ROSELLE_BUSH.get(), block -> LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(LootItem.lootTableItem(block)))
                                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                                    .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))));
            this.add(TALL_BEACH_ROSE_BUSH.get(), block -> LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(block)))
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))));
            this.add(STOUT_BEACH_ROSE_BUSH.get(), block -> LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(block))))
                            .withPool(this.applyExplosionCondition(block,
                                            LootPool.lootPool())));
                //Decorative
            this.dropSelf(SCATTERED_ROSE_PETAL_MAT.get());
            this.dropSelf(SCATTERED_ROSELLE_PETAL_MAT.get());
            this.dropSelf(SCATTERED_SPRUCE_TIP_MAT.get());
            this.dropSelf(SCATTERED_STRAW_MAT.get());
            this.dropSelf(DENSE_STRAW_MAT.get());
            this.dropSelf(DENSE_SPRUCE_TIP_MAT.get());
            this.dropSelf(DENSE_ROSE_PETAL_MAT.get());
            this.dropSelf(DENSE_ROSELLE_PETAL_MAT.get());
                //Saplings and Tree Stuff
            this.dropSelf(BOUNTIFUL_OAK_SAPLING.get());
            this.dropSelf(BOUNTIFUL_DARK_OAK_SAPLING.get());
            this.dropSelf(BOUNTIFUL_SPRUCE_SAPLING.get());
            this.add(BOUNTIFUL_SPRUCE_TIPS.get(), LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK));
            this.add(FIBlocks.SAPPY_BIRCH_LOG.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Blocks.BIRCH_LOG)));
            this.add(BOUNTIFUL_OAK_LEAVES.get(), this.createBountifulLeavesDrops(BOUNTIFUL_OAK_LEAVES, BOUNTIFUL_OAK_SAPLING.get()));
            this.add(BOUNTIFUL_DARK_OAK_LEAVES.get(), this.createBountifulLeavesDrops(BOUNTIFUL_DARK_OAK_LEAVES, BOUNTIFUL_DARK_OAK_SAPLING.get()));
            this.add(BOUNTIFUL_SPRUCE_LEAVES.get(), this.createSpruceLeavesDrops(BOUNTIFUL_SPRUCE_LEAVES.get(), BOUNTIFUL_SPRUCE_SAPLING.get()));
                //Tools + Workstations
            this.add(FIBlocks.TAPPER.get(), block -> createSingleItemTable(FIItems.TAPPER.get()));

        }

        private void createFlowerBushDrops(RegistryObject<? extends Block> registryBlock, RegistryObject<Item> registrySeed, Item originalFlower) {
            Block bush = registryBlock.get();
            Item seed = registrySeed.get();
            this.add(bush, this.applyExplosionDecay(seed, LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(seed)))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(seed))
                            .when(stateCond(registryBlock, CropBlock.AGE, CropBlock.MAX_AGE)))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(originalFlower)
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                            .when(stateCond(registryBlock, CropBlock.AGE, CropBlock.MAX_AGE)).when(HAS_KNIFE))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(seed)
                            .when(stateCond(registryBlock, CropBlock.AGE, CropBlock.MAX_AGE)).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714285f, 3))))));
        }

        private LootTable.Builder createBountifulLeavesDrops(RegistryObject<? extends Block> leafBlock, ItemLike sapling) {
            BountifulLeavesBlock block = (BountifulLeavesBlock) leafBlock.get();
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
                            .add(this.applyExplosionCondition(block, LootItem.lootTableItem(block.getBounty()))));
        }

        private LootTable.Builder createSpruceLeavesDrops(Block leaves, ItemLike sapling) {
            return createSilkTouchOrShearsDispatchTable(leaves,
                    this.applyExplosionCondition(leaves,
                                    LootItem.lootTableItem(sapling))
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, VanillaBlockLoot.NORMAL_LEAVES_SAPLING_CHANCES)))
                    .withPool(LootPool.lootPool()
                            .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                            .add(this.applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))));
        }

        public static LootItemCondition.Builder stateCond(RegistryObject<? extends Block> block, String value) {
            return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RoseCropBlock.HALF, value));
        }

        private static LootItemCondition.Builder isUpper(RegistryObject<? extends Block> block) {
            return stateCond(block, DoubleBlockHalf.UPPER.toString());
        }

        private static LootItemCondition.Builder isLower(RegistryObject<? extends Block> block) {
            return stateCond(block, DoubleBlockHalf.LOWER.toString());
        }

        private static LootItemCondition.Builder isUpperOrLower(RegistryObject<? extends Block> block) {
            return isUpper(block).or(isLower(block));
        }

        private static LootItemCondition.Builder stateCond(RegistryObject<? extends Block> block, Property<Integer> property, int value) {
            return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getNamespace().equals(ForagersInsight.MOD_ID)).collect(Collectors.toSet());
        }
    }
}

