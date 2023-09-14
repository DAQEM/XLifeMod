package com.daqem.xlife.mixin.client;

import com.daqem.xlife.XLife;
import com.daqem.xlife.player.XLifeLocalPlayer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    @Inject(method = "setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", at = @At("TAIL"))
    private static void setShaderTexture(int i, ResourceLocation resourceLocation, CallbackInfo ci) {
        if (resourceLocation.equals(Gui.GUI_ICONS_LOCATION)) {
            if (RenderSystem.isOnRenderThread()) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
                    if (attribute != null) {
                        AttributeModifier modifier = attribute.getModifier(XLife.ATTRIBUTE_MODIFIER_ID);
                        if (modifier != null) {
                            int lives = ((int) modifier.getAmount() + 20) / 2;
                            ResourceLocation resourceLocation1 = x_life_mod$getHeartColorLocation(lives);
                            RenderSystem._setShaderTexture(i, resourceLocation1);
                        }
                    }
                }
            }
        }
    }

    @Unique
    private static ResourceLocation x_life_mod$getHeartColorLocation(int lives) {
        return switch (lives) {
            case 1 -> XLife.getId("textures/gui/black.png");
            case 2 -> XLife.getId("textures/gui/lime.png");
            case 3 -> XLife.getId("textures/gui/cyan.png");
            case 4 -> XLife.getId("textures/gui/yellow.png");
            case 5 -> XLife.getId("textures/gui/purple.png");
            case 6 -> XLife.getId("textures/gui/pink.png");
            case 7 -> XLife.getId("textures/gui/orange.png");
            case 8 -> XLife.getId("textures/gui/green.png");
            case 9 -> XLife.getId("textures/gui/blue.png");
            default -> Gui.GUI_ICONS_LOCATION;
        };
    }
}
