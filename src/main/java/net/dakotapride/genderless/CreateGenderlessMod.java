package net.dakotapride.genderless;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;

import dev.mayaqq.estrogen.content.EstrogenItems;
import net.createmod.catnip.lang.FontHelper;
import net.dakotapride.genderless.advancement.GenderlessAdvancementUtils;
import net.dakotapride.genderless.armour.BraOfHoldingItem;
import net.dakotapride.genderless.client.overlay.GenderlessOverlayUtils;
import net.dakotapride.genderless.init.*;
import net.dakotapride.genderless.item.GenericPatchItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import uwu.serenity.kritter.RegistryManager;
import uwu.serenity.kritter.forge.RegistryManagerImpl;

@Mod(CreateGenderlessMod.MOD_ID)
public class CreateGenderlessMod{
    public static final String MOD_ID = "genderless";
    public static final String NAME = "Create: Genderless";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);
    public static final RegistryManager REGISTRIES = new RegistryManagerImpl(MOD_ID);

    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE));
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
//        modEventBus.register(CreateGenderlessMod.REGISTRIES);

        BotariumGenderlessFluids.FLUID_REGISTRAR.register();
        GenderlessItems.register();
        GenderlessBlocks.register();
        //GenderlessFluids.register();
        GenderlessCreativeModeTabs.register(modEventBus);
        GenderlessStatusEffects.register(modEventBus);
        GenderlessPotions.register(modEventBus);
        GenderlessAdvancementUtils.register();
        BotariumGenderlessFluids.FLUIDS.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateGenderlessClient.onCtorClient(modEventBus, forgeEventBus));
    }

    private void setup(FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.MUNDANE)), Ingredient.of(GenderlessItems.GENDERSLIME), EstrogenItems.INSTANCE.getGenderChangePotion().getDefaultInstance());
        //PotionBrewing.(GenderlessItems.GENDERSLIME.asStack(), EstrogenItems.GENDER_CHANGE_POTION.asStack());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            CuriosRendererRegistry.register(GenderlessItems.BRA_OF_HOLDING.get(), () -> new BraOfHoldingItem.Renderer(Minecraft.getInstance().getEntityModels().bakeLayer(BraOfHoldingItem.Renderer.LAYER)));

            CuriosRendererRegistry.register(GenderlessItems.GENDERLESS_PATCH.get(), GenericPatchItemRenderer::new);
            CuriosRendererRegistry.register(GenderlessItems.GENDERFLUID_PATCH.get(), GenericPatchItemRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BraOfHoldingItem.Renderer.LAYER, () -> LayerDefinition.create(BraOfHoldingItem.Renderer.mesh(), 1, 1));
        }

        @SubscribeEvent
        public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("overlay", GenderlessOverlayUtils.OVERLAY);
        }
    }
}