package net.dakotapride.creategenderless.entity.client.leafkin.slabber;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Leafkin;
import net.dakotapride.creategenderless.entity.LeafkinSlabber;
import net.dakotapride.creategenderless.entity.client.leafkin.LeafkinAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class LeafkinSlabberModel<T extends LeafkinSlabber> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CreateGenderlessMod.asResource("leafkin_slabber"), "main");
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart camo;

	public LeafkinSlabberModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.camo = this.body.getChild("camo");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 23.75F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition camo = body.addOrReplaceChild("camo", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 4.0F, 16.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LeafkinSlabber entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(LeafkinSlabberAnimations.LEAFKIN_SLABBER_WALK, limbSwing, limbSwingAmount, 1f, 1f);
		this.animate(entity.idleAnimationState, LeafkinSlabberAnimations.LEAFKIN_SLABBER_IDLE, ageInTicks, 1f);
		this.animate(entity.sleepingAnimationState, LeafkinSlabberAnimations.LEAFKIN_SLABBER_SLEEPING, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return main;
	}
}