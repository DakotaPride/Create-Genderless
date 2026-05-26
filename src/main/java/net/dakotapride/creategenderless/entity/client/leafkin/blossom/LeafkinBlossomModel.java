package net.dakotapride.creategenderless.entity.client.leafkin.blossom;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Leafkin;
import net.dakotapride.creategenderless.entity.LeafkinBlossom;
import net.dakotapride.creategenderless.entity.client.leafkin.LeafkinAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class LeafkinBlossomModel<T extends LeafkinBlossom> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CreateGenderlessMod.asResource("leafkin_blossom"), "main");

	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart bottom;
	private final ModelPart petals;
	private final ModelPart r;
	private final ModelPart l;
	private final ModelPart f;
	private final ModelPart b;
	private final ModelPart head;
	private final ModelPart r_antennae;
	private final ModelPart l_antennae;

	public LeafkinBlossomModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.bottom = this.body.getChild("bottom");
		this.petals = this.bottom.getChild("petals");
		this.r = this.petals.getChild("r");
		this.l = this.petals.getChild("l");
		this.f = this.petals.getChild("f");
		this.b = this.petals.getChild("b");
		this.head = this.body.getChild("head");
		this.r_antennae = this.head.getChild("r_antennae");
		this.l_antennae = this.head.getChild("l_antennae");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 21.8333F, 0.25F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 37).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, 3.1667F, -0.25F));

		PartDefinition petals = bottom.addOrReplaceChild("petals", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1958F, 0.0F));

		PartDefinition r = petals.addOrReplaceChild("r", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.1958F, 0.0F));

		PartDefinition cube_r1 = r.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 25).addBox(-3.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition l = petals.addOrReplaceChild("l", CubeListBuilder.create(), PartPose.offset(3.0F, 0.1958F, 0.0F));

		PartDefinition cube_r2 = l.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 25).mirror().addBox(0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition f = petals.addOrReplaceChild("f", CubeListBuilder.create(), PartPose.offset(0.0F, 0.1958F, 3.0F));

		PartDefinition cube_r3 = f.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(9, 34).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition b = petals.addOrReplaceChild("b", CubeListBuilder.create(), PartPose.offset(0.0F, 0.1958F, -3.0F));

		PartDefinition cube_r4 = b.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(9, 34).mirror().addBox(-3.0F, 0.0F, 0.0F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.0107F, 0.0F, 3.1416F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 25).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.1667F, -0.25F));

		PartDefinition r_antennae = head.addOrReplaceChild("r_antennae", CubeListBuilder.create().texOffs(24, 25).addBox(0.0F, -7.0F, -3.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -3.0F, 0.0F));

		PartDefinition l_antennae = head.addOrReplaceChild("l_antennae", CubeListBuilder.create().texOffs(24, 25).mirror().addBox(0.0F, -7.0F, -3.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LeafkinBlossom entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(LeafkinBlossomAnimations.LEAFKIN_BLOSSOM_WANDER, limbSwing, limbSwingAmount, 1f, 1f);
		this.animate(entity.idleAnimationState, LeafkinBlossomAnimations.LEAFKIN_BLOSSOM_IDLE, ageInTicks, 1f);
		this.animate(entity.attackAnimationState, LeafkinBlossomAnimations.LEAFKIN_BLOSSOM_SPORE, ageInTicks, 1f);
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