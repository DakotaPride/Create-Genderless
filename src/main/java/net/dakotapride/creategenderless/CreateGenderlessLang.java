package net.dakotapride.creategenderless;

import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class CreateGenderlessLang extends Lang {

    public static MutableComponent translateDirect(String key, Object... args) {
        Object[] args1 = LangBuilder.resolveBuilders(args);
        return Component.translatable(CreateGenderlessMod.MOD_ID + "." + key, args1);
    }

    public static LangBuilder builder() {
        return new LangBuilder(CreateGenderlessMod.MOD_ID);
    }

    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }

    private static void addBlankSpace(List<Component> tooltip) {
        tooltip.add(Component.literal(""));
    }

}