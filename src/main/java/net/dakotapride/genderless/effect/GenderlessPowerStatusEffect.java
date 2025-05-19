package net.dakotapride.genderless.effect;

import dev.mayaqq.estrogen.registry.EstrogenEffects;
import net.dakotapride.genderless.init.GenderlessStatusEffects;
import net.mifort.testosterone.effects.testosteroneModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.fml.ModList;

import static net.dakotapride.genderless.CreateGenderlessMod.isEstrogenLoaded;
import static net.dakotapride.genderless.CreateGenderlessMod.isTestosteroneLoaded;

public class GenderlessPowerStatusEffect extends MobEffect {
    public GenderlessPowerStatusEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x7573B6);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(entity, pAttributeMap, pAmplifier);
        entity.setInvulnerable(true);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(entity, pAttributeMap, pAmplifier);
        entity.setInvulnerable(false);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if (!entity.hasEffect(GenderlessStatusEffects.ERROR.get())) {
            if (isEstrogenLoaded())
                if (entity.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get()))
                    entity.removeEffect(EstrogenEffects.ESTROGEN_EFFECT.get());
            if (isTestosteroneLoaded())
                if (entity.hasEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get()))
                    entity.removeEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get());
            if (entity.hasEffect(GenderlessStatusEffects.GENDERFLUIDITY.get()))
                entity.removeEffect(GenderlessStatusEffects.GENDERFLUIDITY.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
