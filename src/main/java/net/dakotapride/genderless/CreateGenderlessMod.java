package net.dakotapride.genderless;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import net.dakotapride.genderless.advancement.GenderlessAdvancementUtils;
import net.dakotapride.genderless.armour.BraOfHoldingItem;
import net.dakotapride.genderless.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(CreateGenderlessMod.MOD_ID)
public class CreateGenderlessMod {
    public static final String MOD_ID = "genderless";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE));
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static boolean isEstrogenLoaded() {
        return ModList.get().isLoaded("estrogen");
    }

    public static boolean isTestosteroneLoaded() {
        return ModList.get().isLoaded("testosterone");
    }

    public CreateGenderlessMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(modEventBus);

        GenderlessItems.register();
        GenderlessBlocks.register();
        GenderlessFluids.register();
        GenderlessCreativeModeTabs.register(modEventBus);
        GenderlessStatusEffects.register(modEventBus);
        GenderlessPotions.register(modEventBus);
        GenderlessAdvancementUtils.register();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateGenderlessClient.onCtorClient(modEventBus, forgeEventBus));

        modEventBus.addListener(CreateGenderlessMod::onInterModEnqueue);
    }


    private static void onInterModEnqueue(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            CuriosRendererRegistry.register(GenderlessItems.BRA_OF_HOLDING.get(), () -> new BraOfHoldingItem.Renderer(Minecraft.getInstance().getEntityModels().bakeLayer(BraOfHoldingItem.Renderer.LAYER)));
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BraOfHoldingItem.Renderer.LAYER, () -> LayerDefinition.create(BraOfHoldingItem.Renderer.mesh(), 1, 1));
        }
    }
}