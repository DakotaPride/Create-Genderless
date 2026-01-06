package net.dakotapride.creategenderless.registry;

import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.createmod.catnip.theme.Color;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class CreateGenderlessFluids {
    private static ResourceLocation createLocation(String fluid, boolean isFlowing) {
        String getMotion;

        if (isFlowing) {
            getMotion = "_flow";
        } else {
            getMotion = "_still";
        }

        return CreateGenderlessMod.asResource("fluid/" + fluid + getMotion);
    }

    public static FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> standardFluid(String name,
                                                                                          FluidBuilder.FluidTypeFactory typeFactory) {
        return CreateGenderlessMod.registrate().fluid(name, createLocation(name, false), createLocation(name, true), typeFactory);
    }

    public static final FluidEntry<ForgeFlowingFluid.Flowing> NULLSTEROGEN =
            CreateGenderlessFluids.standardFluid("nullsterogen", SolidRenderedPlaceableFluidType.create(0x5C64A7,
                            () -> 1f / 8f))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket().build()
                    .register();
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENSED_VOID =
            CreateGenderlessFluids.standardFluid("condensed_void", SolidRenderedPlaceableFluidType.create(0x1D192D,
                            () -> 1f / 8f))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket().build()
                    .register();
    public static final FluidEntry<ForgeFlowingFluid.Flowing> DILUTED_SOUL =
            CreateGenderlessFluids.standardFluid("diluted_soul", SolidRenderedPlaceableFluidType.create(0x1D192D,
                            () -> 1f / 8f))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket().build()
                    .register();

    public static void transRights() {}

    public static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        public Vector3f fogColor;
        public Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        public SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                               ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        public int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        public Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        public float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
