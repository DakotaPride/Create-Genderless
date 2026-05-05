package net.dakotapride.creategenderless;

import net.dakotapride.creategenderless.crest.CrestType;
import net.dakotapride.creategenderless.registry.CreateGenderlessAdvancementCriteria;
import net.dakotapride.creategenderless.registry.CreateGenderlessKeybinds;
import net.dakotapride.creategenderless.registry.CreateGenderlessMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CreateGenderlessUtils {
    private static int BIND_COOLDOWN = 0;

    public static void createCrestBinding(CrestType crestType, ServerPlayer serverPlayer) {
        if (BIND_COOLDOWN > 0) {
            BIND_COOLDOWN--;
            CreateGenderlessMod.createDevMessage(serverPlayer, "Create Bind Cooldown: " + BIND_COOLDOWN + " (ticks)", true);
        }

        if (CreateGenderlessKeybinds.BIND.isPressed()) {
            if (BIND_COOLDOWN > 0) {
                serverPlayer.sendSystemMessage(Component.translatable("text.creategenderless.crest_bind.cooldown"));
                return;
            }

            serverPlayer.addEffect(new MobEffectInstance(CreateGenderlessMobEffects.BINDING.get(), crestType.getBindTicks(), crestType.getHealAmount()));
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.GLOWING, crestType.getBindTicks(), crestType.getHealAmount(), false, false, false));
            CreateGenderlessAdvancementCriteria.BINDING.trigger(serverPlayer);
            if (BIND_COOLDOWN == 0) {
                BIND_COOLDOWN = crestType.getBindTicks();
            }
            CreateGenderlessMod.createDevMessage(serverPlayer, "Crest has been bound!", false);
        }
    }

    public static boolean hasRequiredItemsInInventory(int requiredCount, Player player, Item item) {
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

    public static boolean hasRequiredItemsInInventory(int requiredCount, Player player, TagKey<Item> itemTag) {
        int stackCount = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.is(itemTag)) {
                stackCount += stack.getCount();
                if (stackCount >= requiredCount) {
                    return true;
                }
            }
        }

        for (ItemStack stack : player.getInventory().offhand) {
            if (!stack.isEmpty() && stack.is(itemTag)) {
                stackCount += stack.getCount();
                if (stackCount >= requiredCount) {
                    return true;
                }
            }
        }

        return stackCount >= requiredCount;
    }

    public static void removeItemsFromInventory(int count, Player player, Item item) {
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.getItem() == item) {
                stack.shrink(count);
            }
        }

        for (ItemStack stack : player.getInventory().offhand) {
            if (!stack.isEmpty() && stack.getItem() == item) {
                stack.shrink(count);
            }
        }

    }

    public static void removeItemsFromInventory(int count, Player player, TagKey<Item> itemTag) {
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.is(itemTag)) {
                stack.shrink(count);
            }
        }

        for (ItemStack stack : player.getInventory().offhand) {
            if (!stack.isEmpty() && stack.is(itemTag)) {
                stack.shrink(count);
            }
        }
    }
}
