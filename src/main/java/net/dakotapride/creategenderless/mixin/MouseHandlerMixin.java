package net.dakotapride.creategenderless.mixin;

import net.dakotapride.creategenderless.registry.CreateGenderlessMobEffects;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    private void onPress(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(CreateGenderlessMobEffects.BINDING.get())) {
            KeyMapping.releaseAll();
            callbackInfo.cancel();
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(CreateGenderlessMobEffects.BINDING.get())) {
            KeyMapping.releaseAll();
            callbackInfo.cancel();
        }
    }
}