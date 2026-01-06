package net.dakotapride.creategenderless.event;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.Lumarfly;
import net.dakotapride.creategenderless.registry.CreateGenderlessEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateGenderlessMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreateGenderlessMobCreationEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CreateGenderlessEntityTypes.LUMARFLY.get(), Lumarfly.createAttributes().build());
    }
}