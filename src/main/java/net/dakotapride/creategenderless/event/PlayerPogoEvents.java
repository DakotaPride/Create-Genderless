package net.dakotapride.creategenderless.event;

import net.dakotapride.creategenderless.registry.CreateGenderlessAdvancementCriteria;
import net.dakotapride.creategenderless.registry.CreateGenderlessConfig;
import net.dakotapride.creategenderless.registry.CreateGenderlessMobEffects;
import net.dakotapride.creategenderless.registry.CreateGenderlessTags;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerPogoEvents {

    @SubscribeEvent
    public static void handlePogoFallDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        float amount = event.getAmount();
        DamageSource source = event.getSource();

        if (source.is(DamageTypeTags.IS_FALL) && entity.hasEffect(CreateGenderlessMobEffects.ENBY_POWER.get())) {
            float newAmountMult = CreateGenderlessConfig.POGO_FALL_DAMAGE_MULTIPLIER.get().floatValue() / (entity.getEffect(CreateGenderlessMobEffects.ENBY_POWER.get()).getAmplifier() + 1);
            // 0.25F / (0 + 1) = 0.25 / 1 = 0.25 or 25%
            // 0.25F / (1 + 1) = 0.25 / 2 = 0.125 or ~13%

            // amount * 0.25 ?= 10 * 0.25 = 2.5 damage dealt
            // amount * 0.13 ?= 10 * 0.13 = 1.3 damage dealt

            // Might nerf to instead be at least a 25% damage reduction instead of the potential 87% damage reduction, seems a bit overpowered
            event.setAmount(amount * newAmountMult);
        }
    }

    @SubscribeEvent
    public static void playerPogoEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        Entity target = event.getTarget();

        MobEffect effect = CreateGenderlessMobEffects.ENBY_POWER.get();

        if (stack.is(ItemTags.SWORDS)
                && (target instanceof LivingEntity || target instanceof EndCrystal)
                && player.hasEffect(effect)
                && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            float yDeltaMovement = (player.isShiftKeyDown() ? CreateGenderlessConfig.POGO_SHIFTING_MULTIPLIER.get().floatValue() : CreateGenderlessConfig.POGO_BASE_MULTIPLIER.get().floatValue()) + ((float) player.getEffect(effect).getAmplifier() * 0.15F);
            player.setDeltaMovement(player.getDeltaMovement().with(Direction.Axis.Y, yDeltaMovement));
            player.level().playSound(player, player, SoundEvents.ANVIL_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(stack.getItem(), 10);

            stack.hurtAndBreak((player.getEffect(effect).getAmplifier() + 1), player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));

            if (target instanceof EndCrystal && player instanceof ServerPlayer serverPlayer
                    && player.hasEffect(CreateGenderlessMobEffects.ENBY_POWER.get()) && player.fallDistance >= 200.0F) {
                CreateGenderlessAdvancementCriteria.END_CRYSTAL_POGO.trigger(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void playerPogoBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        BlockState state = event.getLevel().getBlockState(event.getPos());

        MobEffect effect = CreateGenderlessMobEffects.ENBY_POWER.get();

        // holy fuck this is an organised mess
        if (stack.is(ItemTags.SWORDS)
                && (CreateGenderlessTags.BlockTags.POGOABLE.matches(state)

                || (state.is(BlockTags.WALLS)
                && state.getValue(BlockStateProperties.EAST_WALL) == WallSide.NONE
                && state.getValue(BlockStateProperties.WEST_WALL) == WallSide.NONE
                && state.getValue(BlockStateProperties.NORTH_WALL) == WallSide.NONE
                && state.getValue(BlockStateProperties.SOUTH_WALL) == WallSide.NONE)

                || (state.is(BlockTags.FENCES)
                && !state.getValue(PipeBlock.EAST)
                && !state.getValue(PipeBlock.WEST)
                && !state.getValue(PipeBlock.NORTH)
                && !state.getValue(PipeBlock.SOUTH)))
                && player.hasEffect(effect)
                && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            float yDeltaMovement = (player.isShiftKeyDown() ? CreateGenderlessConfig.POGO_SHIFTING_MULTIPLIER.get().floatValue() : CreateGenderlessConfig.POGO_BASE_MULTIPLIER.get().floatValue()) + ((float) player.getEffect(effect).getAmplifier() * 0.15F);

            player.setDeltaMovement(player.getDeltaMovement().with(Direction.Axis.Y, yDeltaMovement));
            event.getLevel().playSound(player, player, SoundEvents.ANVIL_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(stack.getItem(), 10);

            stack.hurtAndBreak(2, player, (entity) -> entity.broadcastBreakEvent(event.getHand()));
        }
    }
}
