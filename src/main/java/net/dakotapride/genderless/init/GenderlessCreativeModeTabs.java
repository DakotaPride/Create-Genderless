package net.dakotapride.genderless.init;

import net.dakotapride.genderless.CreateGenderlessMod;
import net.dakotapride.genderless.item.GenderlessPatchItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GenderlessCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateGenderlessMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATE_GENDERLESS_TAB = CREATIVE_MOD_TABS.register("create_genderless_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(GenderlessItems.GENDERLESS_PILL.get()))
                    .title(Component.translatable("itemGroup.genderless.create_genderless_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(GenderlessItems.GENDERLESS_PILL);
                        output.accept(GenderlessItems.GENDERLESS_PATCH);
                        output.accept(GenderlessItems.GENDERLESS_PATCH.get().getFullStack());
                        output.accept(GenderlessItems.GENDERFLUID_PILL);
                        output.accept(GenderlessItems.GENDERFLUID_PATCH);
                        output.accept(GenderlessItems.GENDERFLUID_PATCH.get().getFullStack());
                        output.accept(GenderlessItems.CIRCUIT_BOARD);
                        output.accept(GenderlessItems.BROKEN_CIRCUIT_BOARD);
                        output.accept(GenderlessItems.GENDERSLIME);
                        output.accept(GenderlessBlocks.GENDERSLIME);
                        output.accept(GenderlessItems.BRA_OF_HOLDING);
                        output.accept(GenderlessItems.BRA_OF_HOLDING.get().getFullStack());
                        output.accept(GenderlessBlocks.GENDERLESS_PILL_BOX);
                        output.accept(GenderlessBlocks.GENDERFLUID_PILL_BOX);
                        output.accept(GenderlessBlocks.BINARY_BLOCK);
                        output.accept(tippedArrow(GenderlessPotions.GENDERLESS_POTION.get()));
                        output.accept(BotariumGenderlessFluids.BINARY_FLUID.getBucket());
                        output.accept(BotariumGenderlessFluids.NON_BINARY_FLUID.getBucket());
                        output.accept(BotariumGenderlessFluids.ZERO_ONE_ZERO_ZERO_ZERO_ONE_ZERO_ONE.getBucket());
                        output.accept(BotariumGenderlessFluids.VOID.getBucket());
                    })
                    .build());

    public static ItemStack tippedArrow(Potion potion) {
        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.setPotion(stack, potion);
        return stack;
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
