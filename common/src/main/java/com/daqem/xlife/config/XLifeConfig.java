package com.daqem.xlife.config;

import com.daqem.xlife.XLife;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;

import java.util.function.Supplier;

public class XLifeConfig {

    public static void init() {
    }

    public static final Supplier<Boolean> isDebug;
    public static final Supplier<Boolean> invertHearts;


    static {
        IConfigBuilder config = ConfigBuilders.newTomlConfig("xlife", null, false);

        config.push("general");
        invertHearts = config.comment("if true, hearts are inverted").define("invert_hearts", false);
        config.pop();

        config.push("debug");
        isDebug = config.comment("if true, debug mode is enabled").define("is_debug", false);
        config.pop();

        config.build();
    }
}
