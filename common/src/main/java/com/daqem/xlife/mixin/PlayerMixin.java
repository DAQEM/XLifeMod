package com.daqem.xlife.mixin;

import com.daqem.xlife.config.XLifeConfig;
import com.daqem.xlife.player.XLifePlayer;
import com.daqem.xlife.player.XLifeServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements XLifePlayer {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "eat")
    private void eat(Level level, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        if (this instanceof XLifeServerPlayer serverPlayer) {
            if (XLifeConfig.enchantedGoldenAppleAddsLife.get()) {
                if (itemStack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
                    serverPlayer.x_life_mod$addLife();
                }
            }
        }
    }
}
