package net.dakotapride.genderless.init;

import dev.mayaqq.estrogen.registry.EstrogenEffects;
import net.dakotapride.genderless.CreateGenderlessMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GenderlessPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, CreateGenderlessMod.MOD_ID);
    public static final RegistryObject<Potion> GENDERLESS_POTION = POTIONS.register("genderless", () -> new Potion("genderless", new MobEffectInstance(GenderlessStatusEffects.GENDERLESS_POWER.get(), 12000)));

    public static void register(IEventBus bus) {
        POTIONS.register(bus);
    }

}
