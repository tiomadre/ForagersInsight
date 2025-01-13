package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.core.DoubleCropBlock;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NaturesDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NDBlocks {
    public static final BlockSubRegistryHelper HELPER = NaturesDelight.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> ROSE_HIP = HELPER.createBlock("rose_hip", () -> new DoubleCropBlock(
            BlockBehaviour.Properties.copy(Blocks.WHEAT), 3));

}
