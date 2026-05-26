package net.dakotapride.creategenderless.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public enum LeafkinPetalVariant {
    BASIC(0),
    RED(1, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2)),
    BLUE(2, new MobEffectInstance(MobEffects.BLINDNESS, 200, 2)),
    PINK(3, new MobEffectInstance(MobEffects.REGENERATION, 200, 2)),

    ;

    private static final LeafkinPetalVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(LeafkinPetalVariant::getId)).toArray(LeafkinPetalVariant[]::new);
    private final int id;
    private MobEffectInstance instance;

    LeafkinPetalVariant(int id) {
        this.id = id;
    }

    LeafkinPetalVariant(int id, MobEffectInstance instance) {
        this.id = id;
        this.instance = instance;
    }

    public String varName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public int getId() {
        return this.id;
    }

    public MobEffectInstance getMobEffectInstance() {
        return instance;
    }

    public static LeafkinPetalVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
}