package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.common.block.*;
import com.doltandtio.foragersinsight.common.worldgen.trees.grower.BountifulDarkOakTreeGrower;
import com.doltandtio.foragersinsight.common.worldgen.trees.grower.BountifulOakTreeGrower;
import com.doltandtio.foragersinsight.common.worldgen.trees.grower.BountifulSpruceTreeGrower;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.teamabnormals.blueprint.common.block.LogBlock;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import static com.doltandtio.foragersinsight.core.registry.FIItems.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.copy;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FIBlocks {
    public static final BlockSubRegistryHelper HELPER = ForagersInsight.REGISTRY_HELPER.getBlockSubHelper();

    //Cakes and Feasts
    public static final RegistryObject<Block> ACORN_CARROT_CAKE = HELPER.createBlockNoItem("acorn_carrot_cake",
            () -> new SliceableCakeBlock(copy(Blocks.CAKE), FIItems.SLICE_OF_ACORN_CARROT_CAKE));
    //Crops
        //Flower
    public static final RegistryObject<Block> POPPY_BUSH = HELPER.createBlockNoItem("poppy_bush", () ->
            new PoppyBushBlock(copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> DANDELION_BUSH = HELPER.createBlockNoItem("dandelion_bush", () ->
            new DandelionBushBlock(copy(Blocks.POTATOES)));
    public static final RegistryObject<Block> ROSE_CROP = HELPER.createBlockNoItem("rose_crop", () -> new RoseCropBlock(
            copy(Blocks.WHEAT), 3));
        //Trees
    public static final RegistryObject<Block> BOUNTIFUL_OAK_SAPLING = HELPER.createFuelBlock("bountiful_oak_sapling", () ->
            new SaplingBlock(new BountifulOakTreeGrower(), copy(Blocks.OAK_SAPLING)), 100);
    public static final RegistryObject<Block> BOUNTIFUL_OAK_LEAVES = HELPER.createBlock("bountiful_oak_leaves", () ->
            new BountifulLeavesBlock(copy(Blocks.OAK_LEAVES), () -> Items.APPLE));
    public static final RegistryObject<Block> BOUNTIFUL_DARK_OAK_SAPLING = HELPER.createFuelBlock("bountiful_dark_oak_sapling", () ->
            new SaplingBlock(new BountifulDarkOakTreeGrower(), copy(Blocks.DARK_OAK_SAPLING)), 100);
    public static final RegistryObject<Block> BOUNTIFUL_DARK_OAK_LEAVES = HELPER.createBlock("bountiful_dark_oak_leaves", () ->
            new BountifulLeavesBlock(copy(Blocks.DARK_OAK_LEAVES), BLACK_ACORN));
    public static final RegistryObject<Block> BOUNTIFUL_SPRUCE_SAPLING = HELPER.createFuelBlock("bountiful_spruce_sapling", () ->
            new SaplingBlock(new BountifulSpruceTreeGrower(), copy(Blocks.SPRUCE_SAPLING)), 100);
    public static final RegistryObject<Block> BOUNTIFUL_SPRUCE_LEAVES = HELPER.createBlock("bountiful_spruce_leaves", () ->
            new BountifulSpruceLeavesBlock(copy(Blocks.SPRUCE_LEAVES)));
    public static final RegistryObject<Block> BOUNTIFUL_SPRUCE_TIPS = HELPER.createBlockNoItem("bountiful_spruce_tips", () ->
            new SpruceTipBlock(copy(Blocks.SWEET_BERRY_BUSH).noCollission()));
    public static final RegistryObject<Block> SAPPY_BIRCH_LOG = HELPER.createFuelBlock("sappy_birch_log", () ->
            new LogBlock(() -> Blocks.STRIPPED_BIRCH_LOG, copy(Blocks.BIRCH_LOG)), 300);
        //Syrup Tap
    public static final RegistryObject<Block> TAPPER = HELPER.createBlockNoItem("tapper", () ->
            new TapperBlock(copy(Blocks.IRON_BLOCK).noOcclusion()));
    //DECORATIVE
        //Foliage Mats
    public static final RegistryObject<Block> SCATTERED_ROSE_PETAL_MAT = HELPER.createBlock("scattered_rose_petals", FoliageMatBlock::new);
    public static final RegistryObject<Block> SCATTERED_ROSELLE_PETAL_MAT = HELPER.createBlock("scattered_roselle_petals", FoliageMatBlock::new);
    public static final RegistryObject<Block> SCATTERED_SPRUCE_TIP_MAT = HELPER.createBlock("scattered_spruce_tips", FoliageMatBlock::new);
    public static final RegistryObject<Block> DENSE_SPRUCE_TIP_MAT = HELPER.createBlock("dense_spruce_tips", FoliageMatBlock::new);
    public static final RegistryObject<Block> DENSE_ROSELLE_PETAL_MAT = HELPER.createBlock("dense_roselle_petals", FoliageMatBlock::new);
    public static final RegistryObject<Block> DENSE_ROSE_PETAL_MAT = HELPER.createBlock("dense_rose_petals", FoliageMatBlock::new);
        //Wildflowers
    public static final RegistryObject<Block> ROSELLE_BUSH = HELPER.createBlock("roselle_bush", () ->
           new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    //STORAGE
        //Crop Crates and Sacks
    public static final RegistryObject<Block> DANDELION_ROOTS_CRATE = HELPER.createBlock("dandelion_roots_crate", () ->
            new Block(copy(ModBlocks.CABBAGE_CRATE.get())));
    public static final RegistryObject<Block> POPPY_SEEDS_SACK = HELPER.createBlock("poppy_seeds_sack", () ->
            new Block(copy(ModBlocks.RICE_BAG.get())));
    public static final RegistryObject<Block> ROSE_HIP_SACK = HELPER.createBlock("rose_hip_sack", () -> new Block(
            copy(ModBlocks.RICE_BAG.get())));
    public static final RegistryObject<Block> ROSELLE_CALYX_SACK = HELPER.createBlock("roselle_calyx_sack", () -> new Block(
            copy(ModBlocks.RICE_BAG.get())));
    public static final RegistryObject<Block> BLACK_ACORN_SACK = HELPER.createBlock("black_acorn_sack", () -> new Block(
            copy(ModBlocks.RICE_BAG.get())));
    public static final RegistryObject<Block> SPRUCE_TIPS_SACK = HELPER.createBlock("spruce_tips_sack", () -> new Block(
            copy(ModBlocks.RICE_BAG.get())));
}