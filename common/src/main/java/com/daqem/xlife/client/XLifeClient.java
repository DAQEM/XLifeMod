package com.daqem.xlife.client;

import com.daqem.xlife.client.particle.HeartParticle;
import com.daqem.xlife.client.renderer.entity.XLifeEntityRenderers;
import com.daqem.xlife.particle.XLifeParticles;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;

public class XLifeClient {

    public static void initClient() {
        XLifeEntityRenderers.init();

        ParticleProviderRegistry.register(XLifeParticles.HEART, HeartParticle.Provider::new);
    }
}
