package net.dakotapride.creategenderless.registry;

import com.simibubi.create.foundation.block.connected.*;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.client.resources.model.BakedModel;
import org.jetbrains.annotations.NotNull;

public class CreateGenderlessSpriteShifts {
    public static final CTSpriteShiftEntry SHELLROCK_CUT_CAP = omni("cut_shellrock_cap");

    public static final CTSpriteShiftEntry SHELLROCK_CUT_LAYERED = horizontal("layered_cut_shellrock");
    public static final CTModelProvider LAYERED_SHELLROCK_PROVIDER = new CTModelProvider(new HorizontalCTBehaviour(SHELLROCK_CUT_LAYERED, SHELLROCK_CUT_CAP));

    public static final CTSpriteShiftEntry SHELLROCK_PILLAR_LAYERED = rect("cut_shellrock_pillar");
    public static final CTModelProvider SHELLROCK_PILLAR_PROVIDER = new CTModelProvider(new RotatedPillarCTBehaviour(SHELLROCK_PILLAR_LAYERED, SHELLROCK_CUT_CAP));


    private static CTSpriteShiftEntry omni(String name) {
        return CTSpriteShifter.getCT(AllCTTypes.OMNIDIRECTIONAL, CreateGenderlessMod.asResource("block/" + name), CreateGenderlessMod.asResource("block/" + name + "_connected"));
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return CTSpriteShifter.getCT(AllCTTypes.HORIZONTAL_KRYPPERS, CreateGenderlessMod.asResource("block/" + name), CreateGenderlessMod.asResource("block/" + name + "_connected"));
    }

    private static CTSpriteShiftEntry rect(String name) {
        return CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, CreateGenderlessMod.asResource("block/" + name), CreateGenderlessMod.asResource("block/" + name + "_connected"));
    }


    public record CTModelProvider(ConnectedTextureBehaviour behavior) implements NonNullFunction<BakedModel, BakedModel> {
        @Override
        public @NotNull BakedModel apply(@NotNull BakedModel bakedModel) {
            return new CTModel(bakedModel, behavior);
        }
    }
}
