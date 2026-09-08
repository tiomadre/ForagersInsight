package com.tiomadre.foragersinsight.client;

import com.tiomadre.foragersinsight.client.particle.DiffuserScentParticleProvider;
import com.tiomadre.foragersinsight.client.particle.SapDripParticleProvider;
import com.tiomadre.foragersinsight.client.particle.SuspiciousLitterParticleProvider;
import com.tiomadre.foragersinsight.core.registry.FIParticleTypes;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.client.particle.FlameParticle;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FIClientParticles {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        //tapper drip particles
        event.registerSpriteSet(FIParticleTypes.DRIPPING_SAP.get(), SapDripParticleProvider::new);
        event.registerSpriteSet(FIParticleTypes.DRIPPING_SYRUP.get(), SapDripParticleProvider::new);
        //diffuser scent particles
        event.registerSpriteSet(FIParticleTypes.CONIFEROUS_SCENT.get(), DiffuserScentParticleProvider::new);
        event.registerSpriteSet(FIParticleTypes.ROSE_SCENT.get(), DiffuserScentParticleProvider::new);
        event.registerSpriteSet(FIParticleTypes.FLORAL_SCENT.get(), DiffuserScentParticleProvider::new);
        event.registerSpriteSet(FIParticleTypes.FLORAL_II_SCENT.get(), DiffuserScentParticleProvider::new);
        event.registerSpriteSet(FIParticleTypes.FOUL_SCENT.get(), DiffuserScentParticleProvider::new);
        //suspicious brush particles
        event.registerSpriteSet(FIParticleTypes.SUSPICIOUS_LEAVES.get(), SuspiciousLitterParticleProvider::new);
        event.registerSpriteSet(FIParticleTypes.SUSPICIOUS_NEEDLES.get(), SuspiciousLitterParticleProvider::new);
        event.registerSpriteSet(FIParticleTypes.SUSPICIOUS_FLOWER.get(), SuspiciousLitterParticleProvider::new);
        //ghost pipe torch particles
        event.registerSpriteSet(FIParticleTypes.GHOST_PIPE.get(), FlameParticle.Provider::new);
    }
}