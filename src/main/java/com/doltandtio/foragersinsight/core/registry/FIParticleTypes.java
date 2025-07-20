package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FIParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ForagersInsight.MOD_ID);

    public static final RegistryObject<SimpleParticleType> DRIPPING_SAP =
            PARTICLES.register("dripping_sap", () -> new SimpleParticleType(true));
}
