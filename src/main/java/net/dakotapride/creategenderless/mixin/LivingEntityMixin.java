package net.dakotapride.creategenderless.mixin;

import net.dakotapride.creategenderless.registry.CreateGenderlessConfig;
import net.dakotapride.creategenderless.registry.CreateGenderlessMobEffects;
import net.dakotapride.creategenderless.registry.CreateGenderlessParticleTypes;
import net.dakotapride.creategenderless.registry.CreateGenderlessTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    LivingEntity livingEntity = (LivingEntity) (Object) this;
    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "canBeSeenAsEnemy", at = @At("HEAD"), cancellable = true)
    private void canBeSeenAsEnemy(CallbackInfoReturnable<Boolean> cir) {
        // !livingEntity.hasEffect(CreateGenderlessMobEffects.UNIFIED.get()))
        if (livingEntity.hasEffect(CreateGenderlessMobEffects.UNIFIED.get()) && CreateGenderlessConfig.UNIFIED_CANCEL_ATTACKS.get())
            cir.setReturnValue(false);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void die(DamageSource source, CallbackInfo ci) {
        if (!CreateGenderlessTags.EntityTypeTags.NONASCENDABLE_CREATURES.matches(livingEntity)) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(CreateGenderlessParticleTypes.LUMARFLIES_SMALL.get(), this.getRandomX(1.0D),
                    this.getRandomY() + 1.5F, this.getRandomZ(1.0D), d0, d1, d2);
            this.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.0F);
        }

        if (livingEntity instanceof WitherBoss) {
            for (int i = 0; i < 3; i++) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(CreateGenderlessParticleTypes.LUMARFLIES_SMALL.get(), this.getRandomX(1.0D),
                        this.getRandomY() + 1.5F, this.getRandomZ(1.0D), d0, d1, d2);
            }
            this.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.0F);
        }
    }
}
