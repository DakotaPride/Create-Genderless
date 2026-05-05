package net.dakotapride.creategenderless.effect;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;

public class BindingMobEffect extends MobEffect {
    public BindingMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xE7EAEC);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        Level level = entity.level();
        if (!level.isClientSide) {
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.BELL_RESONATE, SoundSource.AMBIENT, 1.0F, 2.0F);
        }

        super.addAttributeModifiers(entity, attributeMap, amplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.setDeltaMovement(0, 0, 0);
        entity.teleportTo(entity.getX(), entity.getY(), entity.getZ());
    }

    @Override
    public boolean isDurationEffectTick(int e, int f) {
        return true;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        entity.heal(amplifier);
    }
}
