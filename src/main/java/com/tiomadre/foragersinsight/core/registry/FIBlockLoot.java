package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.block.*;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.loot.CanItemPerformAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.CommonTags;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tiomadre.foragersinsight.core.registry.FIBlocks.*;
import static com.tiomadre.foragersinsight.core.registry.FIBlocks.LILAC_HANGING_SIGN;
import static com.tiomadre.foragersinsight.core.registry.FIBlocks.LILAC_SIGN;
import static com.tiomadre.foragersinsight.core.registry.FIItems.*;
import static com.tiomadre.foragersinsight.core.registry.FIItems.BLEWIT_MUSHROOM;

public class FIBlockLoot extends BlockLootSubProvider
{

    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
    public FIBlockLoot(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());

    @Override
    protected Iterable<Block> getKnownBlocks() {
        // The contents of our DeferredRegister.
        return FIBlocks.HELPER.getDeferredRegister().getEntries()
                .stream()
                // Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
                .map(e -> (Block) e.value())
                .toList();
    }

    private static final LootItemCondition.Builder HAS_KNIFE = MatchTool.toolMatches(ItemPredicate.Builder.item().of(CommonTags.Items.TOOLS_KNIFE));



    //CROP LOOT STUFF
    @Override
    protected void generate() {
        //Rose
        this.add(ROSE_CROP.get(), this.applyExplosionDecay(FIItems.ROSE_HIP.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().when(isUpperOrLower(ROSE_CROP))
                                .add(LootItem.lootTableItem(FIItems.ROSE_HIP.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                                .when(stateCond(ROSE_CROP, RoseCropBlock.AGE, RoseCropBlock.MAX_AGE)))
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ROSE_PETALS.get())
                                .when(HAS_KNIFE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        //Roselle
        this.add(ROSELLE_CROP.get(), this.applyExplosionDecay(ROSELLE_CALYX.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().when(isUpperOrLower(ROSELLE_CROP))
                                .add(LootItem.lootTableItem(ROSELLE_CALYX.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(registries.holderOrThrow(Enchantments.FORTUNE), 0.5714285f, 3)))
                                .when(stateCond(ROSELLE_CROP, RoseCropBlock.AGE, RoseCropBlock.MAX_AGE)))
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ROSELLE_PETALS.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))
                                .when(HAS_KNIFE)))));
        //Dandelion
        this.createFlowerBushDrops(DANDELION_BUSH, DANDELION_ROOT, Items.YELLOW_DYE, DandelionBushBlock.AGE, DandelionBushBlock.MAX_AGE);
        //Poppy
        this.createFlowerBushDrops(POPPY_BUSH, POPPY_SEEDS, Items.RED_DYE, PoppyBushBlock.AGE, PoppyBushBlock.MAX_AGE);

        //BLOCK LOOT STUFF
        //Feasts
        this.dropSelf(RAINBOW_SANDWICH.get());
        this.dropSelf(ACORN_CARROT_CAKE.get());
        //Storage
        this.dropSelf(ROSE_HIP_SACK.get());
        this.dropSelf(APPLE_CRATE.get());
        this.dropSelf(BLACK_ACORN_SACK.get());
        this.dropSelf(SPRUCE_TIPS_SACK.get());
        this.dropSelf(ROSELLE_CALYX_SACK.get());
        this.dropSelf(DANDELION_ROOT_SACK.get());
        this.dropSelf(POPPY_SEEDS_SACK.get());
        this.add(FIBlocks.BLEWIT_CRATE.get(), this::createSlabItemTable);
        this.add(LILAC_BLOOM_CRATE.get(), this::createSlabItemTable);
        this.dropSelf(TINDER_CONK_CRATE.get());
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
        this.dropSelf(WOODLAND_FERN.get());
        this.dropSelf(GHOST_PIPE.get());
        this.dropSelf(PHLOX.get());
        this.dropSelf(SKUNK_CABBAGE.get());

        //Other Natural Blocks
        this.add(HOLLOW_LOG.get(), block -> this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(vectorwing.farmersdelight.common.registry.ModItems.TREE_BARK.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))))));
        this.add(SAP_SPLOTCH.get(), LootTable.lootTable());
        this.add(SUSPICIOUS_LEAF_LITTER.get(), LootTable.lootTable());
        this.add(FIBlocks.BLEWIT_MUSHROOM.get(), block -> LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                        .add(LootItem.lootTableItem(FIItems.BLEWIT_MUSHROOM.get())))));

        // Mushroom Colonies
        this.add(BLEWIT_MUSHROOM_COLONY.get(), block -> createMushroomColonyDrops(BLEWIT_MUSHROOM_COLONY, BLEWIT_MUSHROOM_COLONY.get(), BLEWIT_MUSHROOM.get()));
        this.add(WALL_RED_MUSHROOM.get(), block -> createSingleItemTable(Items.RED_MUSHROOM));
        this.add(WALL_BROWN_MUSHROOM.get(), block -> createSingleItemTable(Items.BROWN_MUSHROOM));
        this.add(WALL_BLEWIT_MUSHROOM.get(), block -> createSingleItemTable(FIItems.BLEWIT_MUSHROOM.get()));
        this.add(WALL_RED_MUSHROOM_COLONY.get(), block -> createMushroomColonyDrops(WALL_RED_MUSHROOM_COLONY, ModBlocks.RED_MUSHROOM_COLONY.get(), Items.RED_MUSHROOM));
        this.add(WALL_BROWN_MUSHROOM_COLONY.get(), block -> createMushroomColonyDrops(WALL_BROWN_MUSHROOM_COLONY, ModBlocks.BROWN_MUSHROOM_COLONY.get(), Items.BROWN_MUSHROOM));
        this.add(WALL_BLEWIT_MUSHROOM_COLONY.get(), block -> createMushroomColonyDrops(WALL_BLEWIT_MUSHROOM_COLONY, BLEWIT_MUSHROOM_COLONY.get(), BLEWIT_MUSHROOM.get()));
        //Unique Mushrooms
        this.add(FIBlocks.TINDER_CONK.get(), block -> createTinderConkDrops());

        //Condensed
        this.add(CONDENSED_DIRT.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Blocks.DIRT)));
        this.add(CONDENSED_SAND.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Blocks.SAND)));

        //Decorative
        this.dropSelf(SCATTERED_LILAC_BLOOM_MAT.get());
        this.dropSelf(SCATTERED_ROSE_PETAL_MAT.get());
        this.dropSelf(SCATTERED_ROSELLE_PETAL_MAT.get());
        this.dropSelf(SCATTERED_SPRUCE_TIP_MAT.get());
        this.dropSelf(SCATTERED_STRAW_MAT.get());
        this.dropSelf(DENSE_LILAC_BLOOM_MAT.get());
        this.dropSelf(DENSE_STRAW_MAT.get());
        this.dropSelf(DENSE_SPRUCE_TIP_MAT.get());
        this.dropSelf(DENSE_ROSE_PETAL_MAT.get());
        this.dropSelf(DENSE_ROSELLE_PETAL_MAT.get());
        this.dropSelf(FIBlocks.GHOST_PIPE_TORCH.get());
        this.dropOther(FIBlocks.WALL_GHOST_PIPE_TORCH.get(), FIItems.GHOST_PIPE_TORCH.get());

        //Leaves and Tree Stuff
        this.add(BOUNTIFUL_SPRUCE_TIPS.get(), LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK));
        this.add(FIBlocks.SAPPY_BIRCH_LOG.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Blocks.BIRCH_LOG)));
        this.add(STRIPPED_SAPPY_BIRCH_LOG.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Blocks.STRIPPED_BIRCH_LOG)));
        this.dropSelf(LILAC_LOG.get());
        this.dropSelf(STRIPPED_LILAC_LOG.get());
        this.add(BOUNTIFUL_OAK_LEAVES.get(), this.createBountifulLeavesDrops(BOUNTIFUL_OAK_LEAVES, Blocks.OAK_SAPLING));
        this.add(BOUNTIFUL_DARK_OAK_LEAVES.get(), this.createBountifulLeavesDrops(BOUNTIFUL_DARK_OAK_LEAVES, Blocks.DARK_OAK_SAPLING));
        this.add(BOUNTIFUL_SPRUCE_LEAVES.get(), this.createSpruceLeavesDrops(BOUNTIFUL_SPRUCE_LEAVES.get(), Blocks.SPRUCE_SAPLING));
        this.add(LILAC_LEAVES.get(), this.createLeafDrops(LILAC_LEAVES.get()));
        this.add(BLOSSOMING_LILAC_LEAVES.get(), this.createBlossomingLilacLeavesDrops(BLOSSOMING_LILAC_LEAVES.get()));
        this.add(HANGING_LILAC_LEAVES.get(), LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK));
        //Wood Stuff
        this.dropSelf(LILAC_PLANKS.get());
        this.dropSelf(LILAC_STAIRS.get());
        this.add(LILAC_SLAB.get(), createSlabItemTable(LILAC_SLAB.get()));
        this.dropSelf(LILAC_FENCE.get());
        this.dropSelf(LILAC_FENCE_GATE.get());
        this.add(LILAC_DOOR.get(), createDoorTable(LILAC_DOOR.get()));
        this.dropSelf(LILAC_TRAPDOOR.get());
        this.dropSelf(LILAC_PRESSURE_PLATE.get());
        this.dropSelf(LILAC_BUTTON.get());
        this.add(LILAC_SIGN.get(), block -> createSingleItemTable(FIItems.LILAC_SIGN.get()));
        this.add(LILAC_WALL_SIGN.get(), block -> createSingleItemTable(FIItems.LILAC_SIGN.get()));
        this.add(LILAC_HANGING_SIGN.get(), block -> createSingleItemTable(FIItems.LILAC_HANGING_SIGN.get()));
        this.add(LILAC_WALL_HANGING_SIGN.get(), block -> createSingleItemTable(FIItems.LILAC_HANGING_SIGN.get()));
        this.dropSelf(LILAC_CABINET.get());

        //Tools + Workstations
        this.add(FIBlocks.DIFFUSER.get(), block -> createSingleItemTable(FIItems.DIFFUSER.get()));
        this.add(FIBlocks.TAPPER.get(), block -> createSingleItemTable(FIItems.TAPPER.get()));
        this.add(SAP_TRAP.get(), block -> LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.5F))
                        .add(LootItem.lootTableItem(vectorwing.farmersdelight.common.registry.ModItems.TREE_BARK.get())))));
    }
    private LootTable.Builder createTinderConkDrops() {
        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(FIItems.TINDER_CONK.get(),
                        LootPool.lootPool()
                                .add(LootItem.lootTableItem(FIBlocks.TINDER_CONK.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                        .when(stateCond(
                                                FIBlocks.TINDER_CONK,
                                                TinderConkBlock.AGE,
                                                4
                                        )))))
                .withPool(this.applyExplosionCondition(FIItems.TINDER_CONK.get(),
                        LootPool.lootPool()
                                .add(LootItem.lootTableItem(FIItems.TINDER_CONK_SPORES.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                        .when(stateCond(
                                                FIBlocks.TINDER_CONK,
                                                TinderConkBlock.AGE,
                                                4
                                        )))));
    }
    private LootTable.Builder createMushroomColonyDrops(DeferredBlock<Block> colonyBlock, ItemLike colonyDrop, ItemLike mushroomDrop) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(colonyBlock.get(), LootPool.lootPool()
                        .add(LootItem.lootTableItem(colonyDrop))
                        .when(stateCond(colonyBlock, MushroomColonyBlock.COLONY_AGE, 3))
                        .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST))))
                .withPool(this.applyExplosionCondition(colonyBlock.get(), LootPool.lootPool().add(AlternativesEntry.alternatives(
                        LootItem.lootTableItem(mushroomDrop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))).when(stateCond(colonyBlock, MushroomColonyBlock.COLONY_AGE, 0)),
                        LootItem.lootTableItem(mushroomDrop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))).when(stateCond(colonyBlock, MushroomColonyBlock.COLONY_AGE, 1)),
                        LootItem.lootTableItem(mushroomDrop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))).when(stateCond(colonyBlock, MushroomColonyBlock.COLONY_AGE, 2)),
                        LootItem.lootTableItem(mushroomDrop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(5))).when(stateCond(colonyBlock, MushroomColonyBlock.COLONY_AGE, 3))
                                .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST).invert())))));

    }
    private LootTable.Builder createBlossomingLilacLeavesDrops(Block leaves) {
        return this.createLeafDrops(leaves)
                .withPool(LootPool.lootPool()
                        .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST).invert())
                        .add(this.applyExplosionCondition(leaves, LootItem.lootTableItem(Blocks.LILAC)))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), VanillaBlockLoot.NORMAL_LEAVES_SAPLING_CHANCES)));
    }
    private LootTable.Builder createLeafDrops(Block leaves) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(leaves)
                                        .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST)))))
                .withPool(LootPool.lootPool()
                        .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST).invert())
                        .add(this.applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))));
    }

    private void createFlowerBushDrops(DeferredBlock<Block> registryBlock, DeferredItem<Item> registrySeed, Item originalFlower, IntegerProperty ageProperty, int maxAge) {
        Block bush = registryBlock.get();
        Item seed = registrySeed.get();
        this.add(bush, this.applyExplosionDecay(seed, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(seed)))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(seed))
                        .when(stateCond(registryBlock, ageProperty, maxAge)))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(originalFlower)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(stateCond(registryBlock, ageProperty, maxAge)).when(HAS_KNIFE))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(seed)
                        .when(stateCond(registryBlock, ageProperty, maxAge)).apply(ApplyBonusCount.addBonusBinomialDistributionCount(registries.holderOrThrow(Enchantments.FORTUNE), 0.5714285f, 3))))));
    }

    private LootTable.Builder createBountifulLeavesDrops(DeferredBlock<Block> leafBlock, ItemLike sapling) {
        BountifulLeavesBlock block = (BountifulLeavesBlock) leafBlock.get();
        return createSilkTouchOrShearsDispatchTable(block,
                this.applyExplosionCondition(block,
                                LootItem.lootTableItem(sapling))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), VanillaBlockLoot.NORMAL_LEAVES_SAPLING_CHANCES)))
                .withPool(LootPool.lootPool()
                        .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST).invert())
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))))
                .withPool(LootPool.lootPool()
                        .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST).invert()).when(stateCond(leafBlock, BountifulLeavesBlock.AGE, BountifulLeavesBlock.MAX_AGE))
                        .add(this.applyExplosionCondition(block, LootItem.lootTableItem(block.getBounty()))));
    }

    private LootTable.Builder createSpruceLeavesDrops(Block leaves, ItemLike sapling) {
        return createSilkTouchOrShearsDispatchTable(leaves,
                this.applyExplosionCondition(leaves,
                                LootItem.lootTableItem(sapling))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), VanillaBlockLoot.NORMAL_LEAVES_SAPLING_CHANCES)))
                .withPool(LootPool.lootPool()
                        .when(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST).invert())
                        .add(this.applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))));
    }

    public static LootItemCondition.Builder stateCond(DeferredBlock<Block> block, String value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RoseCropBlock.HALF, value));
    }

    private static LootItemCondition.Builder isUpper(DeferredBlock<Block> block) {
        return stateCond(block, DoubleBlockHalf.UPPER.toString());
    }

    private static LootItemCondition.Builder isLower(DeferredBlock<Block> block) {
        return stateCond(block, DoubleBlockHalf.LOWER.toString());
    }

    private static LootItemCondition.Builder isUpperOrLower(DeferredBlock<Block> block) {
        return isUpper(block).or(isLower(block));
    }

    private static LootItemCondition.Builder stateCond(DeferredBlock<Block> block, Property<Integer> property, int value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }


}

