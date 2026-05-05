package net.dakotapride.creategenderless.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.CreateGenderlessUtils;
import net.dakotapride.creategenderless.crest.CrestType;
import net.dakotapride.creategenderless.registry.CreateGenderlessKeybinds;
import net.dakotapride.creategenderless.registry.CreateGenderlessMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class BindingCrestItem extends Item implements ICurioItem {
    CrestType crestType;

    Multimap<Attribute, AttributeModifier> attributeMap = HashMultimap.create();

    private final AttributeModifier reachModifier = new AttributeModifier(
            "152f73eb-88aa-4cdf-a160-8da3f609e79a",
            -1.0D, AttributeModifier.Operation.ADDITION);
    private final AttributeModifier entityReachModifier = new AttributeModifier(
            "95d4a0e3-57b9-41e2-bf79-8c8bbdd0b2c9",
            -0.5D, AttributeModifier.Operation.ADDITION);

    public BindingCrestItem(CrestType crestType, Properties properties) {
        super(properties);
        this.crestType = crestType;
        if (CrestType.PYROMANIAC.checkCrestType(crestType)) {
            attributeMap.put(ForgeMod.BLOCK_REACH.get(), this.reachModifier);
            attributeMap.put(ForgeMod.ENTITY_REACH.get(), this.entityReachModifier);
        }
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        if (CrestType.PYROMANIAC.checkCrestType(crestType)) {
            tooltips.add(Component.literal(""));
            tooltips.add(Component.translatable("text.creategenderless.crest.pyromaniac.fire_immunity").withStyle(ChatFormatting.BLUE));
            tooltips.add(Component.translatable("text.creategenderless.crest.pyromaniac.fire_attacks").withStyle(ChatFormatting.BLUE));
        }
        if (CrestType.FAITHFUL.checkCrestType(crestType)) {
            tooltips.add(Component.literal(""));
            tooltips.add(Component.translatable("curios.modifiers.crest.with_needle"));
            tooltips.add(Component.translatable("text.creategenderless.crest.faithful.lunge", CreateGenderlessKeybinds.LUNGE.getBoundKey()).withStyle(ChatFormatting.BLUE));
        }
        return ICurioItem.super.getAttributesTooltip(tooltips, stack);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (CrestType.PYROMANIAC.checkCrestType(crestType)) {
            if (!uuid.equals(reachModifier.getId())) {
                attributeMap.remove(ForgeMod.BLOCK_REACH.get(), reachModifier);
                attributeMap.put(ForgeMod.BLOCK_REACH.get(), reachModifier);
            }
            if (!uuid.equals(entityReachModifier.getId())) {
                attributeMap.remove(ForgeMod.ENTITY_REACH.get(), entityReachModifier);
                attributeMap.put(ForgeMod.ENTITY_REACH.get(), entityReachModifier);
            }
        }
        return attributeMap;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity instanceof ServerPlayer serverPlayer) {
            CreateGenderlessUtils.createCrestBinding(crestType, serverPlayer);
        }

        if (CrestType.PYROMANIAC.checkCrestType(crestType)) {
            entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, false, false, false));
            entity.addEffect(new MobEffectInstance(CreateGenderlessMobEffects.FIERCE.get(), 100, 0, false, false, false));
        }

        if (CrestType.FAITHFUL.checkCrestType(crestType)) {
            entity.addEffect(new MobEffectInstance(CreateGenderlessMobEffects.SWIFT.get(), 100, 0, false, false, false));
            if (entity instanceof Player player && CreateGenderlessKeybinds.LUNGE.isPressed() && player.getMainHandItem().getItem() instanceof NeedleItem) {
                Vec3 lookAngle = player.getLookAngle();
                player.setDeltaMovement(lookAngle.x * 1.5F, lookAngle.y, lookAngle.z * 1.5F);
                player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 20);
            }
        }

        if (CrestType.MUCKED.checkCrestType(crestType)) {
            if (entity.hasEffect(MobEffects.POISON))
                entity.removeEffect(MobEffects.POISON);
            entity.addEffect(new MobEffectInstance(CreateGenderlessMobEffects.CROAKED.get(), 100, 0, false, false, false));
        }
    }
}
