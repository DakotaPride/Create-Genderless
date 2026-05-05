package net.dakotapride.creategenderless.mixin;

import net.dakotapride.creategenderless.registry.CreateGenderlessMobEffects;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    public void keyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo callbackInfo) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(CreateGenderlessMobEffects.BINDING.get())) {
            KeyMapping.releaseAll();
            callbackInfo.cancel();
        }
    }
}