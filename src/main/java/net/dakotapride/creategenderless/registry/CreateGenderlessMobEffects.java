package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.effect.EnbyPowerMobEffect;
import net.dakotapride.creategenderless.effect.UnifiedMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CreateGenderlessMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CreateGenderlessMod.ID);

    public static final RegistryObject<MobEffect> ENBY_POWER = EFFECTS.register("enby_power", EnbyPowerMobEffect::new);
    public static final RegistryObject<MobEffect> UNIFIED = EFFECTS.register("unified", UnifiedMobEffect::new);

    public static void transRights(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
