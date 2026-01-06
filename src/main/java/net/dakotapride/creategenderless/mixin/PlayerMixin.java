package net.dakotapride.creategenderless.mixin;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.registry.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Unique
    Player player = (Player) (Object) this;

    public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private boolean hasRequiredItemsInInventory(int requiredCount, ServerPlayer player, Item item) {
        int stackCount = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.getItem() == item) {
                stackCount += stack.getCount();
                if (stackCount >= requiredCount) {
                    return true;
                }
            }
        }

        for (ItemStack stack : player.getInventory().offhand) {
            if (!stack.isEmpty() && stack.getItem() == item) {
                stackCount += stack.getCount();
                if (stackCount >= requiredCount) {
                    return true;
                }
            }
        }

        return stackCount >= requiredCount;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            // Save The Lumarflies! (advancement)
            // https://youtu.be/FxI1s5dZCs0
            // 652 Luma(r)fly lamps + 1 Luma(r)fly Lantern from Sly
            if (hasRequiredItemsInInventory(653, serverPlayer, CreateGenderlessItems.LUMARFLY_BOTTLE.get()))
                CreateGenderlessAdvancementCriteria.SAVE_THE_LUMARFLIES.trigger(serverPlayer);
            // A True Servant... (advancement)
            // A True Servant... Collects 3000~ Shellrock Rubble For The Kingdom
            // 3000~ Shelllrock Rubble | 47 stacks of Shellrock blocks
            if (hasRequiredItemsInInventory(47, serverPlayer, CreateGenderlessBlocks.SHELLROCK.asItem()))
                CreateGenderlessAdvancementCriteria.SHELLROCK.trigger(serverPlayer);
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (player.hasEffect(CreateGenderlessMobEffects.ENBY_POWER.get())) {
            if (source.is(DamageTypeTags.IS_EXPLOSION)) {
                cir.setReturnValue(false);
            }
        }

        if (player.hasEffect(CreateGenderlessMobEffects.UNIFIED.get()) && CreateGenderlessConfig.UNIFIED_CANCEL_DAMAGE.get())
            cir.setReturnValue(false);
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void attack(Entity entity, CallbackInfo ci) {
        if (player.hasEffect(CreateGenderlessMobEffects.UNIFIED.get()) && entity instanceof LivingEntity && CreateGenderlessConfig.UNIFIED_CANCEL_ATTACKS.get())
            ci.cancel();
    }
}
