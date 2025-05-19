package net.dakotapride.genderless.advancement;

import com.google.gson.JsonObject;
import net.dakotapride.genderless.CreateGenderlessMod;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class AllGenderEffectsCriteria extends SimpleCriterionTrigger<AllGenderEffectsCriteria.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(CreateGenderlessMod.MOD_ID, "all_gender_effects_applied");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public void trigger(@NotNull ServerPlayer player) {
        this.trigger(player, TriggerInstance::test);
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate player, @NotNull DeserializationContext conditionsParser) {
        return new TriggerInstance(player);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate player) {
            super(AllGenderEffectsCriteria.ID, player);
        }

        public static TriggerInstance simple() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}

