package net.dakotapride.creategenderless.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.dakotapride.creategenderless.CreateGenderlessMod;
import net.dakotapride.creategenderless.registry.CreateGenderlessBlocks;
import net.dakotapride.creategenderless.registry.CreateGenderlessItems;
import net.dakotapride.creategenderless.registry.CreateGenderlessVillagers;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = CreateGenderlessMod.MOD_ID)
public class WeavemasterTradesEvent {
    // Rosary : Geo
    // 1      : 2
    // 5      : 10
    // 15     : 30

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == CreateGenderlessVillagers.WEAVEMASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(CreateGenderlessItems.GILDED_ROSARY, 1),
                    new ItemStack(CreateGenderlessItems.SHELLROCK_RUBBLE, 2),
                    2, 8, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(CreateGenderlessItems.STEEL_ROSARY, 1),
                    new ItemStack(CreateGenderlessItems.SHELLROCK_RUBBLE, 10),
                    2, 8, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(CreateGenderlessItems.PALE_ROSARY, 1),
                    new ItemStack(CreateGenderlessItems.SHELLROCK_RUBBLE, 30),
                    2, 8, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(CreateGenderlessItems.STEEL_ROSARY, 4),
                    new ItemStack(CreateGenderlessItems.SILK, 1),
                    2, 8, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(CreateGenderlessItems.PALE_ROSARY, 64),
                    new ItemStack(CreateGenderlessItems.PALE_ROSARY, 56),
                    new ItemStack(CreateGenderlessBlocks.LUMARFLY_LANTERN.asItem(), 1),
                    2, 8, 0.02f));
        }
    }
}
