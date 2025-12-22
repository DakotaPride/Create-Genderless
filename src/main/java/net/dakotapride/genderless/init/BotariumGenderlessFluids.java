package net.dakotapride.genderless.init;

import dev.mayaqq.estrogen.content.fluids.registry.EstrogenFluidEntry;
import dev.mayaqq.estrogen.content.fluids.registry.FluidBuilder;
import dev.mayaqq.estrogen.content.fluids.registry.FluidRegistryProvider;
import earth.terrarium.botarium.common.registry.fluid.*;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import net.dakotapride.genderless.CreateGenderlessMod;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import uwu.serenity.kritter.api.Registrar;

import java.lang.reflect.Proxy;
import java.util.function.Consumer;

import static net.dakotapride.genderless.CreateGenderlessMod.MOD_ID;
import static net.dakotapride.genderless.CreateGenderlessMod.REGISTRIES;

public class BotariumGenderlessFluids  implements  FluidRegistryProvider{

    @SuppressWarnings("unchecked")
    public static <T> Registrar<T> proxyRegistrar(Registrar<T> target) {
        return (Registrar<T>) Proxy.newProxyInstance(
                Registrar.class.getClassLoader(),
                new Class<?>[]{ Registrar.class , FluidRegistryProvider.class},
                (proxy, method, args) ->
                    {
                        if (method.getName().equals("getFluidRegistry"))
                        {
                            return FLUIDS;
                        }
                        return method.invoke(target, args);
                    }
                );
            }
    public static final Registrar<Fluid> FLUID_REGISTRAR = proxyRegistrar(REGISTRIES.rangeTo(Registries.FLUID));


    public static <S extends BotariumSourceFluid, F extends BotariumFlowingFluid>
    EstrogenFluidEntry<S, F> fluid(
            String name,
            Function1<FluidData, S> sourceFactory,
            Function1<FluidData, F> flowingFactory,
            Consumer<FluidBuilder<S, F>> builder
    ) {
        FluidBuilder<S, F> fluidBuilder =
                new FluidBuilder<>(
                        name,
                        FLUID_REGISTRAR,
                        FLUID_REGISTRAR.getCallback(),
                        sourceFactory,
                        flowingFactory,
                        FLUID_REGISTRAR.createResourceKey("flowing_" + name)
                );
        builder.accept(fluidBuilder);
        return (EstrogenFluidEntry<S, F>) fluidBuilder.register();
    }


    public static final FluidRegistry FLUIDS = new FluidRegistry(MOD_ID);
    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> BINARY_FLUID = fluid("binary_fluid",
            BotariumSourceFluid::new, BotariumFlowingFluid::new,
            builder -> {
                builder.properties((p) ->
                        {p.still(CreateGenderlessMod.asResource("fluid/binary_fluid_still"))
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
                                .density(1500);
                        return Unit.INSTANCE;
                    });

                builder.renderType(RenderType::translucent);
                builder.block(BotariumLiquidBlock::new,
                        "binary_fluid",
                        (blockBuilder)->
                        {
                            blockBuilder.copyProperties(()->Blocks.WATER);
                            blockBuilder.properties((p) -> {p.mapColor(MapColor.TERRACOTTA_BLUE); return Unit.INSTANCE;});
                            return Unit.INSTANCE;
                        }
                        );

                builder.bucket(FluidBucketItem::new, "binary_fluid",
                    (itemBuilder)->
                    {
                        itemBuilder.properties(p -> {p.craftRemainder(Items.BUCKET).stacksTo(1);
                            return Unit.INSTANCE;
                        });
                        return Unit.INSTANCE;
                    }
                );
            }
        );

    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> NON_BINARY_FLUID = fluid("non_binary_fluid",
            BotariumSourceFluid::new, BotariumFlowingFluid::new,
            builder -> {
                builder.properties((p) ->
                        {p.still(CreateGenderlessMod.asResource("fluid/non_binary_fluid_still"))
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
                                .density(1500);
                        return Unit.INSTANCE;
                    });

                builder.renderType(RenderType::translucent);
                builder.block(BotariumLiquidBlock::new,
                        "non_binary_fluid",
                        (blockBuilder)->
                        {
                            blockBuilder.copyProperties(()->Blocks.WATER);
                            blockBuilder.properties((p) -> {p.mapColor(MapColor.TERRACOTTA_PURPLE); return Unit.INSTANCE;});
                            return Unit.INSTANCE;
                        }
                        );

                builder.bucket(FluidBucketItem::new, "non_binary_fluid",
                    (itemBuilder)->
                    {
                        itemBuilder.properties(p -> {p.craftRemainder(Items.BUCKET).stacksTo(1);
                            return Unit.INSTANCE;
                        });
                        return Unit.INSTANCE;
                    }
                );
            }
        );

    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> ZERO_ONE_ZERO_ZERO_ZERO_ONE_ZERO_ONE = fluid("01000101",
            BotariumSourceFluid::new, BotariumFlowingFluid::new,
            builder -> {
                builder.properties((p) ->
                        {p.still(CreateGenderlessMod.asResource("fluid/01000101_still"))
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
                                .density(1500);
                        return Unit.INSTANCE;
                    });

                builder.renderType(RenderType::translucent);
                builder.block(BotariumLiquidBlock::new,
                        "01000101",
                        (blockBuilder)->
                        {
                            blockBuilder.copyProperties(()->Blocks.WATER);
                            blockBuilder.properties((p) -> {p.mapColor(MapColor.COLOR_BLACK); return Unit.INSTANCE;});
                            return Unit.INSTANCE;
                        }
                        );

                builder.bucket(FluidBucketItem::new, "01000101",
                    (itemBuilder)->
                    {
                        itemBuilder.properties(p -> {p.craftRemainder(Items.BUCKET).stacksTo(1);
                            return Unit.INSTANCE;
                        });
                        return Unit.INSTANCE;
                    }
                );
            }
        );



    public static final EstrogenFluidEntry<BotariumSourceFluid, BotariumFlowingFluid> VOID = fluid("void",
            BotariumSourceFluid::new, BotariumFlowingFluid::new,
            builder -> {
                builder.properties((p) ->
                        {p.still(CreateGenderlessMod.asResource("fluid/void_still"))
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
                                .density(1500);
                        return Unit.INSTANCE;
                    });

                builder.renderType(RenderType::translucent);
                builder.block(BotariumLiquidBlock::new,
                        "void",
                        (blockBuilder)->
                        {
                            blockBuilder.copyProperties(()->Blocks.WATER);
                            blockBuilder.properties((p) -> {p.mapColor(MapColor.COLOR_BLACK); return Unit.INSTANCE;});
                            return Unit.INSTANCE;
                        }
                        );

                builder.bucket(FluidBucketItem::new, "void",
                    (itemBuilder)->
                    {
                        itemBuilder.properties(p -> {p.craftRemainder(Items.BUCKET).stacksTo(1);
                            return Unit.INSTANCE;
                        });
                        return Unit.INSTANCE;
                    }
                );
            }
    );

    @Override
    public @NotNull FluidRegistry getFluidRegistry() {
        return FLUIDS;
    }
}