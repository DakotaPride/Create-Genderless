package net.dakotapride.creategenderless;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.contraptions.wrench.RadialWrenchMenu;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.createmod.catnip.lang.FontHelper;
import net.dakotapride.creategenderless.block.SilkSpoolBlock;
import net.dakotapride.creategenderless.entity.client.lumarfly.LumarflyModel;
import net.dakotapride.creategenderless.entity.client.lumarfly.LumarflyRenderer;
import net.dakotapride.creategenderless.entity.client.leafkin.LeafkinModel;
import net.dakotapride.creategenderless.entity.client.leafkin.LeafkinRenderer;
import net.dakotapride.creategenderless.recipe.CreateGenderlessFanProcessing;
import net.dakotapride.creategenderless.registry.*;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(CreateGenderlessMod.MOD_ID)
public class CreateGenderlessMod {
    public static final String MOD_ID = "creategenderless";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final NonNullSupplier<CreateRegistrate> REGISTRATE =
            NonNullSupplier.lazy(() -> CreateRegistrate.create(MOD_ID));

    public static final boolean isEstrogenLoaded = ModList.get().isLoaded("estrogen");

    public static final FontHelper.Palette GENDERLESS_TOOLTIP_PALETTE = new FontHelper.Palette(FontHelper.styleFromColor(0xBF78B8), FontHelper.styleFromColor(0xDFC4ED));

    static {
        REGISTRATE.get().setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, GENDERLESS_TOOLTIP_PALETTE));

        RadialWrenchMenu.registerRotationProperty(SilkSpoolBlock.SPOOL_CONNECTION, "Spool Connection");

    }

    public static void createDevLogger(String logInfo) {
        if (!FMLLoader.isProduction())
            LOGGER.info(logInfo);
    }

    public static void createDevMessage(ServerPlayer player, String msgInfo, boolean hotbarMsg) {
        if (!FMLLoader.isProduction())
            player.sendSystemMessage(Component.literal(msgInfo), hotbarMsg);
    }

    public static ResourceLocation asResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public CreateGenderlessMod(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();

        REGISTRATE.get().registerEventListeners(eventBus);

        CreateGenderlessItems.transRights();
        CreateGenderlessBlocks.transRights();
        CreateGenderlessFoods.transRights();
        CreateGenderlessFluids.transRights();
        CreateGenderlessCreativeModeTabs.transRights(eventBus);
        CreateGenderlessMobEffects.transRights(eventBus);
        CreateGenderlessPotions.transRights(eventBus);
        CreateGenderlessEntityTypes.transRights(eventBus);
        CreateGenderlessRecipeTypes.transRights(eventBus);
        CreateGenderlessAdvancementCriteria.transRights();
        CreateGenderlessParticleTypes.transRights(eventBus);
        CreateGenderlessVillagers.register(eventBus);
        eventBus.addListener(CreateGenderlessMod::onRegister);

        //context.registerConfig(ModConfig.Type.SERVER, CreateGenderlessConfig.SERVER_SPEC, "creategenderless-server.toml");
        CreateGenderlessConfigs.register(context);

        MinecraftForge.EVENT_BUS.register(this);
        //eventBus.addListener(this::commonSetup);
    }

//    @SubscribeEvent
//    public void commonSetup(FMLCommonSetupEvent event) {
//        CuriosApi.registerCurio(CreateGenderlessItems.BASIC_CREST, new CurioItem());
//    }

    public static void onRegister(final RegisterEvent event) {
        CreateGenderlessFanProcessing.transRights();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(CreateGenderlessEntityTypes.LUMARFLY.get(), LumarflyRenderer::new);
            EntityRenderers.register(CreateGenderlessEntityTypes.LEAFKIN.get(), LeafkinRenderer::new);
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(CreateGenderlessParticleTypes.LUMARFLIES_SMALL.get(), SoulParticle.EmissiveProvider::new);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(LumarflyModel.LAYER_LOCATION, LumarflyModel::createBodyLayer);
            event.registerLayerDefinition(LeafkinModel.LAYER_LOCATION, LeafkinModel::createBodyLayer);
        }
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE.get();
    }
}
