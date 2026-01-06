package net.dakotapride.creategenderless.mixin;

import net.minecraft.client.resources.SplashManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SplashManager.class)
public class SplashMixin {
    @Unique
    private static final List<String> nullsterogenSplashes = List.of(
            "Now with nullsterogen!",
            "SHAW",
            "HEGALE",
            "ADINO",
            "GIT GUD",
            "Simibubi is a mod by Create",
            "Daddy Grimm Approves",
            "Wimdy",
            "No Cost Too Great",
            "No Mind To Think",
            "No Will To Break",
            "No Voice To Cry Suffering",
            "Egg",
            "You Have Ah'd 9999 Times",
            "Grass Randomiser",
            "L3mm(y) Wants Your Relics!",
            "Hungry...",
            "Save The Lumarflies!",
            "Pierogi!",
            "Low%",
            "Ass Jim",
            "Open a Bank Account With Me!",
            "...then get scammed",
            "Daughter of Minenest",
            "Pharcraft's Haunting"
    );

    @Shadow
    @Final
    private List<String> splashes;

    @Inject(method = "apply(Ljava/util/List;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "TAIL"))
    protected void apply(List<String> list, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        splashes.addAll(nullsterogenSplashes);
    }
}