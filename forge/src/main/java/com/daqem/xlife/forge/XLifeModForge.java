package com.daqem.xlife.forge;

import com.daqem.xlife.XLife;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(XLife.MOD_ID)
public class XLifeModForge {
    public XLifeModForge() {
        EventBuses.registerModEventBus(XLife.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        XLife.initCommon();
    }
}
