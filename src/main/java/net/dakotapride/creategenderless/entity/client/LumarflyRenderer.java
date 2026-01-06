package net.dakotapride.creategenderless.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Lumarfly;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LumarflyRenderer extends MobRenderer<Lumarfly, LumarflyModel<Lumarfly>> {
    public LumarflyRenderer(EntityRendererProvider.Context context) {
        super(context, new LumarflyModel<>(context.bakeLayer(LumarflyModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(Lumarfly lumarfly) {
        return CreateGenderlessMod.asResource("textures/entity/lumarfly_low_res.png");
    }

    @Override
    public void render(Lumarfly entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
            poseStack.scale(0.65F, 0.65F, 0.65F);
        else poseStack.scale(1.0F, 1.0F, 1.0F);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
