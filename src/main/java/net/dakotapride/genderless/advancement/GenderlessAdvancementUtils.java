package net.dakotapride.genderless.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class GenderlessAdvancementUtils {
    public static AllGenderEffectsCriteria ALL_GENDER_EFFECTS = new AllGenderEffectsCriteria();

    public static void register() {
        CriteriaTriggers.register(ALL_GENDER_EFFECTS);
    }
}

