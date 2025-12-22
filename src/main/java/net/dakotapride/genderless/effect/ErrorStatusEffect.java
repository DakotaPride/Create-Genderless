package net.dakotapride.genderless.effect;

import dev.mayaqq.estrogen.content.EstrogenEffects;
import net.dakotapride.genderless.advancement.GenderlessAdvancementUtils;
import net.dakotapride.genderless.init.GenderlessStatusEffects;
import net.mifort.testosterone.effects.testosteroneModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

import static net.dakotapride.genderless.CreateGenderlessMod.isEstrogenLoaded;
import static net.dakotapride.genderless.CreateGenderlessMod.isTestosteroneLoaded;

public class ErrorStatusEffect extends MobEffect {
    public ErrorStatusEffect() {
        super(MobEffectCategory.NEUTRAL, 0x000000);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if (entity instanceof ServerPlayer player) {
            if (
                    // Cannot require *every* gender effect due to Testosterone removing the Girl Power effect (and for some reason it really doesn't like mixining into the class to prevent this, so RIP)
                    ((isEstrogenLoaded() && player.hasEffect(EstrogenEffects.getEstrogen()))
                    || (isTestosteroneLoaded() && player.hasEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get())))
                    && player.hasEffect(GenderlessStatusEffects.GENDERLESS_POWER.get())
                    && player.hasEffect(GenderlessStatusEffects.GENDERFLUIDITY.get())
            ) {
                GenderlessAdvancementUtils.ALL_GENDER_EFFECTS.trigger(player);
                player.removeEffect(GenderlessStatusEffects.ERROR.get());

                Minecraft minecraft = Minecraft.getInstance();

                Screenshot.grab(minecraft.gameDirectory, minecraft.getMainRenderTarget(), (component) -> {
                    minecraft.execute(() -> {
                        minecraft.gui.getChat().addMessage(component);
                    });
                });
            }

        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
