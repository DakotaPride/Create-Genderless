package net.dakotapride.genderless.init;

import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.joml.Vector3f;

import java.util.function.Supplier;

import static net.dakotapride.genderless.CreateGenderlessMod.REGISTRATE;

public class GenderlessFluids {
    public static final FluidEntry<ForgeFlowingFluid.Flowing> BINARY_FLUID =
            REGISTRATE.standardFluid("binary_fluid",
                            SolidRenderedPlaceableFluidType.create(0xB9B7ED,
                                    () -> 1f / 32f * AllConfigs.client().chocolateTransparencyMultiplier.getF()))
                    .properties(b -> b
                            .viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p
                            .levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    //.defaultBucket()
                    .register();
    public static final FluidEntry<ForgeFlowingFluid.Flowing> NON_BINARY_FLUID =
            REGISTRATE.standardFluid("non_binary_fluid",
                            SolidRenderedPlaceableFluidType.create(0x6E5F8C,
                                    () -> 1f / 32f * AllConfigs.client().chocolateTransparencyMultiplier.getF()))
                    .properties(b -> b
                            .viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p
                            .levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    //.defaultBucket()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> ZERO_ONE_ZERO_ZERO_ZERO_ONE_ZERO_ONE =
            REGISTRATE.standardFluid("01000101",
                            SolidRenderedPlaceableFluidType.create(0x000000,
                                    () -> 1f / 32f * AllConfigs.client().chocolateTransparencyMultiplier.getF()))
                    .properties(b -> b
                            .viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p
                            .levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    //.defaultBucket()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VOID =
            REGISTRATE.standardFluid("void",
                            SolidRenderedPlaceableFluidType.create(0x050012,
                                    () -> 1f / 32f * AllConfigs.client().chocolateTransparencyMultiplier.getF()))
                    .properties(b -> b
                            .viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p
                            .levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    //.defaultBucket()
                    .register();

    // Load this class

    public static void register() {}

//    public static void registerFluidInteractions() {
//        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
//                BINARY_FLUID.get().getFluidType(),
//                fluidState -> {
//                    if (fluidState.isSource()) {
//                        return Blocks.OBSIDIAN.defaultBlockState();
//                    } else {
//                        return AllPaletteStoneTypes.SCORIA.getBaseBlock()
//                                .get()
//                                .defaultBlockState();
//                    }
//                }
//        ));
//    }
//
//    @Nullable
//    public static BlockState getLavaInteraction(FluidState fluidState) {
//        Fluid fluid = fluidState.getType();
//        if (fluid.isSame(BINARY_FLUID.get()))
//            return AllPaletteStoneTypes.SCORIA.getBaseBlock()
//                    .get()
//                    .defaultBlockState();
//        return null;
//    }

    private static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        /*
         * Removing alpha from tint prevents optifine from forcibly applying biome
         * colors to modded fluids (this workaround only works for fluids in the solid
         * render layer)
         */
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
