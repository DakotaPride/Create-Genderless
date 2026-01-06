package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.advancement.EndCrystalPogoCriteriaTrigger;
import net.dakotapride.creategenderless.advancement.LumarflyCaptureCriteriaTrigger;
import net.dakotapride.creategenderless.advancement.SaveTheLumarfliesCriteriaTrigger;
import net.dakotapride.creategenderless.advancement.ShellrockCriteriaTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class CreateGenderlessAdvancementCriteria {
    public static final LumarflyCaptureCriteriaTrigger LUMARFLY_CAPTURE = new LumarflyCaptureCriteriaTrigger();
    public static final EndCrystalPogoCriteriaTrigger END_CRYSTAL_POGO = new EndCrystalPogoCriteriaTrigger();
    public static final ShellrockCriteriaTrigger SHELLROCK = new ShellrockCriteriaTrigger();
    public static final SaveTheLumarfliesCriteriaTrigger SAVE_THE_LUMARFLIES = new SaveTheLumarfliesCriteriaTrigger();

    public static void transRights() {
        CriteriaTriggers.register(LUMARFLY_CAPTURE);
        CriteriaTriggers.register(END_CRYSTAL_POGO);
        CriteriaTriggers.register(SHELLROCK);
        CriteriaTriggers.register(SAVE_THE_LUMARFLIES);
    }
}
