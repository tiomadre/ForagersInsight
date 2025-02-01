package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.common.block.BountifulLeavesBlock;
import com.doltandtio.naturesdelight.common.block.DandelionBushBlock;
import com.doltandtio.naturesdelight.common.block.DoubleCropBlock;
import com.doltandtio.naturesdelight.common.block.PoppyBushBlock;
import com.doltandtio.naturesdelight.common.worldgen.trees.grower.BountifulDarkOakTreeGrower;
import com.doltandtio.naturesdelight.common.worldgen.trees.grower.BountifulOakGrower;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.DarkOakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import static com.doltandtio.naturesdelight.core.registry.NDItems.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = NaturesDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NDBlocks {
    public static final BlockSubRegistryHelper HELPER = NaturesDelight.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> POPPY_BUSH = HELPER.createBlockNoItem("poppy_bush", () ->
            new PoppyBushBlock(BlockBehaviour.Properties.copy(Blocks.BEETROOTS)));
    public static final RegistryObject<Block> DANDELION_BUSH = HELPER.createBlockNoItem("dandelion_bush", () ->
            new DandelionBushBlock(BlockBehaviour.Properties.copy(Blocks.POTATOES)));

    public static final RegistryObject<Block> BOUNTIFUL_OAK_SAPLING = HELPER.createFuelBlock("bountiful_oak_sapling", () ->
            new SaplingBlock(new BountifulOakGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), 100);
    public static final RegistryObject<Block> BOUNTIFUL_OAK_LEAVES = HELPER.createBlock("bountiful_oak_leaves", () ->
            new BountifulLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES), () -> Items.APPLE));

    public static final RegistryObject<Block> BOUNTIFUL_DARK_OAK_SAPLING = HELPER.createFuelBlock("bountiful_dark_oak_sapling", () ->
            new SaplingBlock(new BountifulDarkOakTreeGrower(), BlockBehaviour.Properties.copy(Blocks.DARK_OAK_SAPLING)), 100);
    public static final RegistryObject<Block> BOUNTIFUL_DARK_OAK_LEAVES = HELPER.createBlock("bountiful_dark_oak_leaves", () ->
            new BountifulLeavesBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LEAVES), BLACK_ACORN));

    public static final RegistryObject<Block> ROSE_HIP = HELPER.createBlock("rose_hip", () -> new DoubleCropBlock(
            BlockBehaviour.Properties.copy(Blocks.WHEAT), 3));

    public static final RegistryObject<Block> DANDELION_ROOTS_CRATE = HELPER.createBlock("dandelion_roots_crate", () ->
            new Block(BlockBehaviour.Properties.copy(ModBlocks.CABBAGE_CRATE.get())));
    public static final RegistryObject<Block> POPPY_SEEDS_SACK = HELPER.createBlock("poppy_seeds_sack", () ->
            new Block(BlockBehaviour.Properties.copy(ModBlocks.RICE_BAG.get())));
    public static final RegistryObject<Block> ROSE_HIP_CRATE = HELPER.createBlock("rose_hip_crate", () -> new Block(
            BlockBehaviour.Properties.copy(ModBlocks.CABBAGE_CRATE.get())));
    public static final RegistryObject<Block> ROSE_PETALS_SACK = HELPER.createBlock("rose_petals_sack", () -> new Block(
            BlockBehaviour.Properties.copy(ModBlocks.RICE_BAG.get())));


    public static void setupTabEditors() {
        CreativeModeTabContentsPopulator.mod(NaturesDelight.MOD_ID)
                .tab(CreativeModeTabs.NATURAL_BLOCKS)
                    .addItemsAfter(of(Items.BEETROOT_SEEDS), ROSE_HIP, POPPY_SEEDS, DANDELION_ROOT)
                    .addItemsAfter(of(Items.OAK_SAPLING), BOUNTIFUL_OAK_SAPLING)
                    .addItemsAfter(of(Items.OAK_LEAVES), BOUNTIFUL_OAK_LEAVES)
                    .addItemsAfter(of(Items.DARK_OAK_SAPLING), BOUNTIFUL_DARK_OAK_SAPLING)
                    .addItemsAfter(of(Items.DARK_OAK_LEAVES), BOUNTIFUL_DARK_OAK_LEAVES)
                    .addItemsAfter(of(Items.HAY_BLOCK), ROSE_HIP_CRATE, ROSE_PETALS_SACK)
                .tab(CreativeModeTabs.FOOD_AND_DRINKS)
                    .addItemsAfter(of(Items.COOKIE), ROSE_COOKIE)
                    .addItemsAfter(of(Items.APPLE), BLACK_ACORN)
                    .addItemsAfter(of(Items.HONEY_BOTTLE), ROSE_GRANITA, ROSE_CORDIAL)
                .tab(CreativeModeTabs.INGREDIENTS)
                    .addItemsAfter(of(Items.WHEAT), ROSE_PETALS, CRUSHED_ICE);
    }
}
