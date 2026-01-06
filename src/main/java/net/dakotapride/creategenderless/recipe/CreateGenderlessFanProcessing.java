package net.dakotapride.creategenderless.recipe;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.createmod.catnip.theme.Color;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.registry.CreateGenderlessRecipeTypes;
import net.dakotapride.creategenderless.registry.CreateGenderlessTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

public class CreateGenderlessFanProcessing {
    public static final TransitioningType TRANSITIONING = register("transitioning", new TransitioningType());

    static {
        Object2ReferenceOpenHashMap<String, FanProcessingType> map = new Object2ReferenceOpenHashMap<>();
        map.put("TRANSITIONING", TRANSITIONING);
        map.trim();
    }

    private static <T extends FanProcessingType> T register(String id, T type) {
        return Registry.register(CreateBuiltInRegistries.FAN_PROCESSING_TYPE, CreateGenderlessMod.asResource(id), type);
    }

    public static void transRights() {}

    public static class TransitioningType implements FanProcessingType {
        private static final TransitioningFanRecipe.TransitioningWrapper TRANSITIONING_WRAPPER = new TransitioningFanRecipe.TransitioningWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (CreateGenderlessTags.FluidTags.FAN_TRANSITIONING_PROCESSING_TAG.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            return CreateGenderlessTags.BlockTags.FAN_TRANSITIONING_PROCESSING_TAG.matches(blockState);
        }

        @Override
        public int getPriority() {
            return 2000;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            TRANSITIONING_WRAPPER.setItem(0, stack);
            Optional<TransitioningFanRecipe> recipe = CreateGenderlessRecipeTypes.TRANSITIONING.find(TRANSITIONING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            TRANSITIONING_WRAPPER.setItem(0, stack);
            Optional<TransitioningFanRecipe> recipe = CreateGenderlessRecipeTypes.TRANSITIONING.find(TRANSITIONING_WRAPPER, level);
            return recipe.map(transitioningFanRecipe -> RecipeApplier.applyRecipeOn(level, stack, transitioningFanRecipe, true)).orElse(null);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color = new Color(0xC1FFF8).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0xC1FFF8, 0xFDC1FF, random.nextFloat()));
            particleAccess.setAlpha(1f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide) {
                return;
            }

            if (entity.isOnFire()) {
                entity.extinguishFire();
            }
        }
    }
}