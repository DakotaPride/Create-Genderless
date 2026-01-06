package net.dakotapride.creategenderless.advancement;

import com.google.gson.JsonObject;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ShellrockCriteriaTrigger extends SimpleCriterionTrigger<ShellrockCriteriaTrigger.TriggerInstance> {
    private static final ResourceLocation ID = CreateGenderlessMod.asResource("shellrock_count");

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
            super(ShellrockCriteriaTrigger.ID, player);
        }

        public static TriggerInstance simple() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}