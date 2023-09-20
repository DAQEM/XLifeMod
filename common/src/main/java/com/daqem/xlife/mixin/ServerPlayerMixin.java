package com.daqem.xlife.mixin;

import com.daqem.xlife.XLife;
import com.daqem.xlife.config.XLifeConfig;
import com.daqem.xlife.player.XLifeServerPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements XLifeServerPlayer {

    @Unique
    private int x_life_mod$lives = 10;

    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(level, blockPos, f, gameProfile, profilePublicKey);
    }

    @Override
    public int x_life_mod$getLives() {
        return x_life_mod$lives;
    }

    @Override
    public void x_life_mod$setLives(int lives) {
        x_life_mod$lives = lives;
        x_life_mod$applyAttributeModifier();
    }

    @Override
    public void x_life_mod$removeLife() {
        x_life_mod$setLives(x_life_mod$getLives() - 1);
    }

    @Override
    public void x_life_mod$addLife() {
        x_life_mod$setLives(x_life_mod$getLives() + 1);
    }

    @Unique
    private void x_life_mod$applyAttributeModifier() {
        ServerPlayer player = x_life_mod$getServerPlayer();
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        int modifierAmount;
        if (XLifeConfig.invertHearts.get()) {
            modifierAmount = x_life_mod$lives * 2 - 20;
        } else {
            modifierAmount = -x_life_mod$lives * 2 + 2;
        }
        int health = x_life_mod$lives * 2;
        if (attribute != null) {
            AttributeModifier modifier = attribute.getModifier(XLife.ATTRIBUTE_MODIFIER_ID);
            if (modifier != null) {
                attribute.removeModifier(modifier);
            }
            AttributeModifier modifier1 = new AttributeModifier(XLife.ATTRIBUTE_MODIFIER_ID, "X Life Health", modifierAmount, AttributeModifier.Operation.ADDITION);
            attribute.addPermanentModifier(modifier1);
        }
        if (health < player.getHealth()) {
            player.setHealth(health);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public ServerPlayer x_life_mod$getServerPlayer() {
        return (ServerPlayer) (Object) this;
    }

    @Inject(at = @At("TAIL"), method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V")
    public void die(DamageSource damageSource, CallbackInfo ci) {
        if (x_life_mod$getLives() == 1) {
            x_life_mod$lives = 0;
            MinecraftServer server = x_life_mod$getServerPlayer().getServer();
            if (server != null) {
                UserBanList userBanList = server.getPlayerList().getBans();
                UserBanListEntry userBanListEntry = new UserBanListEntry(x_life_mod$getServerPlayer().getGameProfile(), null, null, null, XLife.translate("disconnect.ban").getString());
                userBanList.add(userBanListEntry);
            }
            x_life_mod$getServerPlayer().connection.disconnect(XLife.translate("disconnect.ban"));
        }
        x_life_mod$setLives(x_life_mod$getLives() - 1);
    }

    @Inject(at = @At("TAIL"), method = "restoreFrom(Lnet/minecraft/server/level/ServerPlayer;Z)V")
    public void restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (oldPlayer instanceof XLifeServerPlayer oldXLifeServerPlayer) {
            x_life_mod$setLives(oldXLifeServerPlayer.x_life_mod$getLives());
        }
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag xLifeTag = new CompoundTag();
        xLifeTag.putInt("Lives", x_life_mod$lives);
        compoundTag.put("XLife", xLifeTag);
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag xLifeTag = compoundTag.getCompound("XLife");
        int lives = xLifeTag.getInt("Lives");
        if (lives == 0) {
            lives = 10;
        }
        x_life_mod$setLives(lives);
    }
}
