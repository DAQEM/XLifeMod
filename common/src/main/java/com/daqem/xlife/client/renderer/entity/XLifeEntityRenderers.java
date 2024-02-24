package com.daqem.xlife.client.renderer.entity;

import com.daqem.xlife.entity.XLifeEntities;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class XLifeEntityRenderers {

    public static void init() {
        EntityRendererRegistry.register(XLifeEntities.EXTRA_LIFE, context -> new ThrownItemRenderer(context, 1.0f, true));
    }
}
