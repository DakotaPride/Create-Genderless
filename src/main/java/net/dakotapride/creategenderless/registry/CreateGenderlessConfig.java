package net.dakotapride.creategenderless.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class CreateGenderlessConfig {
    public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ForgeConfigSpec.ConfigValue<Double> POGO_BASE_MULTIPLIER;
    public static final ForgeConfigSpec.ConfigValue<Double> POGO_SHIFTING_MULTIPLIER;
    public static final ForgeConfigSpec.ConfigValue<Double> POGO_FALL_DAMAGE_MULTIPLIER;

    public static final ForgeConfigSpec.ConfigValue<Boolean> UNIFIED_CANCEL_ATTACKS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> UNIFIED_CANCEL_DAMAGE;

    static {
        SERVER_BUILDER.push("Pogo Mechanics");
        POGO_BASE_MULTIPLIER = SERVER_BUILDER.comment("A multiplicative value applied to the delta movement in the y-axis.").defineInRange("Pogo Base Multiplier", 0.75F, 0, Integer.MAX_VALUE);
        POGO_SHIFTING_MULTIPLIER = SERVER_BUILDER.comment("A multiplicative value applied to the delta movement in the y-axis while shifting.").defineInRange("Pogo Shifting Multiplier", 0.25F, 0, Integer.MAX_VALUE);
        POGO_FALL_DAMAGE_MULTIPLIER = SERVER_BUILDER.comment("A multiplicative value applied to the fall damage value while in a state able to pogo certain terrain.").defineInRange("Pogo Fall Damage Multiplier", 0.25F, 0, Integer.MAX_VALUE);
        SERVER_BUILDER.pop();
        SERVER_BUILDER.push("Unified Effect");
        UNIFIED_CANCEL_ATTACKS = SERVER_BUILDER.comment("Cancel attacks from the player to other living creatures/entities.").define("Cancel Player Attacks", true);
        UNIFIED_CANCEL_DAMAGE = SERVER_BUILDER.comment("Cancel attacks to the player from other damage sources.").define("Cancel Attacks To Player", true);
        SERVER_BUILDER.pop();
        SERVER_SPEC = SERVER_BUILDER.build();
    }
}
