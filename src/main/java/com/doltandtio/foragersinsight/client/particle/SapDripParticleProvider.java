package com.doltandtio.foragersinsight.client.particle;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class SapDripParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public SapDripParticleProvider(SpriteSet sprite) {
        this.sprite = sprite;
    }

    @Override
    public TextureSheetParticle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level,
                                               double x, double y, double z,
                                               double vx, double vy, double vz) {
        return new SapDripParticle(level, x, y, z, sprite);
    }
}
