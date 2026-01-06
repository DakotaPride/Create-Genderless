package net.dakotapride.creategenderless.registry;

import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class CreateGenderlessBlocks {
    public static final BlockEntry<Block> GENDERSLIME_BLOCK = CreateGenderlessMod.registrate().block("genderslime_block", Block::new)
            .addLayer(() -> RenderType::translucent)
            .initialProperties(() -> Blocks.SLIME_BLOCK)
            .properties(properties -> properties.mapColor(MapColor.COLOR_CYAN))
            //.properties(BlockBehaviour.Properties::noOcclusion)
            .simpleItem().register();
    public static final BlockEntry<Block> GENDERSLIME_BRICKS = CreateGenderlessMod.registrate().block("genderslime_bricks", Block::new)
            .addLayer(() -> RenderType::translucent)
            .initialProperties(() -> Blocks.SLIME_BLOCK).properties(properties -> properties.mapColor(MapColor.COLOR_CYAN))
            //.properties(BlockBehaviour.Properties::noOcclusion)
            .simpleItem().register();
    public static final BlockEntry<Block> BINARY_BLOCK = CreateGenderlessMod.registrate().block("binary_block", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE).properties(properties -> properties.mapColor(MapColor.TERRACOTTA_GREEN))
            .simpleItem().register();
    public static final BlockEntry<Block> LUMAR_IRON_BLOCK = CreateGenderlessMod.registrate().block("lumar_iron_block", Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK).properties(properties -> properties.mapColor(MapColor.WOOL))
            .simpleItem().register();
    public static final BlockEntry<LanternBlock> LUMARFLY_LANTERN = CreateGenderlessMod.registrate().block("lumarfly_lantern", LanternBlock::new)
            .addLayer(() -> RenderType::translucent)
            .initialProperties(() -> Blocks.LANTERN).properties(properties -> properties.mapColor(MapColor.WOOL))
            .simpleItem().register();

    public static final BlockEntry<Block> LAYERED_SHELLROCK = CreateGenderlessMod.registrate().block("layered_shellrock", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .onRegister(CreateRegistrate.blockModel(() -> CreateGenderlessSpriteShifts.LAYERED_SHELLROCK_PROVIDER))
            .simpleItem()
            .register();
    public static final BlockEntry<ConnectedPillarBlock> SHELLROCK_PILLAR = CreateGenderlessMod.registrate().block("shellrock_pillar", ConnectedPillarBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .onRegister(CreateRegistrate.blockModel(() -> CreateGenderlessSpriteShifts.SHELLROCK_PILLAR_PROVIDER))
            .simpleItem()
            .register();
    public static final BlockEntry<Block> SHELLROCK = CreateGenderlessMod.registrate().block("shellrock", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> CUT_SHELLROCK = CreateGenderlessMod.registrate().block("cut_shellrock", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> CUT_SHELLROCK_STAIRS = CreateGenderlessMod.registrate().block("cut_shellrock_stairs", p -> new StairBlock(Blocks.STONE_STAIRS::defaultBlockState, p))
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> CUT_SHELLROCK_SLAB = CreateGenderlessMod.registrate().block("cut_shellrock_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<WallBlock> CUT_SHELLROCK_WALL = CreateGenderlessMod.registrate().block("cut_shellrock_wall", WallBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> POLISHED_CUT_SHELLROCK = CreateGenderlessMod.registrate().block("polished_cut_shellrock", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> POLISHED_CUT_SHELLROCK_STAIRS = CreateGenderlessMod.registrate().block("polished_cut_shellrock_stairs", p -> new StairBlock(Blocks.STONE_STAIRS::defaultBlockState, p))
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> POLISHED_CUT_SHELLROCK_SLAB = CreateGenderlessMod.registrate().block("polished_cut_shellrock_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<WallBlock> POLISHED_CUT_SHELLROCK_WALL = CreateGenderlessMod.registrate().block("polished_cut_shellrock_wall", WallBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> CUT_SHELLROCK_BRICKS = CreateGenderlessMod.registrate().block("cut_shellrock_bricks", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> CUT_SHELLROCK_BRICK_STAIRS = CreateGenderlessMod.registrate().block("cut_shellrock_brick_stairs", p -> new StairBlock(Blocks.STONE_STAIRS::defaultBlockState, p))
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> CUT_SHELLROCK_BRICK_SLAB = CreateGenderlessMod.registrate().block("cut_shellrock_brick_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<WallBlock> CUT_SHELLROCK_BRICK_WALL = CreateGenderlessMod.registrate().block("cut_shellrock_brick_wall", WallBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> SMALL_SHELLROCK_BRICKS = CreateGenderlessMod.registrate().block("small_shellrock_bricks", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> SMALL_SHELLROCK_BRICK_STAIRS = CreateGenderlessMod.registrate().block("small_shellrock_brick_stairs", p -> new StairBlock(Blocks.STONE_STAIRS::defaultBlockState, p))
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> SMALL_SHELLROCK_BRICK_SLAB = CreateGenderlessMod.registrate().block("small_shellrock_brick_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();
    public static final BlockEntry<WallBlock> SMALL_SHELLROCK_BRICK_WALL = CreateGenderlessMod.registrate().block("small_shellrock_brick_wall", WallBlock::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .simpleItem()
            .register();

    public static void transRights() {}
}
