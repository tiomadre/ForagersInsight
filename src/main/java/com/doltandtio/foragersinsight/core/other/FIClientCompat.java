package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class FIClientCompat {
    public static void registerCompat() {
        registerBlockColors();
    }

    private static void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();


        List<RegistryObject<Block>> genericFoliage = new ArrayList<>();
        genericFoliage.add(FIBlocks.BOUNTIFUL_OAK_LEAVES);
        genericFoliage.add(FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES);

        List<RegistryObject<Block>> spruceLeaves = new ArrayList<>();
        spruceLeaves.add(FIBlocks.BOUNTIFUL_SPRUCE_LEAVES);

        DataUtil.registerBlockColor(blockColors, (state, world, pos, tintIndex) -> 0xFFFFFF, spruceLeaves);
        DataUtil.registerBlockItemColor(itemColors, (stack, tintIndex) -> 0xFFFFFF, spruceLeaves);

        DataUtil.registerBlockColor(
                blockColors,
                (state, world, pos, tintIndex) ->
                        (world != null && pos != null)
                                ? BiomeColors.getAverageFoliageColor(world, pos)
                                : FoliageColor.get(0.5D, 1.0D),
                genericFoliage
        );
        DataUtil.registerBlockItemColor(
                itemColors,
                (stack, tintIndex) -> FoliageColor.get(0.5D, 1.0D),
                genericFoliage
        );
    }
}
