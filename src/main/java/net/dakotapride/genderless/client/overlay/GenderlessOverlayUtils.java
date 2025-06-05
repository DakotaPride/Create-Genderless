package net.dakotapride.genderless.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dakotapride.genderless.CreateGenderlessMod;
import net.dakotapride.genderless.init.GenderlessStatusEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GenderlessOverlayUtils {

    private static final ResourceLocation OVERLAY_TEXTURE0 = CreateGenderlessMod.asResource("textures/overlay/overlay.png");
    private static final ResourceLocation OVERLAY_TEXTURE1 = CreateGenderlessMod.asResource("textures/overlay/overlay_compact.png");

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        LocalPlayer player = Minecraft.getInstance().player;

        int x = 0;
        int y = 0;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        if (player != null) {
            if (player.hasEffect(GenderlessStatusEffects.GENDERFLUIDITY.get())) {
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(
                        (float) 134 / 255,
                        (float) 8 / 255,
                        (float) 148 / 255,
                        0.5F);
                guiGraphics.blit(OVERLAY_TEXTURE1, x, y, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
            }

            if (player.hasEffect(GenderlessStatusEffects.GENDERLESS_POWER.get())) {
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(
                        (float) 50 / 255,
                        (float) 75 / 255,
                        (float) 118 / 255,
                        0.7F);
                guiGraphics.blit(OVERLAY_TEXTURE0, x, y, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
            }
        }
    };
}
