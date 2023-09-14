package com.daqem.xlife.mixin.client;

import com.daqem.xlife.player.XLifeLocalPlayer;
import com.daqem.xlife.player.XLifePlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements XLifeLocalPlayer {

    @Unique
    private int x_life_mod$lives = 0;

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(clientLevel, gameProfile, profilePublicKey);
    }

    @Override
    public int x_life_mod$getLives() {
        return x_life_mod$lives;
    }

    @Override
    public void x_life_mod$setLives(int lives) {
        x_life_mod$lives = lives;
    }

    @Override
    public void x_life_mod$removeLife() {
        x_life_mod$lives--;
    }
}
