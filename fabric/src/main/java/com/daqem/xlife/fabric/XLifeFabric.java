package com.daqem.xlife.fabric;

import com.daqem.xlife.XLife;
import net.fabricmc.api.ModInitializer;

public class XLifeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        XLife.initCommon();
    }
}
