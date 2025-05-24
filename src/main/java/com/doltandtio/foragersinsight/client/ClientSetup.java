package com.doltandtio.foragersinsight.client;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIMenuTypes;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import com.doltandtio.foragersinsight.client.screen.HandbasketScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = ForagersInsight.MOD_ID,
        bus = Bus.MOD,
        value = Dist.CLIENT
)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            //container screen
            MenuScreens.register(
                    FIMenuTypes.HANDBASKET_MENU.get(),
                    HandbasketScreen::new
            );
            // "full" handbasket property
            ItemProperties.register(
                    FIItems.HANDBASKET.get(),
                    new ResourceLocation(ForagersInsight.MOD_ID, "full"),
                    (stack, world, entity, seed) ->
                            stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
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
        });
    }
}
