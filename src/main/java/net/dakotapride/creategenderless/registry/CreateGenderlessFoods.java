package net.dakotapride.creategenderless.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class CreateGenderlessFoods {
    public static FoodProperties GENDERLESS_PILL = new FoodProperties.Builder().nutrition(0).saturationMod(0.0F)
            .effect(() -> new MobEffectInstance(CreateGenderlessMobEffects.ENBY_POWER.get(), 6000, 0, false, false, true), 1.0F).build();
    public static FoodProperties ENLIGHTENED_PILL = new FoodProperties.Builder().nutrition(0).saturationMod(0.0F)
            .effect(() -> new MobEffectInstance(CreateGenderlessMobEffects.ENBY_POWER.get(), 6000, 1, false, false, true), 1.0F).build();
    public static FoodProperties VOID_PILL = new FoodProperties.Builder().nutrition(0).saturationMod(0.0F)
            .effect(() -> new MobEffectInstance(CreateGenderlessMobEffects.UNIFIED.get(), 6000, 0, false, false, true), 1.0F).build();
    public static FoodProperties MALT_BALLS = new FoodProperties.Builder().nutrition(8).saturationMod(1.5F)
            .effect(() -> new MobEffectInstance(CreateGenderlessMobEffects.ENBY_POWER.get(), 6000, 0, false, false, true), 1.0F).build();

    public static void transRights() {}
}
