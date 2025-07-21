package com.doltandtio.foragersinsight.client.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

public class SapDripParticle extends TextureSheetParticle {
    public SapDripParticle(ClientLevel level, double x, double y, double z, SpriteSet sprite) {
        super(level, x, y, z);
        this.gravity = 0.1F;
        this.lifetime = 40 + level.random.nextInt(20);
        this.setSize(0.02F, 0.02F);
        this.pickSprite(sprite);
    }
    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}