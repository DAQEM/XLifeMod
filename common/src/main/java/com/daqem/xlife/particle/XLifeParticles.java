package com.daqem.xlife.particle;

import com.daqem.xlife.XLife;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;

public class XLifeParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(XLife.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<XLifeSimpleParticleType> HEART = PARTICLE_TYPES.register("heart", XLifeSimpleParticleType::new);

    public static void init() {
        PARTICLE_TYPES.register();
    }
}
