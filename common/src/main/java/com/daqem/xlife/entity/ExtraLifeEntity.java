package com.daqem.xlife.entity;

import com.daqem.xlife.client.particle.HeartParticle;
import com.daqem.xlife.item.XLifeItems;
import com.daqem.xlife.particle.XLifeParticles;
import com.daqem.xlife.player.XLifePlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtraLifeEntity extends Entity implements ItemSupplier {

    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ExtraLifeEntity.class, EntityDataSerializers.ITEM_STACK);

    private @Nullable XLifePlayer player;
    private double particle;
    private double tx;
    private double ty;
    private double tz;

    public ExtraLifeEntity(Level level, @Nullable XLifePlayer player, Vec3 eyePosition, Vec2 rotationVector) {
        super(XLifeEntities.EXTRA_LIFE.get(), level);
        this.player = player;

        double yawRad = Math.toRadians(rotationVector.x);
        double pitchRad = Math.toRadians(rotationVector.y);

        double blocksForwardStart = 1D;
        double blocksUpStart = -0.5D;

        double xOffsetStart = -blocksForwardStart * Math.cos(yawRad) * Math.sin(pitchRad);
        double zOffsetStart = blocksForwardStart * Math.cos(yawRad) * Math.cos(pitchRad);

        this.setPos(eyePosition.x + xOffsetStart, eyePosition.y + blocksUpStart, eyePosition.z + zOffsetStart);

        double blocksForwardEnd = 5D;
        double blocksUpEnd = 2D;

        double xOffsetEnd = -blocksForwardEnd * Math.cos(yawRad) * Math.sin(pitchRad);
        double zOffsetEnd = blocksForwardEnd * Math.cos(yawRad) * Math.cos(pitchRad);

        this.signalTo(this.getX() + xOffsetEnd, this.getY() + blocksUpEnd, this.getZ() + zOffsetEnd);
    }

    public ExtraLifeEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public void signalTo(double x, double y, double z) {
        this.tx = x;
        this.ty = y;
        this.tz = z;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        double d = this.getX() + vec3.x;
        double e = this.getY() + vec3.y;
        double f = this.getZ() + vec3.z;
        double g = vec3.horizontalDistance();
        this.setXRot(Projectile.lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, g) * 57.2957763671875)));
        this.setYRot(Projectile.lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875)));
        if (!this.level().isClientSide) {
            double h = this.tx - d;
            double i = this.tz - f;
            float j = (float)Math.sqrt(h * h + i * i);
            float k = (float)Mth.atan2(i, h);
            double l = Mth.lerp(0.0025, g, j);
            double m = vec3.y;
            if (j < 1.0f) {
                l *= 0.8;
                m *= 0.8;
            }
            int n = this.getY() < this.ty ? 1 : -1;
            vec3 = new Vec3(Math.cos(k) * l, m + ((double)n - m) * (double)0.015f, Math.sin(k) * l);
            this.setDeltaMovement(vec3);
        }
        if (this.isInWater()) {
            for (int p = 0; p < 4; ++p) {
                this.level().addParticle(ParticleTypes.BUBBLE, d - vec3.x * 0.25, e - vec3.y * 0.25, f - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }
        } else {
            this.level().addParticle(XLifeParticles.HEART.get(), d - vec3.x * 0.25 + this.random.nextDouble() * 0.6 - 0.3, e - vec3.y * 0.25 - 0.5, f - vec3.z * 0.25 + this.random.nextDouble() * 0.6 - 0.3, 0.2, 16, 0);
        }
        if (!this.level().isClientSide) {
            this.setPos(d, e, f);
            if (this.tickCount >= 85 && this.tickCount % 3 == 0) {
                this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5f, 0.8f);
            }
            if (this.tickCount >= 100) {
                this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.5f, 1.0f);
                this.discard();
                if (this.player != null) {
                    if (this.player.x_life_mod$getLives() >= 10) {
                        this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(XLifeItems.EXTRA_LIFE.get(), 1)));
                    } else {
                        this.player.x_life_mod$addLife();
                    }
                }
            }
        } else {
            this.setPosRaw(d, e, f);
        }

        if (this.level().isClientSide) {
            this.particle += 0.5;
            double x = this.getX() + Math.cos(this.particle) * 0.35;
            double y = this.getY();
            double z = this.getZ() + Math.sin(this.particle) * 0.35;
            this.level().addParticle(XLifeParticles.HEART.get(), x, y, z, 0.35, 8, 0);

            if (this.tickCount >= 85 && this.tickCount % 2 == 0) {
                this.level().addParticle(XLifeParticles.HEART.get(), this.getX() + (Math.random() - 0.5D), this.getY() + (Math.random() - 0.5D), this.getZ() + (Math.random() - 0.5D), 1.0, 16.0, 0.0);
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, new ItemStack(XLifeItems.EXTRA_LIFE.get()));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public @NotNull ItemStack getItem() {
        return new ItemStack(XLifeItems.EXTRA_LIFE.get());
    }

    public void setItem(ItemStack itemStack) {
        if (!itemStack.is(XLifeItems.EXTRA_LIFE.get()) || itemStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, itemStack.copyWithCount(1));
        }
    }
}
