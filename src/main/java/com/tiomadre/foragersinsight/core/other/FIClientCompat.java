package com.tiomadre.foragersinsight.core.other;

import com.tiomadre.foragersinsight.common.block.SuspiciousLitterBlock;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;

public class FIClientCompat {
    public static void registerCompat() {

    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
                event.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.get(0.5D, 1.0D),
                FIBlocks.SUSPICIOUS_LEAF_LITTER.get(),
                        FIBlocks.BOUNTIFUL_OAK_LEAVES.get(),
                        FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES.get(),
                        FIBlocks.WOODLAND_FERN.get(),
                        FIBlocks.LILAC_LEAVES.get(),
                        FIBlocks.BLOSSOMING_LILAC_LEAVES.get(),
                        FIBlocks.BOUNTIFUL_SPRUCE_LEAVES.get()
                );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event){
        event.register((item, tintIndex) -> FoliageColor.get(0.5D, 1.0D),
                FIBlocks.SUSPICIOUS_LEAF_LITTER.get(),
                FIBlocks.BOUNTIFUL_OAK_LEAVES.get(),
                FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES.get(),
                FIBlocks.WOODLAND_FERN.get(),
                FIBlocks.LILAC_LEAVES.get(),
                FIBlocks.BLOSSOMING_LILAC_LEAVES.get(),
                FIBlocks.BOUNTIFUL_SPRUCE_LEAVES.get());
    }
}