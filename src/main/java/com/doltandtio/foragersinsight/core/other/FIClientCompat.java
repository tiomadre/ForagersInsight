package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.client.particle.SapDripParticleProvider;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIParticleTypes;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class FIClientCompat {
    public static void registerCompat() {
        registerBlockColors();
        registerParticleFactories(); // ← Add this
    }

    private static void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();

        List<RegistryObject<Block>> genericFoliage = Arrays.asList(
                FIBlocks.BOUNTIFUL_OAK_LEAVES, FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES
        );

        DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.get(0.5D, 1.0D), genericFoliage);
        DataUtil.registerBlockItemColor(itemColors, (color, items) -> FoliageColor.get(0.5D, 1.0D), genericFoliage);
    }

    private static void registerParticleFactories() {
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        particleEngine.register(FIParticleTypes.DRIPPING_SAP.get(), SapDripParticleProvider::new);
    }
}
