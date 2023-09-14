package com.daqem.xlife.player;

import net.minecraft.server.level.ServerPlayer;

public interface XLifeServerPlayer extends XLifePlayer {

    ServerPlayer x_life_mod$getServerPlayer();
}
