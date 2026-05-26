package net.dakotapride.creategenderless.entity;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Leafkin extends AbstractLeafkinMob {
    protected static final EntityDataAccessor<Boolean> SPOT_PREY = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> RETURN_TO_WAIT = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SHEDDING = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.INT);

    public final AnimationState spotPreyAnimationState = new AnimationState();
    public final AnimationState returnToWaitAnimationState = new AnimationState();
    public final AnimationState sheddingAnimationState = new AnimationState();
    private int spotPreyAnimationTimeout = 0;
    private int returnToWaitPositionAnimationTimeout = 0;
    public Leafkin(EntityType<? extends AbstractLeafkinMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPOT_PREY, false);
        this.entityData.define(RETURN_TO_WAIT, false);
        this.entityData.define(SHEDDING, false);
        this.entityData.define(VARIANT, 0);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public LeafkinVariant getVariant() {
        return LeafkinVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(LeafkinVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(VARIANT, compoundTag.getInt("Variant"));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                                  MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        LeafkinVariant variant = Util.getRandom(LeafkinVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, mobSpawnType, spawnGroupData, compoundTag);
    }

    public boolean hasSpotPrey() {
        return this.entityData.get(SPOT_PREY);
    }

    public void setHasSpottedPrey(boolean b) {
        this.entityData.set(SPOT_PREY, b);
    }

    public boolean canReturnToWaitingPosition() {
        return this.entityData.get(RETURN_TO_WAIT);
    }

    public void setToWaitPosition(boolean b) {
        this.entityData.set(RETURN_TO_WAIT, b);
    }

    public boolean isShedding() {
        return this.entityData.get(SHEDDING);
    }

    public void setShedding(boolean b) {
        this.entityData.set(SHEDDING, b);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(4, new LeafkinMeleeAttackGoal((int) (20 * 0.5417F), (int) (20 * 0.2916F), this, 0.85F, true));
        this.targetSelector.addGoal(2, new LeafkinNearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public float getEyeHeight(Pose pose) {
        return 1.2F;
    }

    public void resetAnims() {
        //this.idleAnimationState.stop();
        this.spotPreyAnimationState.stop();
        this.returnToWaitAnimationState.stop();
        this.attackAnimationState.stop();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.ticksUntilReturnToWaitAnimMustHalt > 0) {
            --this.ticksUntilReturnToWaitAnimMustHalt;
            this.idleAnimationState.stop();
        }
        if (this.ticksUntilReturnToWaitAnimMustHalt <= 0)
            this.setToWaitPosition(false);
    }

    @Override
    public void setupAnimationStates() {
        if (!this.walkAnimation.isMoving() && !this.isAttacking()) {
            this.setIdle(true);
            if (this.idleAnimationTimeout <= 0) {
                //this.returnToWaitAnimationState.stop();
                this.resetAnims();
                this.idleAnimationTimeout = 45;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        } else {
            this.setIdle(false);
            this.idleAnimationTimeout = 45;
            this.idleAnimationState.stop();
        }

        if (this.isAttacking() && this.attackAnimationTimeout <= 0) {
            this.attackAnimationTimeout = (int) (20 * 0.8333F);
            this.attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if (this.hasSpotPrey()) {
            if (this.spotPreyAnimationTimeout <= 0) {
                this.idleAnimationState.stop();
                this.spotPreyAnimationTimeout = 30;
                this.spotPreyAnimationState.start(this.tickCount);
            }
        }
        if (this.spotPreyAnimationTimeout > 0)
            --this.spotPreyAnimationTimeout;

        if (this.canReturnToWaitingPosition()) {
            if (this.returnToWaitPositionAnimationTimeout <= 0) {
                //this.idleAnimationState.stop();
                this.returnToWaitPositionAnimationTimeout = 20;
                this.returnToWaitAnimationState.start(this.tickCount);
            }
        }
        if (this.returnToWaitPositionAnimationTimeout > 0)
            --this.returnToWaitPositionAnimationTimeout;

        //this.returnToWaitAnimationState.animateWhen(this.canReturnToWaitingPosition(), this.tickCount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    private int ticksUntilSpotPreyAnimNoLongerApplicable = 15;
    private int ticksUntilReturnToWaitAnimMustHalt = 10;
    public class LeafkinNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public LeafkinNearestAttackableTargetGoal(Mob mob, Class<T> target, boolean canSee) {
            super(mob, target, canSee);
        }

        @Override
        public void tick() {
            super.tick();

            if (Leafkin.this.ticksUntilSpotPreyAnimNoLongerApplicable > 0)
                --Leafkin.this.ticksUntilSpotPreyAnimNoLongerApplicable;

            if (Leafkin.this.ticksUntilSpotPreyAnimNoLongerApplicable <= 0)
                Leafkin.this.setHasSpottedPrey(false);
        }

        @Override
        public void start() {
            super.start();
            Leafkin.this.setToWaitPosition(false);

            Leafkin.this.ticksUntilSpotPreyAnimNoLongerApplicable = 15;

            Leafkin.this.setHasSpottedPrey(true);
            Leafkin.this.spotPreyAnimationTimeout = 0;
        }

        @Override
        public void stop() {
            super.stop();
            Leafkin.this.ticksUntilReturnToWaitAnimMustHalt = 10;

            Leafkin.this.setToWaitPosition(true);
            Leafkin.this.returnToWaitPositionAnimationTimeout = 0;
        }
    }
}
