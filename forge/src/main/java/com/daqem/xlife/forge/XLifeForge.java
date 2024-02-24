package com.daqem.xlife.forge;

import com.daqem.xlife.XLife;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(XLife.MOD_ID)
public class XLifeForge {
    public XLifeForge() {
        EventBuses.registerModEventBus(XLife.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        DistExecutor.safeRunForDist(
                () -> SideProxyForge.Client::new,
                () -> SideProxyForge.Server::new
        );
    }
}
