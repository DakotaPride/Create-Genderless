package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.entity.LeafkinBlossom;
import net.dakotapride.creategenderless.entity.LeafkinSlabber;
import net.dakotapride.creategenderless.entity.Lumarfly;
import net.dakotapride.creategenderless.entity.Leafkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CreateGenderlessEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CreateGenderlessMod.MOD_ID);

    public static final RegistryObject<EntityType<Lumarfly>> LUMARFLY =
            ENTITY_TYPES.register("lumarfly", () -> EntityType.Builder.of(Lumarfly::new, MobCategory.AMBIENT)
                    .sized(0.25f, 0.45f).build("lumarfly"));
    public static final RegistryObject<EntityType<Leafkin>> LEAFKIN =
            ENTITY_TYPES.register("leafkin", () -> EntityType.Builder.of(Leafkin::new, MobCategory.MONSTER)
                    .sized(0.65f, 1.75f).build("leafkin"));
    public static final RegistryObject<EntityType<LeafkinSlabber>> LEAFKIN_SLABBER =
            ENTITY_TYPES.register("leafkin_slabber", () -> EntityType.Builder.of(LeafkinSlabber::new, MobCategory.MONSTER)
                    .sized(0.65f, 0.35f).build("leafkin_slabber"));
    public static final RegistryObject<EntityType<LeafkinBlossom>> LEAFKIN_BLOSSOM =
            ENTITY_TYPES.register("leafkin_blossom", () -> EntityType.Builder.of(LeafkinBlossom::new, MobCategory.MONSTER)
                    .sized(0.65f, 0.35f).build("leafkin_blossom"));


    public static void transRights(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}