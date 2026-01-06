package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.Locale;

public class CreateGenderlessTags {
    public enum BlockTags {
        POGOABLE(),
        FAN_TRANSITIONING_PROCESSING_TAG("fan_processing_catalysts/transitioning")


        ;

        private final TagKey<Block> blockTagKey;

        BlockTags() {
            this.blockTagKey = TagKey.create(Registries.BLOCK, CreateGenderlessMod.asResource(name().toLowerCase(Locale.ROOT)));
        }

        BlockTags(String location) {
            this.blockTagKey = TagKey.create(Registries.BLOCK, CreateGenderlessMod.asResource(location));
        }

        public TagKey<Block> getTag() {
            return blockTagKey;
        }

        public boolean matches(BlockState state) {
            return state.is(blockTagKey);
        }
    }

    public enum FluidTags {
        FAN_TRANSITIONING_PROCESSING_TAG("fan_processing_catalysts/transitioning")


        ;

        private final TagKey<Fluid> fluidTagKey;

        FluidTags() {
            this.fluidTagKey = TagKey.create(Registries.FLUID, CreateGenderlessMod.asResource(name().toLowerCase(Locale.ROOT)));
        }

        FluidTags(String location) {
            this.fluidTagKey = TagKey.create(Registries.FLUID, CreateGenderlessMod.asResource(location));
        }

        public TagKey<Fluid> getTag() {
            return fluidTagKey;
        }

        public boolean matches(FluidState state) {
            return state.is(fluidTagKey);
        }
    }

    public enum EntityTypeTags {
        NONASCENDABLE_CREATURES(),


        ;

        private final TagKey<EntityType<?>> entityTypeTagKey;

        EntityTypeTags() {
            this.entityTypeTagKey = TagKey.create(Registries.ENTITY_TYPE, CreateGenderlessMod.asResource(name().toLowerCase(Locale.ROOT)));
        }

        EntityTypeTags(String location) {
            this.entityTypeTagKey = TagKey.create(Registries.ENTITY_TYPE, CreateGenderlessMod.asResource(location));
        }



        public boolean matches(EntityType<?> type) {
            return type.is(entityTypeTagKey);
        }

        public boolean matches(Entity entity) {
            return matches(entity.getType());
        }
    }
}
