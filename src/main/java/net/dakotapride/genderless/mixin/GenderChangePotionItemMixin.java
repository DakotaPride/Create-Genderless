package net.dakotapride.genderless.mixin;

import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.items.GenderChangePotionItem;
import dev.mayaqq.estrogen.utils.Time;
import net.mifort.testosterone.effects.testosteroneModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GenderChangePotionItem.class, remap = false)
public class GenderChangePotionItemMixin extends Item {
    public GenderChangePotionItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "changeGender(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private static void changeGender(Level level, LivingEntity livingEntity, CallbackInfo ci) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            AttributeInstance showBoobs = player.getAttribute(EstrogenAttributes.SHOW_BOOBS.get());
            AttributeInstance startTime = player.getAttribute(EstrogenAttributes.BOOB_GROWING_START_TIME.get());
            AttributeInstance initialSize = player.getAttribute(EstrogenAttributes.BOOB_INITIAL_SIZE.get());
            if (showBoobs != null && startTime != null && initialSize != null) {
                if (showBoobs.getBaseValue() > (double)0.0F) {
                    player.removeEffect(EstrogenEffects.ESTROGEN_EFFECT.get());
                    player.addEffect(new MobEffectInstance(testosteroneModEffects.TESTOSTERONE_EFFECT.get(), 600, 0, true, false, true));
                } else {
                    if (startTime.getBaseValue() < (double)0.0F) {
                        player.removeEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get());
                        player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 600, 0, true, false, true));
                    }
                }
            }
        }
    }

}
