package com.doltandtio.foragersinsight.core.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class FIParticleTypes {
    public static final String MODID = "foragersinsight";

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<SimpleParticleType> DRIPPING_SAP =
            PARTICLES.register("dripping_sap", () -> new SimpleParticleType(false));

    private FIParticleTypes() {}

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }
}
