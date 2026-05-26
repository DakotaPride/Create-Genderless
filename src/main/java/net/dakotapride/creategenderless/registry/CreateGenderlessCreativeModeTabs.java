package net.dakotapride.creategenderless.registry;

import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreateGenderlessCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateGenderlessMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("creative_mode_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.creategenderless.creative_mode_tab"))
                    .icon(CreateGenderlessItems.GENDERLESS_PILL::asStack)
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(CreateGenderlessItems.BASIC_CREST);
                        output.accept(CreateGenderlessItems.PYROMANIAC_CREST);
                        output.accept(CreateGenderlessItems.MUCKED_CREST);
                        output.accept(CreateGenderlessItems.FAITHFUL_CREST);

                        output.accept(CreateGenderlessItems.MASK);
                        output.accept(CreateGenderlessItems.NEEDLE);

                        output.accept(CreateGenderlessItems.GENDERLESS_PILL);
                        output.accept(CreateGenderlessItems.ENLIGHTENED_PILL);
                        output.accept(CreateGenderlessItems.VOID_PILL);
                        output.accept(CreateGenderlessItems.NULLSTEROGEN_MALT_BALLS);
                        output.accept(CreateGenderlessItems.GENDERSLIME_BALL);
                        output.accept(CreateGenderlessItems.SHELLROCK_RUBBLE);
                        output.accept(CreateGenderlessItems.GILDED_ROSARY);
                        output.accept(CreateGenderlessItems.STEEL_ROSARY);
                        output.accept(CreateGenderlessItems.PALE_ROSARY);
                        output.accept(CreateGenderlessItems.LUMARFLY_BOTTLE);
                        output.accept(CreateGenderlessItems.LUMAR_IRON_INGOT);
                        output.accept(CreateGenderlessItems.LUMAR_IRON_NUGGET);
                        output.accept(CreateGenderlessBlocks.LUMAR_IRON_BLOCK);
                        output.accept(CreateGenderlessBlocks.LUMARFLY_LANTERN);
                        output.accept(CreateGenderlessBlocks.GENDERSLIME_BLOCK);
                        output.accept(CreateGenderlessBlocks.GENDERSLIME_BRICKS);
                        output.accept(CreateGenderlessBlocks.BINARY_BLOCK);
                        output.accept(CreateGenderlessFluids.NULLSTEROGEN.get().getBucket());
                        output.accept(CreateGenderlessFluids.CONDENSED_VOID.get().getBucket());
                        output.accept(CreateGenderlessFluids.DILUTED_SOUL.get().getBucket());
                        output.accept(tippedArrow(CreateGenderlessPotions.ENBY_POWER_POTION.get()));
                        output.accept(CreateGenderlessItems.LUMARFLY_SPAWN_EGG);
                        output.accept(CreateGenderlessItems.LEAFKIN_SPAWN_EGG);
                        output.accept(CreateGenderlessItems.LEAFKIN_SLABBER_SPAWN_EGG);
                        output.accept(CreateGenderlessItems.SILK);

                        output.accept(CreateGenderlessBlocks.SHELLROCK.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK_STAIRS.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK_SLAB.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK_WALL.get());
                        output.accept(CreateGenderlessBlocks.POLISHED_CUT_SHELLROCK.get());
                        output.accept(CreateGenderlessBlocks.POLISHED_CUT_SHELLROCK_STAIRS.get());
                        output.accept(CreateGenderlessBlocks.POLISHED_CUT_SHELLROCK_SLAB.get());
                        output.accept(CreateGenderlessBlocks.POLISHED_CUT_SHELLROCK_WALL.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK_BRICK_STAIRS.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK_BRICK_SLAB.get());
                        output.accept(CreateGenderlessBlocks.CUT_SHELLROCK_BRICK_WALL.get());
                        output.accept(CreateGenderlessBlocks.SMALL_SHELLROCK_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.SMALL_SHELLROCK_BRICK_STAIRS.get());
                        output.accept(CreateGenderlessBlocks.SMALL_SHELLROCK_BRICK_SLAB.get());
                        output.accept(CreateGenderlessBlocks.SMALL_SHELLROCK_BRICK_WALL.get());
                        output.accept(CreateGenderlessBlocks.LAYERED_SHELLROCK.get());
                        output.accept(CreateGenderlessBlocks.SHELLROCK_PILLAR.get());
                        output.accept(CreateGenderlessBlocks.GILDED_ROSARY_BLOCK.get());
                        output.accept(CreateGenderlessBlocks.GILDED_ROSARY_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.STEEL_ROSARY_BLOCK.get());
                        output.accept(CreateGenderlessBlocks.STEEL_ROSARY_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.PALE_ROSARY_BLOCK.get());
                        output.accept(CreateGenderlessBlocks.PALE_ROSARY_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.SILK_BLOCK.get());
                        output.accept(CreateGenderlessBlocks.SILKY_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.SILK_SPOOL.get());
                        output.accept(CreateGenderlessBlocks.ORDAINED_CHALICE_GOLD_BLOCK.get());
                        output.accept(CreateGenderlessBlocks.ORDAINED_CHALICE_GOLD_TRAPDOOR.get());
                        output.accept(CreateGenderlessBlocks.HONED_STEEL_BLOCK.get());
                        output.accept(CreateGenderlessBlocks.HONED_STEEL_TRAPDOOR.get());
                        output.accept(CreateGenderlessBlocks.DECAYING_LOG.get());
                        output.accept(CreateGenderlessBlocks.DECAYED_WOODEN_PLANKS.get());
                        output.accept(CreateGenderlessBlocks.ROYAL_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.CRACKED_ROYAL_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.GILDED_ROYAL_BRICKS.get());
                        output.accept(CreateGenderlessBlocks.OVERGROWN_ROYAL_BRICKS.get());
                    })
                    .build());

    public static ItemStack tippedArrow(Potion potion) {
        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.setPotion(stack, potion);
        return stack;
    }

    public static void transRights(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
