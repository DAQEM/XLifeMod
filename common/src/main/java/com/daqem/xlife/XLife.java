package com.daqem.xlife;

import com.daqem.xlife.config.XLifeConfig;
import com.daqem.xlife.entity.XLifeEntities;
import com.daqem.xlife.event.PlayerJoinEvent;
import com.daqem.xlife.event.RegisterCommandsEvent;
import com.daqem.xlife.item.XLifeItems;
import com.daqem.xlife.particle.XLifeParticles;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.UUID;

public class XLife {
    public static final String MOD_ID = "xlife";
    public static final UUID ATTRIBUTE_MODIFIER_ID = UUID.fromString("6cff330e-0aa7-4d3f-bfde-f8f3b20ea757");
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void initCommon() {
        XLifeItems.init();
        XLifeEntities.init();
        XLifeParticles.init();
        XLifeConfig.init();
        registerEvents();
    }

    public static void registerEvents() {
        RegisterCommandsEvent.registerEvent();
        PlayerJoinEvent.registerEvent();
    }

    public static ResourceLocation getId(String id) {
        return new ResourceLocation(MOD_ID, id);
    }

    public static Component translate(String s) {
        return Component.translatable(MOD_ID + "." + s);
    }

    public static Component translate(String s, Object... args) {
        return Component.translatable(MOD_ID + "." + s, args);
    }

    public static boolean isDebug() {
        return XLifeConfig.isDebug.get();
    }
}
