package net.dakotapride.genderless.init;


import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstancingController;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.blocks.fluids.LavaLikeLiquidBlock;
import dev.mayaqq.estrogen.utils.registry.EstrogenFluidBuilder;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import uwu.serenity.critter.platform.PlatformUtils;
import uwu.serenity.critter.stdlib.blockEntities.BlockEntityBuilder;
import uwu.serenity.critter.stdlib.blocks.BlockBuilder;
import uwu.serenity.critter.stdlib.items.ItemBuilder;
import uwu.serenity.critter.utils.SafeSupplier;
import uwu.serenity.critter.utils.environment.Environment;

import java.util.function.*;

final class NonBinaries {
    private NonBinaries() throws UtilityClassException {
        throw new UtilityClassException();
    }

    static <B extends Block, P> UnaryOperator<BlockBuilder<B, P>> stressImpact(double impact) {
        return (b) -> {
            BlockStressDefaults.setDefaultImpact(b.getId(), impact);
            return b;
        };
    }

    static <I extends Item, P> UnaryOperator<ItemBuilder<I, P>> tooltip(Function<Item, TooltipModifier> tooltipFactory) {
        return (b) -> {
            TooltipModifier.REGISTRY.registerDeferred(b.getId(), tooltipFactory);
            return b;
        };
    }

    static <I extends Item, P> UnaryOperator<ItemBuilder<I, P>> standardTooltip() {
        return tooltip((item) -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE));
    }

    static <I extends Item, P> UnaryOperator<ItemBuilder<I, P>> potatoProjectile(Consumer<PotatoCannonProjectileType.Builder> consumer) {
        return (b) -> (ItemBuilder)b.onSetup((item) -> {
            PotatoCannonProjectileType.Builder builder = new PotatoCannonProjectileType.Builder(b.getId());
            consumer.accept(builder);
            builder.registerAndAssign(new ItemLike[]{item});
        });
    }

    static <I extends Item & ICurioItem, P> UnaryOperator<ItemBuilder<I, P>> curioRenderer(SafeSupplier<ICurioRenderer> rendererFactory) {
        return (b) -> {
            if (PlatformUtils.getEnvironment() == Environment.CLIENT) {
                b.onRegister((item) -> CuriosRendererRegistry.register(item, rendererFactory::getSafe));
            }

            b.onRegister((x$0) -> CuriosApi.registerCurio(x$0, x$0));
            return b;
        };
    }

    static <BE extends BlockEntity, P> UnaryOperator<BlockEntityBuilder<BE, P>> instance(Supplier<BiFunction<MaterialManager, BE, BlockEntityInstance<? super BE>>> instanceFactory, boolean alwaysRender) {
        return (b) -> {
            if (PlatformUtils.getEnvironment() == Environment.CLIENT) {
                b.onRegister((betype) -> InstancedRenderRegistry.configure(betype).factory((BiFunction)instanceFactory.get()).skipRender((be) -> !alwaysRender).apply());
            }

            return b;
        };
    }

    static <BE extends BlockEntity, P> UnaryOperator<BlockEntityBuilder<BE, P>> instanceController(SafeSupplier<BlockEntityInstancingController<? super BE>> instanceController) {
        return (b) -> {
            if (PlatformUtils.getEnvironment() == Environment.CLIENT) {
                b.onRegister((beType) -> InstancedRenderRegistry.setController(beType, (BlockEntityInstancingController)instanceController.getSafe()));
            }

            return b;
        };
    }

    static <F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> UnaryOperator<EstrogenFluidBuilder<F, F2, P>> lavaLikeFluid(MapColor mapColor, int tint) {
        return (b) -> (EstrogenFluidBuilder)b.properties((p) -> p.still(Estrogen.id("block/blank_lava/blank_lava_still")).flowing(Estrogen.id("block/blank_lava/blank_lava_flow")).overlay(Estrogen.id("block/blank_lava/blank_lava_flow")).screenOverlay(new ResourceLocation("textures/misc/underwater.png")).tintColor(tint).temperature(10000).canConvertToSource(false).canDrown(false).canExtinguish(false).canHydrate(false).canPushEntity(true).canSwim(false).lightLevel(15).motionScale(0.004).pathType(BlockPathTypes.LAVA).tickRate(10).viscosity(1500).density(1500)).block(LavaLikeLiquidBlock::new).copyProperties(() -> Blocks.LAVA).properties((p) -> p.mapColor(mapColor)).build();
    }

    static <F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> UnaryOperator<EstrogenFluidBuilder<F, F2, P>> waterLikeFluid(MapColor mapColor, int tintColor) {
        return (b) -> (EstrogenFluidBuilder)b.properties((p) -> p.still(new ResourceLocation("minecraft", "block/water_still")).flowing(new ResourceLocation("minecraft", "block/water_flow")).overlay(new ResourceLocation("minecraft", "block/water_flow")).screenOverlay(new ResourceLocation("textures/misc/underwater.png")).tintColor(tintColor).canConvertToSource(false).canDrown(true).canExtinguish(true).canHydrate(true).canPushEntity(true).canSwim(true).viscosity(1500).density(1500)).renderType(() -> RenderType::translucent).block(BotariumLiquidBlock::new).copyProperties(() -> Blocks.WATER).properties((p) -> p.mapColor(mapColor)).build();
    }

    static <F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> UnaryOperator<EstrogenFluidBuilder<F, F2, P>> simpleBucket() {
        return (b) -> (EstrogenFluidBuilder)b.bucket(FluidBucketItem::new).properties(EstrogenItems::bucketProperties).build();
    }
}