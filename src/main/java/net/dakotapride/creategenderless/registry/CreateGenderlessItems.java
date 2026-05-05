package net.dakotapride.creategenderless.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.crest.CrestType;
import net.dakotapride.creategenderless.item.BindingCrestItem;
import net.dakotapride.creategenderless.item.LumarflyBottleItem;
import net.dakotapride.creategenderless.item.NeedleItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class CreateGenderlessItems {
    public static final ItemEntry<Item> GENDERLESS_PILL = CreateGenderlessMod.registrate().item("genderless_pill", Item::new)
            .properties(p -> p.food(CreateGenderlessFoods.GENDERLESS_PILL).rarity(Rarity.RARE).stacksTo(16))
            .onRegister(s -> ItemDescription.useKey(s, "text.creategenderless.temp_enby_power")).register();
    public static final ItemEntry<Item> VOID_PILL = CreateGenderlessMod.registrate().item("void_pill", Item::new)
            .properties(p -> p.food(CreateGenderlessFoods.VOID_PILL).rarity(Rarity.RARE).stacksTo(16))
            .onRegister(s -> ItemDescription.useKey(s, "text.creategenderless.temp_unified")).register();
    public static final ItemEntry<Item> NULLSTEROGEN_MALT_BALLS = CreateGenderlessMod.registrate().item("nullsterogen_malt_balls", Item::new)
            .properties(p -> p.food(CreateGenderlessFoods.MALT_BALLS).rarity(Rarity.RARE))
            .onRegister(s -> ItemDescription.useKey(s, "text.creategenderless.temp_enby_power")).register();
    public static final ItemEntry<Item> GENDERSLIME_BALL = CreateGenderlessMod.registrate().item("genderslime_ball", Item::new).register();

    public static final ItemEntry<LumarflyBottleItem> LUMARFLY_BOTTLE = CreateGenderlessMod.registrate().item("lumarfly_bottle", LumarflyBottleItem::new)
            .onRegister(s -> ItemDescription.useKey(s, "item.creategenderless.lumarfly_bottle")).register();
    public static final ItemEntry<Item> LUMAR_IRON_INGOT = CreateGenderlessMod.registrate().item("lumar_iron_ingot", Item::new).register();
    public static final ItemEntry<Item> LUMAR_IRON_NUGGET = CreateGenderlessMod.registrate().item("lumar_iron_nugget", Item::new).register();

    public static final ItemEntry<Item> ENLIGHTENED_PILL = CreateGenderlessMod.registrate().item("enlightened_pill", Item::new)
            .properties(p -> p.food(CreateGenderlessFoods.ENLIGHTENED_PILL).rarity(Rarity.EPIC).stacksTo(16))
            .onRegister(s -> ItemDescription.useKey(s, "text.creategenderless.temp_enby_power")).register();

    public static final ItemEntry<Item> SILK = CreateGenderlessMod.registrate().item("silk", Item::new).register();

    public static final ItemEntry<Item> SHELLROCK_RUBBLE = CreateGenderlessMod.registrate().item("shellrock_rubble", Item::new).register();
    public static final ItemEntry<Item> GILDED_ROSARY = CreateGenderlessMod.registrate().item("gilded_rosary", Item::new).register();
    public static final ItemEntry<Item> STEEL_ROSARY = CreateGenderlessMod.registrate().item("steel_rosary", Item::new).register();
    public static final ItemEntry<Item> PALE_ROSARY = CreateGenderlessMod.registrate().item("pale_rosary", Item::new).register();

    public static final ItemEntry<BindingCrestItem> BASIC_CREST = createCrestItemEntry(CrestType.BASIC);
    public static final ItemEntry<BindingCrestItem> PYROMANIAC_CREST = createCrestItemEntry(CrestType.PYROMANIAC);
    public static final ItemEntry<BindingCrestItem> MUCKED_CREST = createCrestItemEntry(CrestType.MUCKED);
    public static final ItemEntry<BindingCrestItem> FAITHFUL_CREST = createCrestItemEntry(CrestType.FAITHFUL);

    public static final ItemEntry<NeedleItem> NEEDLE = CreateGenderlessMod.registrate().item("needle", properties -> new NeedleItem(Tiers.IRON, 3, -2.4F, properties)).register();
    public static final ItemEntry<ArmorItem> MASK = CreateGenderlessMod.registrate().item("mask", properties -> new ArmorItem(CreateGenderlessArmorMaterials.MASK, ArmorItem.Type.HELMET, properties)).register();

    public static final ItemEntry<Item> MASK_FRAGMENT = CreateGenderlessMod.registrate().item("mask_fragment", Item::new).register();

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_ENLIGHTENED_PILL = CreateGenderlessMod.registrate().item("incomplete_enlightened_pill", SequencedAssemblyItem::new).register();

    public static final ItemEntry<ForgeSpawnEggItem> LUMARFLY_SPAWN_EGG = CreateGenderlessMod.registrate().item("lumarfly_spawn_egg",
            properties -> new ForgeSpawnEggItem(CreateGenderlessEntityTypes.LUMARFLY, 0xFCFEFF, 0xC9CCE4, properties)).register();

    private static ItemEntry<BindingCrestItem> createCrestItemEntry(CrestType type) {
        return CreateGenderlessMod.registrate().item(type.getId() + "_crest", properties -> new BindingCrestItem(type, properties)).register();
    }

    public static void transRights() {}
}
