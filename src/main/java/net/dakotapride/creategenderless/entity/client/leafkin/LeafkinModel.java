package net.dakotapride.creategenderless.entity.client.leafkin;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Leafkin;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class LeafkinModel<T extends Leafkin> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CreateGenderlessMod.asResource("leafkin"), "main");

	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart l_leg;
	private final ModelPart r_leg;
	private final ModelPart waist;
	private final ModelPart nose;
	private final ModelPart camo;

	public LeafkinModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.l_leg = this.body.getChild("l_leg");
		this.r_leg = this.body.getChild("r_leg");
		this.waist = this.body.getChild("waist");
		this.nose = this.waist.getChild("nose");
		this.camo = this.waist.getChild("camo");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, -2.5F));

		PartDefinition l_leg = body.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(40, 10).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.0F, 3.5F));

		PartDefinition r_leg = body.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(40, 10).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, -3.0F, 3.5F));

		PartDefinition waist = body.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -24.0F, -5.0F, 10.0F, 24.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0F, 3.5F));

		PartDefinition nose = waist.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, -5.0F));

		PartDefinition camo = waist.addOrReplaceChild("camo", CubeListBuilder.create().texOffs(0, 34).addBox(-5.0F, -24.0F, -5.0F, 10.0F, 24.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Leafkin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(LeafkinAnimations.LEAFKIN_WALK, limbSwing, limbSwingAmount, 1f, 1f);
		this.animate(entity.idleAnimationState, LeafkinAnimations.LEAFKIN_IDLE, ageInTicks, 1f);
		this.animate(entity.spotPreyAnimationState, LeafkinAnimations.LEAFKIN_SPOT_PREY, ageInTicks, 1f);
		this.animate(entity.returnToWaitAnimationState, LeafkinAnimations.LEAFKIN_RETURN_TO_WAIT_POS, ageInTicks, 1f);
		this.animate(entity.attackAnimationState, LeafkinAnimations.LEAFKIN_ATTACK, ageInTicks, 1f);
		this.animate(entity.sheddingAnimationState, LeafkinAnimations.LEAFKIN_SHEDDING, ageInTicks, 1f);
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