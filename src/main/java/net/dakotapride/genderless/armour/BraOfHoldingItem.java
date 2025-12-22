package net.dakotapride.genderless.armour;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllEnchantments;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import dev.mayaqq.estrogen.content.items.GenderChangePotionItem;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.dakotapride.genderless.CreateGenderlessMod;
import net.dakotapride.genderless.init.BotariumGenderlessFluids;
import net.dakotapride.genderless.init.GenderlessPartialModels;
import net.dakotapride.genderless.init.GenderlessStatusEffects;
import net.minecraft.ChatFormatting;
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
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.awt.*;
import java.util.List;

import static net.dakotapride.genderless.CreateGenderlessMod.isEstrogenLoaded;

public class BraOfHoldingItem extends Item implements ICurioItem, BotariumFluidItem<WrappedItemFluidContainer> {
    public BraOfHoldingItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public long getMaxCapacity(ItemStack stack) {
        return FluidConstants.getBucketAmount() + FluidConstants.getBucketAmount() / 2L * (long) EnchantmentHelper.getEnchantments(stack).getOrDefault(AllEnchantments.CAPACITY.get(), 0);
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        ItemStackHolder holder = new ItemStackHolder(stack);
        ItemFluidContainer itemFluidManager = FluidContainer.of(holder);
        if (itemFluidManager != null) {
            long amount = FluidConstants.toMillibuckets((itemFluidManager.getFluids().get(0)).getFluidAmount());
            long amountCapacity = FluidConstants.toMillibuckets(itemFluidManager.getTankCapacity(0));
            String fluidString = Component.translatable("fluid_type.genderless.void").getString();
            tooltipComponents.add(Component.literal(" "));
            tooltipComponents.add(Component.literal(String.format("%s: %smb / %smb", fluidString, amount, amountCapacity)).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
            tooltipComponents.add(Component.literal(" "));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Level level = slotContext.entity().level();
        ItemFluidContainer itemFluidManager = this.getFluidContainer(stack);
        if (!level.isClientSide) {
            LivingEntity var6 = slotContext.entity();
            if (var6 instanceof Player player) {
                if ((itemFluidManager.getFluids().get(0)).getFluidAmount() > 0L) {
                    this.addEffect(player, level, GenderlessStatusEffects.EUPHORIA.get());
                }
            }
        }

        equipped = true;
    }

    public void addEffect(Player player, Level level, MobEffect effect) {
        player.addEffect(new MobEffectInstance(effect, 520, 0, false, false, false));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

        equipped = false;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();

        ItemFluidContainer itemFluidManager = this.getFluidContainer(stack);

        if (!level.isClientSide) {
            LivingEntity var6 = slotContext.entity();
            if (var6 instanceof Player player) {
                if (itemFluidManager.getFluids().get(0).getFluidAmount() > 0L) {
                    tick(level, player);

                    if (level.getGameTime() % 72L == 0L && !player.isCreative()) {
                        itemFluidManager.extractFromSlot(0, FluidHolder.of(BotariumGenderlessFluids.VOID.getSource(), FluidConstants.getBucketAmount() / 1000L), false);
                        itemFluidManager.serialize(stack.getOrCreateTag());
                    }
                }
            }
        }

        ICurioItem.super.curioTick(slotContext, stack);
    }

    public void addEffect(Player player, Level level, MobEffect effect, int duration, float amplifier, boolean show) {
        player.addEffect(new MobEffectInstance(effect, 520, 0, false, false, show));
    }

    public void tick(Level level, Player player) {
        if (isEstrogenLoaded() && !level.isClientSide) {
            if (player.hasEffect(EstrogenEffects.getEstrogen())) {
                player.removeEffect(EstrogenEffects.getEstrogen());
            }
            // bandaid fix, really need to just cancel the usage of the Gender Change Potion while this item is equipped in the curios body slot
            GenderChangePotionItem.Companion.changeGender(level, player, 0);
        }
        if (!level.isClientSide && player.hasEffect(GenderlessStatusEffects.GENDERFLUIDITY.get()))
            player.removeEffect(GenderlessStatusEffects.GENDERFLUIDITY.get());
        if (!level.isClientSide)
            if (level.getGameTime() % 300L == 0L) {
                this.addEffect(player, level, GenderlessStatusEffects.EUPHORIA.get(), 60, 1, true);
            }
    }


    public ItemStack getFullStack() {
        ItemStack stack = this.getDefaultInstance();
        ItemFluidContainer itemFluidManager = this.getFluidContainer(stack);
        itemFluidManager.insertFluid(FluidHolder.of(BotariumGenderlessFluids.VOID.getSource(), FluidConstants.getBucketAmount()), false);
        itemFluidManager.serialize(stack.getOrCreateTag());
        return stack;
    }

    public long getAmount(ItemStack stack) {
        ItemStackHolder holder = new ItemStackHolder(stack);
        ItemFluidContainer itemFluidManager = FluidContainer.of(holder);
        return (itemFluidManager.getFluids().get(0)).getFluidAmount();
    }

    public WrappedItemFluidContainer getFluidContainer(ItemStack stack) {
        return new WrappedItemFluidContainer(stack, new SimpleFluidContainer(this.getMaxCapacity(stack), 1, (amount, fluid) -> fluid.is(BotariumGenderlessFluids.VOID.getSource())));
    }

    public boolean isBarVisible(@NotNull ItemStack stack) {
        return this.getAmount(stack) != this.getMaxCapacity(stack);
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        return (int)((double)this.getAmount(stack) / (double)this.getMaxCapacity(stack) * (double)13.0F);
    }

    public int getBarColor(@NotNull ItemStack stack) {
        return new Color(4, 0, 61).getRGB();
    }
}
