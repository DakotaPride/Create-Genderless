package net.dakotapride.creategenderless.config;

import net.createmod.catnip.config.ConfigBase;

public class PogoMechanicsConfig extends ConfigBase {
    public ConfigBase.ConfigFloat POGO_BASE_MULTIPLIER = f(0.75F, 0.0F, Integer.MAX_VALUE, "pogoBaseMultiplier",
            "A multiplicative value applied to the delta movement in the y-axis.");
    public ConfigBase.ConfigFloat POGO_SHIFTING_MULTIPLIER = f(0.25F, 0.0F, Integer.MAX_VALUE, "pogoShiftingMultiplier",
            "A multiplicative value applied to the delta movement in the y-axis while shifting.");
    public ConfigBase.ConfigFloat POGO_FALL_DAMAGE_MULTIPLIER = f(0.25F, 0.0F, Integer.MAX_VALUE, "pogoFallDamageMultiplier",
            "A multiplicative value applied to the fall damage value while in a state able to pogo certain terrain.");

    @Override
    public String getName() {
        return "pogo_mechanics";
    }
}
