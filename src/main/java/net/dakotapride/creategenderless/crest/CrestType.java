package net.dakotapride.creategenderless.crest;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public enum CrestType {
    BASIC("basic"),
    PYROMANIAC("pyromaniac"),
    MUCKED("mucked", CrestType.defaultBindTicks * 2),
    FAITHFUL("faithful", CrestType.defaultBindTicks * 4, CrestType.defaultHealAmount * 2),

    ;

    // actual default : 27 ticks
    private static final int defaultBindTicks = 27;
    // actual default : 6 health (3 hearts)
    private static final int defaultHealAmount = 6;

    final String id;
    final int bindTicks;
    final int healAmount;

    CrestType(String id) {
        this.id = id;
        this.bindTicks = defaultBindTicks;
        this.healAmount = defaultHealAmount;
    }

    CrestType(String id, int bindTicks) {
        this.id = id;
        this.bindTicks = bindTicks;
        this.healAmount = defaultHealAmount;
    }

    CrestType(String id, int bindTicks, int healAmount) {
        this.id = id;
        this.bindTicks = bindTicks;
        this.healAmount = healAmount;
    }

    public boolean checkCrestType(CrestType type) {
        return this == type;
    }

    public String getId() {
        return id;
    }

    public int getBindTicks() {
        return bindTicks;
    }

    public int getHealAmount() {
        return healAmount;
    }

    // WIP
    private static int pyromanicBindTicks() {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.level().dimension() == Level.NETHER)
                return defaultBindTicks / 2;
            else return defaultBindTicks;
        }
        return defaultBindTicks;
    }
}
