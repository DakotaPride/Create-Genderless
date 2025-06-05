package net.dakotapride.genderless.init;

import com.jozufozu.flywheel.core.PartialModel;
import net.dakotapride.genderless.CreateGenderlessMod;

public class GenderlessPartialModels {

    public static final PartialModel BRA_OF_HOLDING = block("bra_of_holding");
    public static final PartialModel GENDERSLIME = block("genderslime");

    private static PartialModel block(String path) {
        return new PartialModel(CreateGenderlessMod.asResource("block/" + path));
    }

    public static void register() {}
}
