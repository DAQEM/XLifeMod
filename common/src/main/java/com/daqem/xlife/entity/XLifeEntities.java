package com.daqem.xlife.entity;

import com.daqem.xlife.XLife;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class XLifeEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(XLife.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<?>> EXTRA_LIFE = ENTITY_TYPES.register("extra_life", () ->
            EntityType.Builder.of(ExtraLifeEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(4).build("extra_life"));

    public static void init() {
        ENTITY_TYPES.register();
    }
}
