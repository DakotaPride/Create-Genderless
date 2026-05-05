package net.dakotapride.creategenderless.registry;

import com.google.common.collect.ImmutableSet;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CreateGenderlessVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, CreateGenderlessMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, CreateGenderlessMod.MOD_ID);

    public static final RegistryObject<PoiType> WEAVEMASTER_POI = POI_TYPES.register("weavemaster_poi",
            () -> new PoiType(ImmutableSet.copyOf(CreateGenderlessBlocks.SILK_SPOOL.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> WEAVEMASTER =
            VILLAGER_PROFESSIONS.register("weavemaster", () -> new VillagerProfession("weavemaster",
                    holder -> holder.get() == WEAVEMASTER_POI.get(), holder -> holder.get() == WEAVEMASTER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_BUTCHER));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}