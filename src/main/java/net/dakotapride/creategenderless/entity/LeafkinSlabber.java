package net.dakotapride.creategenderless.entity;

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

public class LeafkinSlabber extends Monster {
    protected static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(LeafkinSlabber.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Leafkin.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int sleepingAnimationTimeout = 0;
    public LeafkinSlabber(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SLEEPING, false);
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

    public boolean isSleepingValue() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleepingValue(boolean b) {
        this.entityData.set(SLEEPING, b);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0F, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public float getEyeHeight(Pose pose) {
        return 0.1F;
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
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 60;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        } else {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.stop();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }
}
