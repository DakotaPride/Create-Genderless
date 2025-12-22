package net.dakotapride.genderless.mixin;

import dev.mayaqq.estrogen.content.EstrogenAttributes;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import dev.mayaqq.estrogen.content.items.GenderChangePotionItem;
import dev.mayaqq.estrogen.content.items.GenderChangePotionItem.Companion;
import net.mifort.testosterone.effects.testosteroneModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Companion.class, remap = false)
public class GenderChangePotionItemMixin {

    @Inject(method = "changeGender (Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private void changeGender(Level level, LivingEntity livingEntity, CallbackInfo ci) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            AttributeInstance showBoobs = player.getAttribute(EstrogenAttributes.INSTANCE.getShowBoobs());
            AttributeInstance startTime = player.getAttribute(EstrogenAttributes.INSTANCE.getBoobGrowingStartTime());
            AttributeInstance initialSize = player.getAttribute(EstrogenAttributes.INSTANCE.getBoobInitialSize());
            if (showBoobs != null && startTime != null && initialSize != null) {
                if (showBoobs.getBaseValue() > (double)0.0F) {
                    player.removeEffect(EstrogenEffects.getEstrogen());
                    player.addEffect(new MobEffectInstance(testosteroneModEffects.TESTOSTERONE_EFFECT.get(), 600, 0, true, false, true));
                } else {
                    if (startTime.getBaseValue() < (double)0.0F) {
                        player.removeEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get());
                        player.addEffect(new MobEffectInstance(EstrogenEffects.getEstrogen(), 600, 0, true, false, true));
                    }
                }
            }
        }
    }

}
