package com.tiomadre.foragersinsight.common.worldgen.trees;

import com.tiomadre.foragersinsight.common.worldgen.FIConfiguredFeatures;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class FITreeGrowers {
    public static final TreeGrower YOUNG_ACORN_TREE = new TreeGrower( "foragersinsight:young_acorn_tree",
            Optional.of(TreeFeatures.DARK_OAK), Optional.of(FIConfiguredFeatures.YOUNG_ACORN_TREE_KEY), Optional.empty());

    public static final TreeGrower ACORN_TREE = new TreeGrower( "foragersinsight:acorn_tree",
            Optional.of(TreeFeatures.DARK_OAK), Optional.of(FIConfiguredFeatures.ACORN_TREE_KEY), Optional.empty());

    public static final TreeGrower APPLE_TREE = new TreeGrower( "foragersinsight:apple_tree",
            Optional.of(TreeFeatures.OAK), Optional.of(FIConfiguredFeatures.APPLE_TREE_KEY), Optional.empty());

    public static final TreeGrower SSPRUCE_TIP_TREE = new TreeGrower( "foragersinsight:spruce_tip_tree",
            Optional.of(TreeFeatures.SPRUCE), Optional.of(FIConfiguredFeatures.SPRUCE_TIP_TREE_KEY), Optional.empty());

    public static final TreeGrower LILAC_TREE = new TreeGrower( "foragersinsight:lilac_tree",
            Optional.empty(), Optional.of(FIConfiguredFeatures.LILAC_TREE_KEY), Optional.empty());

    public static final TreeGrower SAPPY_BIRCH = new TreeGrower( "foragersinsight:sappy_birch_tree",
            Optional.of(TreeFeatures.BIRCH), Optional.of(FIConfiguredFeatures.SAPPY_BIRCH_TREE_KEY), Optional.empty());

}
