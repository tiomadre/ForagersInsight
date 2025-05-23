package com.doltandtio.foragersinsight.client;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIMenuTypes;
import com.doltandtio.foragersinsight.client.screen.HandbasketScreen;

import net.minecraft.client.gui.screens.MenuScreens;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = ForagersInsight.MOD_ID,
        bus   = Bus.MOD,
        value = Dist.CLIENT
)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(
                    FIMenuTypes.HANDBASKET_MENU.get(),
                    HandbasketScreen::new
            );
        });
    }
}
