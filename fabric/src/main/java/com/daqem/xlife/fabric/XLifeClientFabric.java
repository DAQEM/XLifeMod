package com.daqem.xlife.fabric;

import com.daqem.xlife.client.XLifeClient;
import net.fabricmc.api.ClientModInitializer;

public class XLifeClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        XLifeClient.initClient();
    }
}
