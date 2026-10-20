package com.tiomadre.foragersinsight.common.worldgen;

import com.tiomadre.foragersinsight.common.block.BountifulLeavesBlock;
import com.tiomadre.foragersinsight.common.block.SuspiciousLitterBlock;
import com.tiomadre.foragersinsight.common.worldgen.trees.decorator.BountifulOakLeafDecorator;
import com.tiomadre.foragersinsight.common.worldgen.trees.decorator.BountifulSpruceTipDecorator;
import com.tiomadre.foragersinsight.common.worldgen.trees.decorator.WallMushroomTreeDecorator;
import com.tiomadre.foragersinsight.common.worldgen.trees.foliage.LilacTreeFoliagePlacer;
import com.tiomadre.foragersinsight.common.worldgen.trees.foliage.WoodlandsOakShrubFoliagePlacer;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.common.worldgen.trees.decorator.SappyBirchLogDecorator;
import com.tiomadre.foragersinsight.core.registry.FIBiomeFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import net.minecraft.world.level.block.PinkPetalsBlock;

import java.util.OptionalInt;
import java.util.function.Supplier;

public class FIConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE_KEY = registerKey("apple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ACORN_TREE_KEY = registerKey("acorn_dark_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YOUNG_ACORN_TREE_KEY = registerKey("young_acorn_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YOUNG_DARK_OAK_TREE_KEY = registerKey("young_dark_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LILAC_TREE_KEY = registerKey("lilac_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_TIP_TREE_KEY = registerKey("spruce_tip_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAPPY_BIRCH_TREE_KEY = registerKey("sappy_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MUSHROOM_BIRCH_TREE_KEY = registerKey("mushroom_birch_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_WOODLANDS_TREES_KEY = registerKey("dark_woodlands_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_OAK_BUSH_KEY = registerKey("dark_oak_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOODLANDS_OAK_SHRUB_KEY = registerKey("woodlands_oak_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_WOODLANDS_FALLEN_TREES_KEY = registerKey("dark_woodlands_fallen_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOODLANDS_PATCH_KEY = registerKey("woodlands_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOODLAND_FERN_PATCH_KEY = registerKey("patch_woodland_fern");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ROSELLE_BUSH_PATCH_KEY = registerKey("patch_roselle_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BEACH_ROSE_PATCH_KEY = registerKey("patch_beach_rose");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PHLOX_PATCH_KEY = registerKey("patch_phlox");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_SUSPICIOUS_LEAF_LITTER_PATCH_KEY = registerKey("oak_suspicious_leaf_litter_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_SUSPICIOUS_LEAF_LITTER_PATCH_KEY = registerKey("birch_suspicious_leaf_litter_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_SUSPICIOUS_LEAF_LITTER_PATCH_KEY = registerKey("spruce_suspicious_leaf_litter_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_OAK_SUSPICIOUS_LEAF_LITTER_PATCH_KEY = registerKey("dark_oak_suspicious_leaf_litter_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_SUSPICIOUS_LEAF_LITTER_PATCH_KEY = registerKey("flower_suspicious_leaf_litter_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PUDDLE_KEY = registerKey("puddle");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ForagersInsight.rl(name));
    }

    public static void bootstap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        //Tree Stuff
        register(context, APPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(Blocks.OAK_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).decorators(java.util.List.of(new BountifulOakLeafDecorator(5)))
                .ignoreVines().build());
        register(context, YOUNG_ACORN_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                new StraightTrunkPlacer(4, 2, 1),
                bountifulLeafStateProvider(Blocks.DARK_OAK_LEAVES, FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build());

        register(context, YOUNG_DARK_OAK_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                new StraightTrunkPlacer(4, 2, 1),
                BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build());

        register(context, ACORN_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                new DarkOakTrunkPlacer(6, 2, 1),
                bountifulLeafStateProvider(Blocks.DARK_OAK_LEAVES, FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 1, 0, 1,2, OptionalInt.empty())
        ).ignoreVines().build());

        register(context, LILAC_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FIBlocks.LILAC_LOG.get()),
                new StraightTrunkPlacer(3, 1, 1),
                BlockStateProvider.simple(FIBlocks.LILAC_LEAVES.get()),
                new LilacTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build());

        register(context, SPRUCE_TIP_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(5, 2, 1),
                bountifulLeafStateProvider(Blocks.SPRUCE_LEAVES, FIBlocks.BOUNTIFUL_SPRUCE_LEAVES),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)),
                new TwoLayersFeatureSize(2, 0, 2)
        ).decorators(java.util.List.of(new BountifulSpruceTipDecorator(6))).ignoreVines().build());

        register(context, SAPPY_BIRCH_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.BIRCH_LOG),
                new StraightTrunkPlacer(5, 2, 0),
                BlockStateProvider.simple(Blocks.BIRCH_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).decorators(java.util.List.of(new SappyBirchLogDecorator(1.0F))).ignoreVines().build());
    //Biome Specific
        //Dark Woodlands
        register(context, WOODLANDS_OAK_SHRUB_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(2, 1, 0),
                BlockStateProvider.simple(Blocks.OAK_LEAVES),
                new WoodlandsOakShrubFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build());
        register(context, DARK_OAK_BUSH_KEY, FIBiomeFeatures.DARK_OAK_BUSH.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, DARK_WOODLANDS_TREES_KEY, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(java.util.List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(YOUNG_DARK_OAK_TREE_KEY)), 0.55F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(YOUNG_ACORN_TREE_KEY)), 0.18F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.DARK_OAK)), 0.10F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WOODLANDS_OAK_SHRUB_KEY)), 0.17F)),
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(YOUNG_DARK_OAK_TREE_KEY))));
        //Birch Forest
        register(context, MUSHROOM_BIRCH_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.BIRCH_LOG),
                new StraightTrunkPlacer(5, 2, 0),
                BlockStateProvider.simple(Blocks.BIRCH_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).decorators(java.util.List.of(new WallMushroomTreeDecorator())).ignoreVines().build());
        //Other Patches
        Holder<PlacedFeature> oakFernPatch = PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(FIBlocks.WOODLAND_FERN.get().defaultBlockState())),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.WATER)),
                        BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.DIRT))));
        register(context, WOODLAND_FERN_PATCH_KEY, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 7, 3, oakFernPatch));

        //Dark Woodlands Features
        register(context, DARK_WOODLANDS_FALLEN_TREES_KEY, FIBiomeFeatures.FALLEN_TREES.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, WOODLANDS_PATCH_KEY, Feature.RANDOM_PATCH, woodlandsPatchConfiguration());

        //Wild Flowers
        Holder<PlacedFeature> roselleBush = PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 6)
                        .add(FIBlocks.ROSELLE_BUSH.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 2)
                        .build())),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.replaceable(BlockPos.ZERO.above()),
                        BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.WATER)),
                        BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.DIRT))));
        register(context, ROSELLE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 7, 3, roselleBush));

        Holder<PlacedFeature> beachRosePatch = PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(ModBlocks.SANDY_SHRUB.get().defaultBlockState(), 6)
                        .add(FIBlocks.STOUT_BEACH_ROSE_BUSH.get().defaultBlockState(), 3)
                        .add(FIBlocks.TALL_BEACH_ROSE_BUSH.get().defaultBlockState()
                                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)
                        .build())),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.replaceable(BlockPos.ZERO.above()),
                        BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.WATER)),
                        BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.SAND))));
        register(context, BEACH_ROSE_PATCH_KEY, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 5, 2, beachRosePatch));

        Holder<PlacedFeature> phloxPatch = PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(FIBlocks.PHLOX.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, 1), 2)
                        .add(FIBlocks.PHLOX.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, 2), 1)
                        .add(FIBlocks.PHLOX.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, 3), 1)
                        .add(FIBlocks.PHLOX.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, 4), 1)
                        .add(FIBlocks.WOODLAND_FERN.get().defaultBlockState(), 1)
                        .build())),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.WATER)),
                        BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.DIRT))));
        register(context, PHLOX_PATCH_KEY, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 7, 3, phloxPatch));

        //Suspicious Litter + Other
        register(context, OAK_SUSPICIOUS_LEAF_LITTER_PATCH_KEY, Feature.RANDOM_PATCH,
                suspiciousLitterPatchConfiguration(SuspiciousLitterBlock.FoliageType.OAK));
        register(context, BIRCH_SUSPICIOUS_LEAF_LITTER_PATCH_KEY, Feature.RANDOM_PATCH,
                suspiciousLitterPatchConfiguration(SuspiciousLitterBlock.FoliageType.BIRCH));
        register(context, SPRUCE_SUSPICIOUS_LEAF_LITTER_PATCH_KEY, Feature.RANDOM_PATCH,
                suspiciousLitterPatchConfiguration(SuspiciousLitterBlock.FoliageType.SPRUCE));
        register(context, DARK_OAK_SUSPICIOUS_LEAF_LITTER_PATCH_KEY, Feature.RANDOM_PATCH,
                suspiciousLitterPatchConfiguration(SuspiciousLitterBlock.FoliageType.DARK_OAK));
        register(context, FLOWER_SUSPICIOUS_LEAF_LITTER_PATCH_KEY, Feature.RANDOM_PATCH,
                suspiciousLitterPatchConfiguration(SuspiciousLitterBlock.FoliageType.FLOWER));
        register(context, PUDDLE_KEY, FIBiomeFeatures.PUDDLE.get(), NoneFeatureConfiguration.INSTANCE);


    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key,
                                                                                          F feature,
                                                                                          FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    //Patch defs + weighted grass generation for Patches
    private static RandomPatchConfiguration suspiciousLitterPatchConfiguration(SuspiciousLitterBlock.FoliageType foliageType) {
        BlockState state = FIBlocks.SUSPICIOUS_LEAF_LITTER.get().defaultBlockState().setValue(SuspiciousLitterBlock.FOLIAGE, foliageType);
        Holder<PlacedFeature> placedFeature = PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 6)
                        .add(state, 2)
                        .build())),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.WATER)),
                        BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.DIRT))));
        return new RandomPatchConfiguration(35, 5, 2, placedFeature);

    }
    //unique to Dark Woodlands
    private static RandomPatchConfiguration woodlandsPatchConfiguration() {
        Holder<PlacedFeature> placedFeature = PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 5)
                        .add(Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 3)
                        .add(FIBlocks.WOODLAND_FERN.get().defaultBlockState(), 3)
                        .add(Blocks.LILAC.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)
                        .add(Blocks.PEONY.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)
                        .add(Blocks.ROSE_BUSH.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)
                        .build())),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.replaceable(BlockPos.ZERO.above()),
                        BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.WATER)),
                        BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.DIRT))));
        return new RandomPatchConfiguration(96, 7, 4, placedFeature);
    }
    private static RandomPatchConfiguration fallenTreePatchConfiguration() {
        Holder<PlacedFeature> placedFeature = PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(Blocks.DARK_OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 4)
                        .add(Blocks.DARK_OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 4)
                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 2)
                        .build())),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.WATER)),
                        BlockPredicate.matchesTag(BlockPos.ZERO.below(), BlockTags.DIRT))));
        return new RandomPatchConfiguration(3, 2, 1, placedFeature);
    }
    //Tree Foliage shizz

    private static WeightedStateProvider bountifulLeafStateProvider(Block leaf, Supplier<Block> bountifulLeaf) {
        BlockState bountifulState = bountifulLeaf.get().defaultBlockState();
        if (bountifulState.hasProperty(BountifulLeavesBlock.AGE)) {
            bountifulState = bountifulState.setValue(BountifulLeavesBlock.AGE, BountifulLeavesBlock.MAX_AGE);
        }

        return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(leaf.defaultBlockState(), 3)
                .add(bountifulState, 1));
    }

}