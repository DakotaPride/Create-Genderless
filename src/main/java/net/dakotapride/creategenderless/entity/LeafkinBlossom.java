package net.dakotapride.creategenderless.entity;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LeafkinBlossom extends AbstractLeafkinMob implements NeutralMob, FlyingAnimal {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(LeafkinBlossom.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(LeafkinBlossom.class, EntityDataSerializers.INT);
    @Nullable
    private UUID persistentAngerTarget;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    public final AnimationState wanderAnimationState = new AnimationState();
    int wanderAnimationTimeout = 0;

    public LeafkinBlossom(EntityType<? extends AbstractLeafkinMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public LeafkinPetalVariant getVariant() {
        return LeafkinPetalVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(LeafkinPetalVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("PetalVariant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(VARIANT, compoundTag.getInt("PetalVariant"));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                                  MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        LeafkinPetalVariant variant = Util.getRandom(LeafkinPetalVariant.values(), this.random);
        this.setVariant(variant);
//
//        if (variant == LeafkinPetalVariant.PINK) {
//            if (this.getAttribute(Attributes.ATTACK_DAMAGE) != null)
//                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.0D);
//        }
        return super.finalizeSpawn(level, difficulty, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new LeafkinBlossomMeleeAttackGoal(20, 30, this, 0.85F, true));
        this.goalSelector.addGoal(9, new FloatGoal(this));
        this.targetSelector.addGoal(1, new LeafkinBlossomHurtByOtherGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new LeafkinBlossomSpotNatureHaterGoal(this));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    public float getEyeHeight(Pose pose) {
        return 1.2F;
    }

    public void resetAnims() {
        //this.idleAnimationState.stop();
        this.attackAnimationState.stop();
    }

    @Override
    public void setupAnimationStates() {
        if (!this.walkAnimation.isMoving() && !this.isAttacking()) {
            this.setIdle(true);
            if (this.idleAnimationTimeout <= 0) {
                //this.returnToWaitAnimationState.stop();
                this.resetAnims();
                this.idleAnimationTimeout = (int) ((20)*(1.9167F));
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        } else {
            this.setIdle(false);
            this.idleAnimationTimeout = (int) ((20)*(1.9167F));
            this.idleAnimationState.stop();
        }

        if (this.isAttacking() && this.attackAnimationTimeout <= 0) {
            this.wanderAnimationState.stop();
            this.attackAnimationTimeout = 50;
            this.attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.4F)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_27795_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_27795_);
    }

    @Override
    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID p_27791_) {
        this.persistentAngerTarget = p_27791_;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    static class LeafkinBlossomHurtByOtherGoal extends HurtByTargetGoal {
        LeafkinBlossomHurtByOtherGoal(LeafkinBlossom leafkinBlossom) {
            super(leafkinBlossom);
        }

        @Override
        protected void alertOther(Mob mob, LivingEntity livingEntity) {
            if (mob instanceof LeafkinBlossom && this.mob.hasLineOfSight(livingEntity)) {
                mob.setTarget(livingEntity);
            }

        }
    }

    static class LeafkinBlossomSpotNatureHaterGoal extends NearestAttackableTargetGoal<Player> {
        LeafkinBlossomSpotNatureHaterGoal(LeafkinBlossom leafkinBlossom) {
            super(leafkinBlossom, Player.class, 10, true, false, leafkinBlossom::isAngryAt);
        }

        @Override
        public boolean canUse() {
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (this.mob.getTarget() != null) {
                return super.canContinueToUse();
            } else {
                this.targetMob = null;
                return false;
            }
        }
    }
}
