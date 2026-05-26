package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CreateGenderlessMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CreateGenderlessMod.MOD_ID);

    public static final RegistryObject<MobEffect> ENBY_POWER = EFFECTS.register("enby_power", EnbyPowerMobEffect::new);
    public static final RegistryObject<MobEffect> UNIFIED = EFFECTS.register("unified", UnifiedMobEffect::new);
    public static final RegistryObject<MobEffect> BINDING = EFFECTS.register("binding", BindingMobEffect::new);
    public static final RegistryObject<MobEffect> FIERCE = EFFECTS.register("fierce", FierceMobEffect::new);
    public static final RegistryObject<MobEffect> CROAKED = EFFECTS.register("croaked", () -> new CroakedMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "8e8456c9-db97-4d7f-9471-4eab52fd6aa9", -0.025D, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> SWIFT = EFFECTS.register("swift", SwiftMobEffect::new);
    public static final RegistryObject<MobEffect> ENSNARED = EFFECTS.register("ensnared", EnsnaredMobEffect::new);

    public static void transRights(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
