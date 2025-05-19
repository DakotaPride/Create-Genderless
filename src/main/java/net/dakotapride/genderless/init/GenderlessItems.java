package net.dakotapride.genderless.init;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.dakotapride.genderless.armour.BraOfHoldingItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import static net.dakotapride.genderless.CreateGenderlessMod.REGISTRATE;

public class GenderlessItems {

    public static final ItemEntry<?>
            GENDERLESS_PILL = REGISTRATE.item("genderless_pill", Item::new)
            .properties(p -> p
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .effect(() -> new MobEffectInstance(
                                    GenderlessStatusEffects.GENDERLESS_POWER.get(),
                                    6000,
                                    0,
                                    false,
                                    false,
                                    true),
                                    1)
                            .fast().alwaysEat().build())
                    .rarity(Rarity.RARE))
//            .transform(potatoProjectile(b -> b.damage(3)
//                    .reloadTicks(8)
//                    .velocity(1.5f)
//                    .knockback(0.3f)
//                    .renderTumbling()
//                    .onEntityHit(EstrogenPotatoProjectiles.potion(EstrogenEffects.ESTROGEN_EFFECT.get(), 1, 200, true))))
            //.transform(standardTooltip())
            .register();
    public static final ItemEntry<?>
            GENDERFLUID_PILL = REGISTRATE.item("genderfluid_pill", Item::new)
            .properties(p -> p
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .effect(() -> new MobEffectInstance(
                                    GenderlessStatusEffects.GENDERFLUIDITY.get(),
                                    6000,
                                    0,
                                    false,
                                    false,
                                    true),
                                    1)
                            .fast().alwaysEat().build())
                    .rarity(Rarity.RARE))
//            .transform(potatoProjectile(b -> b.damage(3)
//                    .reloadTicks(8)
//                    .velocity(1.5f)
//                    .knockback(0.3f)
//                    .renderTumbling()
//                    .onEntityHit(EstrogenPotatoProjectiles.potion(EstrogenEffects.ESTROGEN_EFFECT.get(), 1, 200, true))))
            //.transform(standardTooltip())
            .register();

    public static final ItemEntry<?> INCOMPLETE_CIRCUIT_BOARD = REGISTRATE.item("incomplete_circuit_board", SequencedAssemblyItem::new).register();
    public static final ItemEntry<?> CIRCUIT_BOARD = REGISTRATE.item("circuit_board", Item::new)
            .properties(p -> p.stacksTo(1)).register();
    public static final ItemEntry<?> BROKEN_CIRCUIT_BOARD = REGISTRATE.item("broken_circuit_board", Item::new)
            .properties(p -> p.food(new FoodProperties.Builder().alwaysEat()
                    .effect(() -> new MobEffectInstance(
                            GenderlessStatusEffects.ERROR.get(),
                            1200,
                            0,
                            false,
                            false,
                            true),
                            1.0F)
                    .build()))
            .register();

    // AND SO IT LIVES ON!!!!
    public static final ItemEntry<?> INCOMPLETE_BRA_OF_HOLDING = REGISTRATE.item("incomplete_bra_of_holding", SequencedAssemblyItem::new).register();
    public static final ItemEntry<BraOfHoldingItem> BRA_OF_HOLDING = REGISTRATE.item("bra_of_holding", BraOfHoldingItem::new)
            .onRegister(CreateRegistrate.itemModel(() -> BraOfHoldingItem.Model::new))
            .properties(p -> p.stacksTo(1)).register();

    public static void register() {}
}
