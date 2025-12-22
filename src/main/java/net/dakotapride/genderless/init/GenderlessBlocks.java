package net.dakotapride.genderless.init;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.mayaqq.estrogen.content.EstrogenSoundTypes;
import net.dakotapride.genderless.block.PillBoxBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import static net.dakotapride.genderless.CreateGenderlessMod.REGISTRATE;

public class GenderlessBlocks {

    public static final BlockEntry<?> BINARY_BLOCK = REGISTRATE.block("binary_block", Block::new)
            .item().properties(p -> p.rarity(Rarity.RARE))
            .build()
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).lightLevel(t -> 15).destroyTime(10.0F))
            .register();
    public static final BlockEntry<?> GENDERLESS_PILL_BOX = REGISTRATE.block("genderless_pill_box", PillBoxBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties((p) -> p.strength(1.0F, 1.0F).sound(EstrogenSoundTypes.INSTANCE.getPILL_BOX()))
            .simpleItem()
            .register();
    public static final BlockEntry<?> GENDERFLUID_PILL_BOX = REGISTRATE.block("genderfluid_pill_box", PillBoxBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties((p) -> p.strength(1.0F, 1.0F).sound(EstrogenSoundTypes.INSTANCE.getPILL_BOX()))
            .item().properties(p -> p.rarity(Rarity.RARE)).build()
            .register();

    public static final BlockEntry<?> GENDERSLIME = REGISTRATE.block("genderslime_block", SlimeBlock::new)
            .item().properties(p -> p.rarity(Rarity.RARE))
            .build()
            .addLayer(() -> RenderType::translucent)
            .initialProperties(() -> Blocks.SLIME_BLOCK)
            .properties(p -> p.mapColor(MapColor.COLOR_PINK).noOcclusion())
            //.properties(p -> p.mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).lightLevel(t -> 15).destroyTime(10.0F))
            .register();

    public static void register() {}

}
