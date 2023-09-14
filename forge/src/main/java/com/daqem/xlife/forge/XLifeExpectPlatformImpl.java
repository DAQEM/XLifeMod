package com.daqem.xlife.forge;

import com.daqem.xlife.XLifeExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class XLifeExpectPlatformImpl {
    /**
     * This is our actual method to {@link XLifeExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
