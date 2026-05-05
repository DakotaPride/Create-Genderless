package net.dakotapride.creategenderless.entity;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class Leafkin extends Monster {
    protected static final EntityDataAccessor<Boolean> IDLE = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SPOT_PREY = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> RETURN_TO_WAIT = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SHEDDING = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spotPreyAnimationState = new AnimationState();
    public final AnimationState returnToWaitAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState sheddingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int spotPreyAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;
    private int returnToWaitPositionAnimationTimeout = 0;
    public Leafkin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE, false);
        this.entityData.define(ATTACKING, false);
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

    public boolean isIdle() {
        return this.entityData.get(IDLE);
    }

    public void setIdle(boolean b) {
        this.entityData.set(IDLE, b);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean b) {
        this.entityData.set(ATTACKING, b);
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
        this.goalSelector.addGoal(4, new LeafkinMeleeAttackGoal(this, 0.85F, true));
        this.targetSelector.addGoal(2, new LeafkinNearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public float getEyeHeight(Pose pose) {
        return 1.2F;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (!this.walkAnimation.isMoving()) {
            this.setIdle(true);
            if (this.idleAnimationTimeout <= 0) {
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
            this.attackAnimationTimeout = (int) (2.0F*(20 * 0.8333F));
            this.attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if (this.hasSpotPrey()) {
            if (this.spotPreyAnimationTimeout <= 0) {
                this.idleAnimationState.stop();
                this.spotPreyAnimationTimeout = 30;
                this.spotPreyAnimationState.start(this.tickCount);
                if (this.isAlive()) {
                    CreateGenderlessMod.LOGGER.info("[{}] Prey Spotted: {}", CreateGenderlessMod.MOD_ID, Leafkin.this.hasSpotPrey());
                    CreateGenderlessMod.LOGGER.info("[{}] Prey Spotted Anim Timeout: {}", CreateGenderlessMod.MOD_ID, Leafkin.this.spotPreyAnimationTimeout);
                }
            }

            this.setHasSpottedPrey(false);
        }
        if (this.spotPreyAnimationTimeout > 0)
            --this.spotPreyAnimationTimeout;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F);
    }

    public class LeafkinNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public LeafkinNearestAttackableTargetGoal(Mob mob, Class<T> target, boolean canSee) {
            super(mob, target, canSee);
        }

        @Override
        public void start() {
            super.start();

            Leafkin.this.setHasSpottedPrey(true);
            Leafkin.this.spotPreyAnimationTimeout = 0;
            if (Leafkin.this.isAlive()) {
                CreateGenderlessMod.LOGGER.info("[{}] start- Prey Spotted: {}", CreateGenderlessMod.MOD_ID, Leafkin.this.hasSpotPrey());
                CreateGenderlessMod.LOGGER.info("[{}] start- Prey Spotted Anim Timeout: {}", CreateGenderlessMod.MOD_ID, Leafkin.this.spotPreyAnimationTimeout);
            }
        }
    }

    public class LeafkinMeleeAttackGoal extends MeleeAttackGoal {
        private int attackDelay = (int) (2.0F*(20 * 0.5417F));
        private int ticksUntilNextAttack = (int) (2.0F*(20 * 0.2916F));
        private boolean shouldCountTillNextAttack = false;
        public LeafkinMeleeAttackGoal(PathfinderMob mob, double speed, boolean followIfNotSeen) {
            super(mob, speed, followIfNotSeen);
        }

        @Override
        public void start() {
            super.start();
            attackDelay = (int) (2.0F*(20 * 0.5417F));
            ticksUntilNextAttack = (int) (2.0F*(20 * 0.2916F));
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
                shouldCountTillNextAttack = true;

                if (isTimeToStartAttackAnimation()) {
                    Leafkin.this.setAttacking(true);
                }

                if (isTimeToAttack()) {
                    this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                    performAttack(pEnemy);
                }
            } else {
                resetAttackCooldown();
                shouldCountTillNextAttack = false;
                Leafkin.this.setAttacking(false);
                Leafkin.this.attackAnimationTimeout = 0;
            }
        }

        private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
            return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay * 2);
        }

        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack <= 0;
        }

        protected boolean isTimeToStartAttackAnimation() {
            return this.ticksUntilNextAttack <= attackDelay;
        }

        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }


        protected void performAttack(LivingEntity pEnemy) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(pEnemy);
        }

        @Override
        public void tick() {
            super.tick();
            if (shouldCountTillNextAttack) {
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            }
        }

        @Override
        public void stop() {
            Leafkin.this.setAttacking(false);
            super.stop();
        }
    }

    public enum LeafkinVariant {
        MARSH(0),
        AUTUMN(1),
        SILK_TRAPPER(2),

        ;

        private static final LeafkinVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
                comparingInt(LeafkinVariant::getId)).toArray(LeafkinVariant[]::new);
        private final int id;

        LeafkinVariant(int id) {
            this.id = id;
        }

        public String varName() {
            return name().toLowerCase(Locale.ROOT);
        }

        public int getId() {
            return this.id;
        }

        public static LeafkinVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }
}
