package com.daqem.xlife.item;

import com.daqem.xlife.entity.ExtraLifeEntity;
import com.daqem.xlife.player.XLifePlayer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ExtraLifeItem extends Item {

    public ExtraLifeItem() {
        super(new Properties().arch$tab(XLifeItems.XLIFE_TAB));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        BlockPos blockPos;
        ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockHitResult blockHitResult = EnderEyeItem.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == HitResult.Type.BLOCK && level.getBlockState(blockHitResult.getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
            return InteractionResultHolder.pass(itemStack);
        }
        player.startUsingItem(interactionHand);
        if (level instanceof ServerLevel serverLevel && (blockPos = serverLevel.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, player.blockPosition(), 100, false)) != null) {
            ExtraLifeEntity eyeOfEnder = new ExtraLifeEntity(level, (XLifePlayer) player, player.getEyePosition(), player.getRotationVector());
            eyeOfEnder.setItem(itemStack);
            level.gameEvent(GameEvent.PROJECTILE_SHOOT, eyeOfEnder.position(), GameEvent.Context.of(player));
            level.addFreshEntity(eyeOfEnder);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)player, blockPos);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
            level.levelEvent(null, 1003, player.blockPosition(), 0);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            player.swing(interactionHand, true);
            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.consume(itemStack);
    }
}
