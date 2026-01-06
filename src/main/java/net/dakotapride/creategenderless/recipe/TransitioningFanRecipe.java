package net.dakotapride.creategenderless.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.dakotapride.creategenderless.registry.CreateGenderlessRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TransitioningFanRecipe extends ProcessingRecipe<TransitioningFanRecipe.TransitioningWrapper> {

    public TransitioningFanRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(CreateGenderlessRecipeTypes.TRANSITIONING, params);
    }

    @Override
    public boolean matches(TransitioningWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0)
                .test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }

    public static class TransitioningWrapper extends RecipeWrapper {
        public TransitioningWrapper() {
            super(new ItemStackHandler(1));
        }
    }

}