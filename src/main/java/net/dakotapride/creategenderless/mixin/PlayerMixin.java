package net.dakotapride.creategenderless.mixin;

import net.dakotapride.creategenderless.CreateGenderlessConfigs;
import net.dakotapride.creategenderless.CreateGenderlessUtils;
import net.dakotapride.creategenderless.item.NeedleItem;
import net.dakotapride.creategenderless.registry.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.common.capability.CurioInventoryCapability;

import java.util.Map;
import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Unique
    Player player = (Player) (Object) this;

    public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            // Save The Lumarflies! (advancement)
            // https://youtu.be/FxI1s5dZCs0
            // 652 Luma(r)fly lamps + 1 Luma(r)fly Lantern from Sly
            if (CreateGenderlessUtils.hasRequiredItemsInInventory(653, serverPlayer, CreateGenderlessItems.LUMARFLY_BOTTLE.get()))
                CreateGenderlessAdvancementCriteria.SAVE_THE_LUMARFLIES.trigger(serverPlayer);
            // A True Servant... (advancement)
            // A True Servant... Collects 3000~ Shellrock Rubble For The Kingdom
            // 3000~ Shelllrock Rubble | 37 stacks of Shellrock blocks
            if (CreateGenderlessUtils.hasRequiredItemsInInventory((37*64), serverPlayer, CreateGenderlessBlocks.SHELLROCK.asItem()))
                CreateGenderlessAdvancementCriteria.SHELLROCK.trigger(serverPlayer);
            // That Darn Donation Quest (advancement)
            // The rosary grind was always a bit... much, yeah?
            // 500 Pale Rosary Blocks | ~31 stacks of Pale Rosaries
            if (CreateGenderlessUtils.hasRequiredItemsInInventory(500, serverPlayer, CreateGenderlessItems.PALE_ROSARY.get()))
                CreateGenderlessAdvancementCriteria.ROSARY.trigger(serverPlayer);
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (player.hasEffect(CreateGenderlessMobEffects.ENBY_POWER.get())) {
            if (source.is(DamageTypeTags.IS_EXPLOSION)) {
                cir.setReturnValue(false);
            }
        }

        if (player.hasEffect(CreateGenderlessMobEffects.UNIFIED.get()) && CreateGenderlessConfigs.server().unifedMobEffectConfig.UNIFIED_CANCEL_DAMAGE.get())
            cir.setReturnValue(false);
    }
}
