package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.common.block.DoubleCropBlock;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import static com.doltandtio.naturesdelight.core.registry.NDItems.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = NaturesDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NDBlocks {
    public static final BlockSubRegistryHelper HELPER = NaturesDelight.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<DoubleCropBlock> ROSE_HIP = HELPER.createBlock("rose_hip", () -> new DoubleCropBlock(
            BlockBehaviour.Properties.copy(Blocks.WHEAT), 3));


    public static void setupTabEditors() {
        CreativeModeTabContentsPopulator.mod(NaturesDelight.MOD_ID)
                .tab(CreativeModeTabs.NATURAL_BLOCKS)
                    .addItemsAfter(of(Items.BEETROOT_SEEDS), ROSE_HIP)
                .tab(CreativeModeTabs.FOOD_AND_DRINKS)
                    .addItemsAfter(of(Items.COOKIE), ROSE_COOKIE)
                    .addItemsAfter(of(Items.HONEY_BOTTLE), ROSE_GRANITA, ROSE_WATER_CORDIAL)
                .tab(CreativeModeTabs.INGREDIENTS)
                    .addItemsAfter(of(Items.WHEAT), ROSE_PETALS, CRUSHED_ICE);

    }
}
