package net.dakotapride.creategenderless.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;

public class LeafkinBlossomMeleeAttackGoal extends LeafkinMeleeAttackGoal {
    public LeafkinBlossomMeleeAttackGoal(int attackDelay, int ticksUntilNextAttack, AbstractLeafkinMob abstractLeafkinMob, double speed, boolean followIfNotSeen) {
        super(attackDelay, ticksUntilNextAttack, abstractLeafkinMob, speed, followIfNotSeen);
    }

    @Override
    protected void performAttack(LivingEntity pEnemy) {
        super.performAttack(pEnemy);

        if (!this.leafkin.level().isClientSide)
            this.spawnMobEffectCloud();
    }

    private void spawnMobEffectCloud() {
        if (this.leafkin instanceof LeafkinBlossom leafkinBlossom) {
            MobEffectInstance mobEffectInstance = leafkinBlossom.getVariant().getMobEffectInstance();
            if (mobEffectInstance != null) {
                AreaEffectCloud areaEffectCloud = new AreaEffectCloud(leafkinBlossom.level(), leafkinBlossom.getX(), leafkinBlossom.getY(), leafkinBlossom.getZ());
                areaEffectCloud.setRadius(3.0F);
                areaEffectCloud.setRadiusOnUse(-0.5F);
                areaEffectCloud.setWaitTime(10);
                areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float)areaEffectCloud.getDuration());
                areaEffectCloud.addEffect(new MobEffectInstance(mobEffectInstance));

                leafkinBlossom.level().addFreshEntity(areaEffectCloud);
            }
        }
    }
//    @Override
//    public boolean canUse() {
//        return this.leafkin instanceof LeafkinBlossom leafkinBlossom && leafkinBlossom.getVariant() != LeafkinPetalVariant.PINK;
//    }
}
