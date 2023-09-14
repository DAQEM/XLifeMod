package com.daqem.xlife;

import com.daqem.xlife.event.RegisterCommandsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class XLife {
    public static final String MOD_ID = "xlife";
    public static final UUID ATTRIBUTE_MODIFIER_ID = UUID.fromString("6cff330e-0aa7-4d3f-bfde-f8f3b20ea757");
    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        registerEvents();
    }

    public static void registerEvents() {
        RegisterCommandsEvent.registerEvent();
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
}
