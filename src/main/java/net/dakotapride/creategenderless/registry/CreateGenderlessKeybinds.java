package net.dakotapride.creategenderless.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum CreateGenderlessKeybinds {

    BIND("bind_crest", GLFW.GLFW_KEY_R, "Bind Crest"),
    LUNGE("lunge", GLFW.GLFW_MOUSE_BUTTON_RIGHT, "Lunge"),

    ;

    private KeyMapping keybind;
    private final String description;
    private final String translation;
    private final int key;
    private final boolean modifiable;

    CreateGenderlessKeybinds(int defaultKey) {
        this("", defaultKey, "");
    }

    CreateGenderlessKeybinds(String description, int defaultKey, String translation) {
        this.description = CreateGenderlessMod.MOD_ID + ".keyinfo." + description;
        this.key = defaultKey;
        this.modifiable = !description.isEmpty();
        this.translation = translation;
    }

    public static void provideLang(BiConsumer<String, String> consumer) {
        for (CreateGenderlessKeybinds key : values())
            if (key.modifiable)
                consumer.accept(key.description, key.translation);
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        for (CreateGenderlessKeybinds key : values()) {
            key.keybind = new KeyMapping(key.description, key.key, "creategenderless.keybindings.group.name");
            if (!key.modifiable)
                continue;

            event.register(key.keybind);
        }
    }

    public KeyMapping getKeybind() {
        return keybind;
    }

    public boolean isPressed() {
        if (!modifiable)
            return isKeyDown(key);
        return keybind.isDown();
    }

    public String getBoundKey() {
        return keybind.getTranslatedKeyMessage()
                .getString()
                .toUpperCase();
    }

    public boolean doesModifierAndCodeMatch(int code) {
        boolean codeMatches = code == keybind.getKey().getValue();

        boolean modifierMatches;
        KeyModifier modifier = keybind.getKeyModifier();
        if (modifier == KeyModifier.NONE) {
            modifierMatches = true;
        } else {
            modifierMatches = modifier.equals(KeyModifier.getActiveModifier());
        }

        return codeMatches && modifierMatches;
    }

    public static boolean isKeyDown(int key) {
        return InputConstants.isKeyDown(Minecraft.getInstance()
                .getWindow()
                .getWindow(), key);
    }
}
