package net.dakotapride.genderless.init;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.dakotapride.genderless.CreateGenderlessMod;

public class GenderlessPartialModels {

    public static final PartialModel BRA_OF_HOLDING = block("bra_of_holding");
    public static final PartialModel GENDERSLIME = block("genderslime");

    private static PartialModel block(String path) {
        return PartialModel.of(CreateGenderlessMod.asResource("block/" + path));
    }

    public static void register() {}
}
