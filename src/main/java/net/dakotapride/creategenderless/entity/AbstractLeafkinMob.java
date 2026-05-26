package net.dakotapride.creategenderless.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractLeafkinMob extends Monster {
    protected static final EntityDataAccessor<Boolean> IDLE = SynchedEntityData.defineId(AbstractLeafkinMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(AbstractLeafkinMob.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    int idleAnimationTimeout = 0;
    int attackAnimationTimeout = 0;
    protected AbstractLeafkinMob(EntityType<? extends Monster> p_33002_, Level level) {
        super(p_33002_, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    public void setupAnimationStates() {}

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(IDLE, false);
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
}
