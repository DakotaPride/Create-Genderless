package net.dakotapride.creategenderless.entity.client.leafkin.blossom;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Leafkin;
import net.dakotapride.creategenderless.entity.LeafkinBlossom;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LeafkinBlossomRenderer extends MobRenderer<LeafkinBlossom, LeafkinBlossomModel<LeafkinBlossom>> {
    public LeafkinBlossomRenderer(EntityRendererProvider.Context context) {
        super(context, new LeafkinBlossomModel<>(context.bakeLayer(LeafkinBlossomModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(LeafkinBlossom leafkinBlossom) {
        return CreateGenderlessMod.asResource("textures/entity/leafkin/blossom/" + leafkinBlossom.getVariant().varName() + ".png");
    }

    @Override
    public void render(LeafkinBlossom entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
            poseStack.scale(0.65F, 0.65F, 0.65F);
        else poseStack.scale(1.0F, 1.0F, 1.0F);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
