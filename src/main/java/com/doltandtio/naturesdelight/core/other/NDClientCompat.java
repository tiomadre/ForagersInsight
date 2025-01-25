package com.doltandtio.naturesdelight.core.other;

import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class NDClientCompat {
    public static void registerCompat() {
        registerBlockColors();
    }

    private static void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();

        @SuppressWarnings("unchecked")
        List<RegistryObject<Block>> foliageColors = (List<RegistryObject<Block>>) (List<?>) Arrays.asList(
                NDBlocks.BOUNTIFUL_OAK_LEAVES
        );

        DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.get(0.5D, 1.0D), foliageColors);
        DataUtil.registerBlockItemColor(itemColors, (color, items) -> FoliageColor.get(0.5D, 1.0D), foliageColors);
    }
}
