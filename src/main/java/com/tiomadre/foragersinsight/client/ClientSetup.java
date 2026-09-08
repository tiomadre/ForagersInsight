package com.tiomadre.foragersinsight.client;

import com.tiomadre.foragersinsight.client.render.blockentity.SuspiciousLitterRender;
import com.tiomadre.foragersinsight.client.render.blockentity.SapTrapRenderer;
import com.tiomadre.foragersinsight.common.block.SuspiciousLitterBlock;
import com.tiomadre.foragersinsight.common.worldgen.FIBiomes;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlockEntityTypes;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIMenuTypes;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.client.gui.DiffuserScreen;
import com.tiomadre.foragersinsight.client.gui.HandbasketScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;


//@Mod.EventBusSubscriber(
//        modid = ForagersInsight.MOD_ID,
//        bus = Bus.MOD,
//        value = Dist.CLIENT
//)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            //container screen
            MenuScreens.register(
                    FIMenuTypes.HANDBASKET_MENU.get(),
                    HandbasketScreen::new
            );
            //diffuser screen
            MenuScreens.register(
                    FIMenuTypes.DIFFUSER_MENU.get(),
                    DiffuserScreen::new
            );
            // "full" handbasket property
            ItemProperties.register(
                    FIItems.HANDBASKET.get(),
                    ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "full"),
                    (stack, world, entity, seed) ->
                            stack.getCapability(Capabilities.ItemHandler.ITEM)
                                    .map(handler -> {
                                        for (int i = 0; i < handler.getSlots(); i++) {
                                            if (handler.getStackInSlot(i).isEmpty()) {
                                                return 0.0F;
                                            }
                                        }
                                        return 1.0F;
                                    })
                                    .orElse(0.0F)
            );

            ItemBlockRenderTypes.setRenderLayer(
                    FIBlocks.SUSPICIOUS_LEAF_LITTER.get(),
                    RenderType.cutout()
            );
            ItemBlockRenderTypes.setRenderLayer(
                    FIBlocks.WOODLAND_FERN.get(),
                    RenderType.cutout()
            );
            ItemBlockRenderTypes.setRenderLayer(
                    FIBlocks.GHOST_PIPE_TORCH.get(),
                    RenderType.cutout()
            );
            ItemBlockRenderTypes.setRenderLayer(
                    FIBlocks.WALL_GHOST_PIPE_TORCH.get(),
                    RenderType.cutout()
            );

            BlockEntityRenderers.register(
                    FIBlockEntityTypes.SUSPICIOUS_LEAF_LITTER.get(),
                    SuspiciousLitterRender::new
            );
            BlockEntityRenderers.register(
                    FIBlockEntityTypes.SAP_TRAP.get(),
                    SapTrapRenderer::new
            );
        });
    }


    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> {
                    if (state == null) {
                        return FoliageColor.getDefaultColor();
                    }

                    return switch (state.getValue(SuspiciousLitterBlock.FOLIAGE)) {
                        case SPRUCE -> FoliageColor.getEvergreenColor();
                        case BIRCH -> FoliageColor.getBirchColor();
                        default -> level != null && pos != null
                                ? BiomeColors.getAverageFoliageColor(level, pos)
                                : FoliageColor.getDefaultColor();
                    };
                },
                FIBlocks.SUSPICIOUS_LEAF_LITTER.get()
        );

        event.register(
                (state, level, pos, tintIndex) -> {
                    if (level == null || pos == null) {
                        return FoliageColor.getDefaultColor();
                    }
                    return BiomeColors.getAverageGrassColor(level, pos);
                },
                FIBlocks.WOODLAND_FERN.get()
        );
        event.register(
                (state, level, pos, tintIndex) -> {
                    if (level == null || pos == null) {
                        return FoliageColor.getDefaultColor();
                    }

                    int grassColor = BiomeColors.getAverageGrassColor(level, pos);
                    if (!(level instanceof LevelReader levelReader) || !levelReader.getBiome(pos).is(FIBiomes.DARK_WOODLANDS)) {
                        return grassColor;
                    }

                    int red = (grassColor >> 16) & 0xFF;
                    int green = (grassColor >> 8) & 0xFF;
                    int blue = grassColor & 0xFF;

                    red = (int) (red * 0.85F);
                    green = (int) (green * 0.85F);
                    blue = (int) (blue * 0.85F);

                    return (red << 16) | (green << 8) | blue;
                },
                Blocks.SHORT_GRASS,
                Blocks.TALL_GRASS
        );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> {
                    SuspiciousLitterBlock.FoliageType foliageType = SuspiciousLitterBlock.FoliageType.OAK;
                    if (stack.hasTag() && stack.getTag().contains("BlockStateTag") && stack.getTag().getCompound("BlockStateTag").contains("foliage")) {
                        String foliageName = stack.getTag().getCompound("BlockStateTag").getString("foliage");
                        try {
                            foliageType = SuspiciousLitterBlock.FoliageType.valueOf(foliageName.toUpperCase());
                        } catch (IllegalArgumentException ignored) {
                        }
                    }

                    return switch (foliageType) {
                        case SPRUCE -> FoliageColor.getEvergreenColor();
                        case BIRCH -> FoliageColor.getBirchColor();
                        default -> FoliageColor.getDefaultColor();
                    };
                },
                FIBlocks.SUSPICIOUS_LEAF_LITTER.get()
        );
        event.register((stack, tintIndex) -> FoliageColor.getDefaultColor(), FIBlocks.WOODLAND_FERN.get());
    }
}