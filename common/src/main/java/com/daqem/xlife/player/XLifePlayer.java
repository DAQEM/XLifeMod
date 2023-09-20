package com.daqem.xlife.player;

public interface XLifePlayer {

    int x_life_mod$getLives();

    void x_life_mod$setLives(int lives);

    @SuppressWarnings("unused")
    void x_life_mod$removeLife();

    void x_life_mod$addLife();
}
