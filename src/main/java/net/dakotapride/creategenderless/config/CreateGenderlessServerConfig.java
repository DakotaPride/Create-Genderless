package net.dakotapride.creategenderless.config;

import net.createmod.catnip.config.ConfigBase;

public class CreateGenderlessServerConfig extends ConfigBase  {
    public final PogoMechanicsConfig pogoMechanicsConfig = nested(0, PogoMechanicsConfig::new,
            "Control how the pogo mechanics function while inflicted by the Enby Power status effect");
    public final UnifiedMobEffectConfig unifedMobEffectConfig = nested(0, UnifiedMobEffectConfig::new,
            "Control how the Unified status effect functionality behaves");

    @Override
    public String getName() {
        return "creategenderless";
    }
}
