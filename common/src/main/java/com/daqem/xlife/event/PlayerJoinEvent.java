package com.daqem.xlife.event;

import com.daqem.xlife.player.XLifeServerPlayer;
import dev.architectury.event.events.common.PlayerEvent;

public class PlayerJoinEvent {

    public static void registerEvent() {
        PlayerEvent.PLAYER_JOIN.register(player -> {
            if (player instanceof XLifeServerPlayer serverPlayer) {
                serverPlayer.x_life_mod$setLives(serverPlayer.x_life_mod$getLives());
            }
        });
    }
}
