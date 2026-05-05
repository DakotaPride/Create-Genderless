package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.advancement.*;
import net.minecraft.advancements.CriteriaTriggers;

public class CreateGenderlessAdvancementCriteria {
    public static final LumarflyCaptureCriteriaTrigger LUMARFLY_CAPTURE = new LumarflyCaptureCriteriaTrigger();
    public static final EndCrystalPogoCriteriaTrigger END_CRYSTAL_POGO = new EndCrystalPogoCriteriaTrigger();
    public static final ShellrockCriteriaTrigger SHELLROCK = new ShellrockCriteriaTrigger();
    public static final SaveTheLumarfliesCriteriaTrigger SAVE_THE_LUMARFLIES = new SaveTheLumarfliesCriteriaTrigger();
    public static final LumarflyRepopulationCriteriaTrigger LUMARFLY_REPOPULATION = new LumarflyRepopulationCriteriaTrigger();
    public static final RosaryCriteriaTrigger ROSARY = new RosaryCriteriaTrigger();
    public static final BindingCriteriaTrigger BINDING = new BindingCriteriaTrigger();

    public static void transRights() {
        CriteriaTriggers.register(LUMARFLY_CAPTURE);
        CriteriaTriggers.register(END_CRYSTAL_POGO);
        CriteriaTriggers.register(SHELLROCK);
        CriteriaTriggers.register(SAVE_THE_LUMARFLIES);
        CriteriaTriggers.register(LUMARFLY_REPOPULATION);
        CriteriaTriggers.register(ROSARY);
        CriteriaTriggers.register(BINDING);
    }
}
