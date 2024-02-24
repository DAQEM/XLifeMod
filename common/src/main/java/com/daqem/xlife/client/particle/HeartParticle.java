package com.daqem.xlife.client.particle;

import com.daqem.xlife.particle.XLifeSimpleParticleType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class HeartParticle extends TextureSheetParticle {

    HeartParticle(ClientLevel clientLevel, double d, double e, double f) {
        super(clientLevel, d, e, f, 0.0, 0.0, 0.0);
        this.speedUpWhenYMotionIsBlocked = true;
        this.friction = 0.86f;
        this.xd *= 0.01f;
        this.yd *= 0.01f;
        this.zd *= 0.01f;
        this.yd += 0.1;
        this.quadSize *= 1.5f;
        this.lifetime = 16;
        this.hasPhysics = false;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp(((float)this.age + f) / (float)this.lifetime * 32.0f, 0.0f, 1.0f);
    }


    @Environment(value=EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            HeartParticle heartParticle = new HeartParticle(clientLevel, d, e, f);
            heartParticle.quadSize *= (float) g;
            heartParticle.lifetime = (int) h;
            heartParticle.pickSprite(this.sprite);
            return heartParticle;
        }
    }
}
