package net.dakotapride.creategenderless.config;

import net.createmod.catnip.config.ConfigBase;

public class UnifiedMobEffectConfig extends ConfigBase {
    public ConfigBool UNIFIED_CANCEL_ATTACKS = b(true, "cancelPlayerAttacks",
            "Cancel attacks from the player to other living creatures/entities.");
    public ConfigBool UNIFIED_CANCEL_DAMAGE = b(true, "cancelAttacksToPlayer",
            "Cancel attacks to the player from other damage sources.");

    @Override
    public String getName() {
        return "unified_mob_effect";
    }
}
