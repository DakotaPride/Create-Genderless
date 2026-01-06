package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreateGenderlessPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, CreateGenderlessMod.ID);
    public static final RegistryObject<Potion> ENBY_POWER_POTION = POTIONS.register("enby_power", () -> new Potion("enby_power",
                    new MobEffectInstance(CreateGenderlessMobEffects.ENBY_POWER.get(), 12000)));

    public static void transRights(IEventBus bus) {
        POTIONS.register(bus);
    }
}
