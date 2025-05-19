package net.dakotapride.genderless;

import net.dakotapride.genderless.init.GenderlessPartialModels;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateGenderlessClient {
    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(CreateGenderlessClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        GenderlessPartialModels.register();
    }
}
