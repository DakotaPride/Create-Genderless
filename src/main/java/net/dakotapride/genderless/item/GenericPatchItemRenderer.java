package net.dakotapride.genderless.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class GenericPatchItemRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                          SlotContext slotContext,
                                                                          PoseStack matrixStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer,
                                                                          int light,
                                                                          float limbSwing,
                                                                          float limbSwingAmount,
                                                                          float partialTicks,
                                                                          float ageInTicks,
                                                                          float netHeadYaw,
                                                                          float headPitch) {
        if (renderLayerParent.getModel() instanceof PlayerModel<? extends LivingEntity> playerModel) {
            if (!(Boolean) EstrogenConfig.client().estrogenPatchRender.get()) {
                return;
            }

            LocalPlayer player = Minecraft.getInstance().player;
            matrixStack.pushPose();
            ModelPart leg = playerModel.leftLeg;
            if (player.isCrouching() && !playerModel.riding && !player.isSwimming()) {
                matrixStack.translate(0.0F, 0.0F, 0.25F);
            }

            matrixStack.translate(0.125F, 0.75F, 0.0F);
            matrixStack.mulPose(Axis.ZP.rotation(leg.zRot));
            matrixStack.mulPose(Axis.YP.rotation(leg.yRot));
            matrixStack.mulPose(Axis.XP.rotation(leg.xRot));
            matrixStack.translate(0.0F, 0.75F, 0.0F);
            matrixStack.mulPose(Axis.YN.rotation(89.6F));
            matrixStack.translate(0.0F, -0.6F, -0.135F);
            matrixStack.scale(0.3F, 0.3F, 0.3F);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, light, matrixStack, renderTypeBuffer, player.level(), 0);
            matrixStack.popPose();
        }
    }
}