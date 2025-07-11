package net.dakotapride.genderless.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.simibubi.create.AllEnchantments;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.dakotapride.genderless.init.GenderlessStatusEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.awt.*;
import java.util.List;
import java.util.UUID;

import static net.dakotapride.genderless.CreateGenderlessMod.isEstrogenLoaded;

public class GenderFluidPatchItem extends Item implements ICurioItem, BotariumFluidItem<WrappedItemFluidContainer> {
    private static final int TRIGGER_EVERY_X_TICKS = 300;
    private static final int EFFECT_DURATION = 520;

    public GenderFluidPatchItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ItemFluidContainer itemFluidManager = this.getFluidContainer(stack);
        Level level = slotContext.entity().level();
        if (!level.isClientSide) {
            LivingEntity var6 = slotContext.entity();
            if (var6 instanceof Player) {
                Player player = (Player)var6;
                if ((itemFluidManager.getFluids().get(0)).getFluidAmount() > 0L) {
                    if (level.getGameTime() % 300L == 0L) {
                        this.addEffect(player, level, GenderlessStatusEffects.GENDERFLUIDITY.get());
                    }

                    if (level.getGameTime() % 72L == 0L && !player.isCreative()) {
                        itemFluidManager.extractFromSlot(0, FluidHolder.of(EstrogenFluids.GENDER_FLUID.get(), FluidConstants.getBucketAmount() / 1000L), false);
                        itemFluidManager.serialize(stack.getOrCreateTag());
                    }

                    if (!player.isCreative() && player.isInFluidType(EstrogenFluids.GENDER_FLUID.get().getFluidType())) {
                        player.setAirSupply(300);
                        this.addEffect(player, level, MobEffects.MOVEMENT_SPEED);
                    }
                }
            }
        }
    }

    public void addEffect(Player player, Level level, MobEffect effect) {
        player.addEffect(new MobEffectInstance(effect, 520, 0, false, false, false));
    }

    public long getMaxCapacity(ItemStack stack) {
        return FluidConstants.getBucketAmount() + FluidConstants.getBucketAmount() / 2L * (long) EnchantmentHelper.getEnchantments(stack).getOrDefault(AllEnchantments.CAPACITY.get(), 0);
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        ItemStackHolder holder = new ItemStackHolder(stack);
        ItemFluidContainer itemFluidManager = FluidContainer.of(holder);
        if (itemFluidManager != null) {
            long amount = FluidConstants.toMillibuckets((itemFluidManager.getFluids().get(0)).getFluidAmount());
            long amountCapacity = FluidConstants.toMillibuckets(itemFluidManager.getTankCapacity(0));
            String fluidString = Component.translatable("fluid_type.estrogen.gender_fluid").getString();
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
            if (var6 instanceof Player) {
                Player player = (Player)var6;
                if ((itemFluidManager.getFluids().get(0)).getFluidAmount() > 0L) {
                    this.addEffect(player, level, GenderlessStatusEffects.GENDERFLUIDITY.get());
                }
            }
        }
    }

    public ItemStack getFullStack() {
        ItemStack stack = this.getDefaultInstance();
        ItemFluidContainer itemFluidManager = this.getFluidContainer(stack);
        itemFluidManager.insertFluid(FluidHolder.of(EstrogenFluids.GENDER_FLUID.get(), FluidConstants.getBucketAmount()), false);
        itemFluidManager.serialize(stack.getOrCreateTag());
        return stack;
    }

    public long getAmount(ItemStack stack) {
        ItemStackHolder holder = new ItemStackHolder(stack);
        ItemFluidContainer itemFluidManager = FluidContainer.of(holder);
        return (itemFluidManager.getFluids().get(0)).getFluidAmount();
    }

    public WrappedItemFluidContainer getFluidContainer(ItemStack stack) {
        return new WrappedItemFluidContainer(stack, new SimpleFluidContainer(this.getMaxCapacity(stack), 1, (amount, fluid) -> fluid.is(EstrogenFluids.GENDER_FLUID.get())));
    }

    public boolean isBarVisible(@NotNull ItemStack stack) {
        return this.getAmount(stack) != this.getMaxCapacity(stack);
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        return (int)((double)this.getAmount(stack) / (double)this.getMaxCapacity(stack) * (double)13.0F);
    }

    public int getBarColor(@NotNull ItemStack stack) {
        return new Color(253, 158, 193).getRGB();
    }
}
