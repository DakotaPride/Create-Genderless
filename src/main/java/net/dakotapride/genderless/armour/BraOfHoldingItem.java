package net.dakotapride.genderless.armour;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.items.GenderChangePotionItem;
import dev.mayaqq.estrogen.utils.Time;
import net.dakotapride.genderless.CreateGenderlessMod;
import net.dakotapride.genderless.init.GenderlessPartialModels;
import net.dakotapride.genderless.init.GenderlessStatusEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import static net.dakotapride.genderless.CreateGenderlessMod.isEstrogenLoaded;

public class BraOfHoldingItem extends Item implements ICurioItem {
    public BraOfHoldingItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {

        LivingEntity entity = slotContext.entity();

        if (entity.hasEffect(GenderlessStatusEffects.GENDERFLUIDITY.get())) {
            if (entity instanceof ServerPlayer player)
                player.displayClientMessage(Component.translatable("text.genderless.bra_of_holding.disabled"), true);
            return false;
        }

        return ICurioItem.super.canEquip(slotContext, stack);
    }

    public static boolean equipped;

    public static boolean isEquippedInCuriosBodySlot() {
        return equipped;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Renderer implements ICurioRenderer {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(CreateGenderlessMod.MOD_ID, "bra_of_holding"), "bra_of_holding");

        private final HumanoidModel<LivingEntity> model;

        public Renderer(ModelPart part) {
            this.model = new HumanoidModel<>(part);
        }

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

//            if (slotContext.entity().getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
//                // Prepare values for transformation
//                model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//                model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
//                ICurioRenderer.followBodyRotations(slotContext.entity(), model);
//
//                // Translate and rotate with our head
//                matrixStack.pushPose();
//                matrixStack.translate(model.body.x / 16.0, model.body.y / 16.0, model.body.z / 16.0);
//                matrixStack.mulPose(Axis.YP.rotation(model.body.yRot));
//                matrixStack.mulPose(Axis.XP.rotation(model.body.xRot));
//
//                // Translate and scale to our head
//                matrixStack.translate(0, 0.33, 0);
//                matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
//                matrixStack.scale(0.625f, 0.625f, 0.625f);
//
//
//                // Render
//                Minecraft mc = Minecraft.getInstance();
//                mc.getItemRenderer()
//                        .renderStatic(stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack,
//                                renderTypeBuffer, mc.level, 0);
//                matrixStack.popPose();
//            }

            // Prepare values for transformation
            model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
            ICurioRenderer.followBodyRotations(slotContext.entity(), model);

            // Translate and rotate with our head
            matrixStack.pushPose();
            matrixStack.translate(model.body.x / 16.0, model.body.y / 16.0, model.body.z / 16.0);
            matrixStack.mulPose(Axis.YP.rotation(model.body.yRot));
            matrixStack.mulPose(Axis.XP.rotation(model.body.xRot));

            // Translate and scale to our head
            matrixStack.translate(0, 0.33, 0);
            matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
            matrixStack.scale(0.625f, 0.625f, 0.625f);


            // Render
            Minecraft mc = Minecraft.getInstance();
            mc.getItemRenderer()
                    .renderStatic(stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack,
                            renderTypeBuffer, mc.level, 0);
            matrixStack.popPose();
        }

        public static MeshDefinition mesh() {
            CubeListBuilder builder = new CubeListBuilder();
            MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
            mesh.getRoot().addOrReplaceChild("body", builder, PartPose.ZERO);
            return mesh;
        }
    }

    public static class Model extends BakedModelWrapper<BakedModel> {

        public Model(BakedModel template) {
            super(template);
        }

        @Override
        public BakedModel applyTransform(ItemDisplayContext cameraItemDisplayContext, PoseStack mat, boolean leftHanded) {
            if (cameraItemDisplayContext == ItemDisplayContext.HEAD)
                return GenderlessPartialModels.BRA_OF_HOLDING.get()
                        .applyTransform(cameraItemDisplayContext, mat, leftHanded);
            return super.applyTransform(cameraItemDisplayContext, mat, leftHanded);
        }

    }

//    @Override
//    public void inventoryTick(ItemStack stack, Level level, Entity pEntity, int pSlotId, boolean pIsSelected) {
//        if (pEntity instanceof LivingEntity livingEntity) {
//            if (pSlotId == EquipmentSlot.CHEST.getIndex())
//                if (livingEntity instanceof Player player)
//                    tick(level, player);
//        }
//        super.inventoryTick(stack, level, pEntity, pSlotId, pIsSelected);
//    }


    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ICurioItem.super.onEquip(slotContext, prevStack, stack);

        equipped = true;
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

        equipped = false;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Entity entity = slotContext.entity();
        Level level = slotContext.entity().level();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity instanceof Player player)
                tick(level, player);
        }
        ICurioItem.super.curioTick(slotContext, stack);
    }

    public void tick(Level level, Player player) {
        if (isEstrogenLoaded() && !level.isClientSide) {
            if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get())) {
                player.removeEffect(EstrogenEffects.ESTROGEN_EFFECT.get());
            }
            // bandaid fix, really need to just cancel the usage of the Gender Change Potion while this item is equipped in the curios body slot
            GenderChangePotionItem.changeGender(level, player, 0);
        }
        if (!level.isClientSide && player.hasEffect(GenderlessStatusEffects.GENDERFLUIDITY.get()))
            player.removeEffect(GenderlessStatusEffects.GENDERFLUIDITY.get());
        if (!level.isClientSide)
            player.addEffect(new MobEffectInstance(GenderlessStatusEffects.EUPHORIA.get(), 60, 1, false, false, true));
    }
}
