package com.daqem.xlife.fabric;

import com.daqem.xlife.XLifeExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class XLifeExpectPlatformImpl {
    /**
     * This is our actual method to {@link XLifeExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
