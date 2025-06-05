package net.dakotapride.genderless.init;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.utils.registry.EstrogenFluidEntry;
import dev.mayaqq.estrogen.utils.registry.EstrogenFluidRegistrar;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.dakotapride.genderless.CreateGenderlessMod;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class BotariumGenderlessFluids {


    public static final EstrogenFluidRegistrar FLUIDS = EstrogenFluidRegistrar.create(CreateGenderlessMod.REGISTRIES);
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> BINARY_FLUID = FLUIDS.entry("binary_fluid",
                    BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .properties((p) -> p
                    .still(CreateGenderlessMod.asResource("fluid/binary_fluid_still"))
                    .flowing(CreateGenderlessMod.asResource("fluid/binary_fluid_flow"))
                    .overlay(CreateGenderlessMod.asResource("fluid/binary_fluid_flow"))
                    .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
                    .canConvertToSource(false)
                    .canDrown(true)
                    .canExtinguish(true)
                    .canHydrate(true)
                    .canPushEntity(true)
                    .canSwim(true)
                    .viscosity(1500)
                    .density(1500))
            .renderType(() -> RenderType::translucent)
            .block(BotariumLiquidBlock::new)
            .copyProperties(() -> Blocks.WATER)
            .properties((p) -> p.mapColor(MapColor.TERRACOTTA_BLUE))
            .build()
            .bucket(FluidBucketItem::new)
            .properties(p -> p.craftRemainder(Items.BUCKET).stacksTo(1)).build().register();
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> NON_BINARY_FLUID = FLUIDS.entry("non_binary_fluid",
                    BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .properties((p) -> p
                    .still(CreateGenderlessMod.asResource("fluid/non_binary_fluid_still"))
                    .flowing(CreateGenderlessMod.asResource("fluid/non_binary_fluid_flow"))
                    .overlay(CreateGenderlessMod.asResource("fluid/non_binary_fluid_flow"))
                    .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
                    .canConvertToSource(false)
                    .canDrown(true)
                    .canExtinguish(true)
                    .canHydrate(true)
                    .canPushEntity(true)
                    .canSwim(true)
                    .viscosity(1500)
                    .density(1500))
            .renderType(() -> RenderType::translucent)
            .block(BotariumLiquidBlock::new)
            .copyProperties(() -> Blocks.WATER)
            .properties((p) -> p.mapColor(MapColor.TERRACOTTA_PURPLE))
            .build()
            .bucket(FluidBucketItem::new)
            .properties(p -> p.craftRemainder(Items.BUCKET).stacksTo(1)).build().register();
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> ZERO_ONE_ZERO_ZERO_ZERO_ONE_ZERO_ONE = FLUIDS.entry("01000101",
                    BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .properties((p) -> p
                    .still(CreateGenderlessMod.asResource("fluid/01000101_still"))
                    .flowing(CreateGenderlessMod.asResource("fluid/01000101_flow"))
                    .overlay(CreateGenderlessMod.asResource("fluid/01000101_flow"))
                    .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
                    .canConvertToSource(false)
                    .canDrown(true)
                    .canExtinguish(true)
                    .canHydrate(true)
                    .canPushEntity(true)
                    .canSwim(true)
                    .viscosity(1500)
                    .density(1500))
            .renderType(() -> RenderType::translucent)
            .block(BotariumLiquidBlock::new)
            .copyProperties(() -> Blocks.WATER)
            .properties((p) -> p.mapColor(MapColor.COLOR_BLACK))
            .build()
            .bucket(FluidBucketItem::new)
            .properties(p -> p.craftRemainder(Items.BUCKET).stacksTo(1)).build().register();
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> VOID = FLUIDS.entry("void",
                    BotariumSourceFluid::new, BotariumFlowingFluid::new)
            .properties((p) -> p
                    .still(CreateGenderlessMod.asResource("fluid/void_still"))
                    .flowing(CreateGenderlessMod.asResource("fluid/void_flow"))
                    .overlay(CreateGenderlessMod.asResource("fluid/void_flow"))
                    .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
                    .canConvertToSource(false)
                    .canDrown(true)
                    .canExtinguish(true)
                    .canHydrate(true)
                    .canPushEntity(true)
                    .canSwim(true)
                    .viscosity(1500)
                    .density(1500))
            .renderType(() -> RenderType::translucent)
            .block(BotariumLiquidBlock::new)
            .copyProperties(() -> Blocks.WATER)
            .properties((p) -> p.mapColor(MapColor.COLOR_BLACK))
            .build()
            .bucket(FluidBucketItem::new)
            .properties(p -> p.craftRemainder(Items.BUCKET).stacksTo(1)).build().register();
}
