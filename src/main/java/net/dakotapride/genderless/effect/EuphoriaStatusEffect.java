package net.dakotapride.genderless.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Objects;

public class EuphoriaStatusEffect extends MobEffect {
    public EuphoriaStatusEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB9B7ED);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
            livingEntity.heal(1.0F);
        }

        final List<Monster> list = livingEntity.level().getEntitiesOfClass(Monster.class,
                livingEntity.getBoundingBox().inflate(4F * (amplifier + 1)), Objects::nonNull);
        list.forEach(monster -> {
            if (livingEntity instanceof Player) {
                monster.setTarget(null);
                monster.setAggressive(false);
            }
        });
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        int k = 60 >> pAmplifier;
        if (k > 0) {
            return pDuration % k == 0;
        } else {
            return true;
        }
    }
}
