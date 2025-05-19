package net.dakotapride.genderless.init;

import net.dakotapride.genderless.CreateGenderlessMod;
import net.dakotapride.genderless.effect.ErrorStatusEffect;
import net.dakotapride.genderless.effect.EuphoriaStatusEffect;
import net.dakotapride.genderless.effect.GenderfluidityPowerStatusEffect;
import net.dakotapride.genderless.effect.GenderlessPowerStatusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GenderlessStatusEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CreateGenderlessMod.MOD_ID);

    public static final RegistryObject<MobEffect> GENDERLESS_POWER = EFFECTS.register("genderless_power", () -> new GenderlessPowerStatusEffect()
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "447a36dd-1eec-460e-b042-f863300af6fc", -10000000, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> GENDERFLUIDITY = EFFECTS.register("genderfluidity", GenderfluidityPowerStatusEffect::new);
    public static final RegistryObject<MobEffect> EUPHORIA = EFFECTS.register("euphoria", () -> new EuphoriaStatusEffect()
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "76da8ce0-cee8-4c91-85f7-361b57ea6d12", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> ERROR = EFFECTS.register("error", ErrorStatusEffect::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}