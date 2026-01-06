package net.dakotapride.creategenderless.compat.JEI;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.mayaqq.estrogen.content.EstrogenFluids;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.dakotapride.creategenderless.CreateGenderlessLang;
import net.dakotapride.creategenderless.registry.CreateGenderlessFluids;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TransitioningFanCategory<T extends ProcessingRecipe<?>> extends ProcessingViaFanCategory.MultiOutput<T> {
    public TransitioningFanCategory(Info<T> info) {
        super(info);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("creategenderless.recipe.fan_transitioning");
    }

    public static Supplier<ItemStack> getFan(String name) {
        return () -> AllBlocks.ENCASED_FAN.asStack()
                .setHoverName(CreateGenderlessLang.translateDirect("recipe." + name + ".fan").withStyle(style -> style.withItalic(false)));
    }

    @Override
    protected void renderAttachedBlock(@NotNull GuiGraphics graphics) {
        ResourceLocation genderfluidID = ResourceLocation.fromNamespaceAndPath("estrogen", "gender_fluid");
        Fluid genderfluidType = ForgeRegistries.FLUIDS.getValue(genderfluidID);

        GuiGameElement.of(genderfluidType)
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);
    }
}