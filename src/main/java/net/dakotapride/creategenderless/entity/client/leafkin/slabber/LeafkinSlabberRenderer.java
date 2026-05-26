package net.dakotapride.creategenderless.entity.client.leafkin.slabber;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Leafkin;
import net.dakotapride.creategenderless.entity.LeafkinSlabber;
import net.dakotapride.creategenderless.entity.client.leafkin.LeafkinModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LeafkinSlabberRenderer extends MobRenderer<LeafkinSlabber, LeafkinSlabberModel<LeafkinSlabber>> {
    public LeafkinSlabberRenderer(EntityRendererProvider.Context context) {
        super(context, new LeafkinSlabberModel<>(context.bakeLayer(LeafkinSlabberModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(LeafkinSlabber leafkinSlabber) {
        return CreateGenderlessMod.asResource("textures/entity/leafkin/slabber/" + leafkinSlabber.getVariant().varName() + ".png");
    }

    @Override
    public void render(LeafkinSlabber entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
            poseStack.scale(0.65F, 0.65F, 0.65F);
        else poseStack.scale(1.0F, 1.0F, 1.0F);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
