package net.dakotapride.creategenderless.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class LeafkinMeleeAttackGoal extends MeleeAttackGoal {
    AbstractLeafkinMob leafkin;

    private int attackDelay;
    private int ticksUntilNextAttack;
    private boolean shouldCountTillNextAttack = false;

    private final int trackDelay;
    private final int trackTicks;

    public LeafkinMeleeAttackGoal(int attackDelay, int ticksUntilNextAttack, AbstractLeafkinMob abstractLeafkinMob, double speed, boolean followIfNotSeen) {
        super(abstractLeafkinMob, speed, followIfNotSeen);
        this.attackDelay = attackDelay;
        this.ticksUntilNextAttack = ticksUntilNextAttack;

        this.trackDelay = attackDelay;
        this.trackTicks = ticksUntilNextAttack;
        this.leafkin = abstractLeafkinMob;
    }

    @Override
    public void start() {
        super.start();
        attackDelay = trackDelay;
        ticksUntilNextAttack = trackTicks;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if (isTimeToStartAttackAnimation()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                this.leafkin.setAttacking(true);
            }

            if (isTimeToAttack()) {
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            this.leafkin.setAttacking(false);
            this.leafkin.attackAnimationTimeout = 0;
        }
    }

    public boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
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
        this.leafkin.setAttacking(false);
        super.stop();
    }
}