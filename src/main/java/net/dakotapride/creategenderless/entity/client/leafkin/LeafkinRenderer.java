package net.dakotapride.creategenderless.entity.client.leafkin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Leafkin;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LeafkinRenderer extends MobRenderer<Leafkin, LeafkinModel<Leafkin>> {
    public LeafkinRenderer(EntityRendererProvider.Context context) {
        super(context, new LeafkinModel<>(context.bakeLayer(LeafkinModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(Leafkin leafkin) {
        return CreateGenderlessMod.asResource("textures/entity/leafkin/" + leafkin.getVariant().varName() + ".png");
    }

    @Override
    public void render(Leafkin entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
            poseStack.scale(0.65F, 0.65F, 0.65F);
        else poseStack.scale(1.0F, 1.0F, 1.0F);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
