package com.doltandtio.foragersinsight.client;

import com.doltandtio.foragersinsight.client.particle.SapDripParticleProvider;
import com.doltandtio.foragersinsight.core.registry.FIParticleTypes;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FIClientParticles {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FIParticleTypes.DRIPPING_SAP.get(), SapDripParticleProvider::new);
    }
}
