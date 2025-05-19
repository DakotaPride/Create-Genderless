package net.dakotapride.genderless.init;

import net.dakotapride.genderless.CreateGenderlessMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GenderlessCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateGenderlessMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATE_GENDERLESS_TAB = CREATIVE_MOD_TABS.register("create_genderless_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(GenderlessItems.GENDERLESS_PILL.get()))
                    .title(Component.translatable("itemGroup.genderless.create_genderless_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(GenderlessItems.GENDERLESS_PILL);
                        pOutput.accept(GenderlessItems.GENDERFLUID_PILL);
                        pOutput.accept(GenderlessItems.CIRCUIT_BOARD);
                        pOutput.accept(GenderlessItems.BROKEN_CIRCUIT_BOARD);
                        pOutput.accept(GenderlessItems.BRA_OF_HOLDING);
                        pOutput.accept(GenderlessBlocks.GENDERLESS_PILL_BOX);
                        pOutput.accept(GenderlessBlocks.GENDERFLUID_PILL_BOX);
                        pOutput.accept(GenderlessBlocks.BINARY_BLOCK);
                        pOutput.accept(GenderlessFluids.BINARY_FLUID.get().getBucket());
                        pOutput.accept(GenderlessFluids.NON_BINARY_FLUID.get().getBucket());
                        pOutput.accept(GenderlessFluids.ZERO_ONE_ZERO_ZERO_ZERO_ONE_ZERO_ONE.get().getBucket());
                        pOutput.accept(GenderlessFluids.VOID.get().getBucket());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
