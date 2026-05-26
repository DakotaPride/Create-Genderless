package net.dakotapride.creategenderless.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public enum LeafkinVariant {
        MARSH(0),
        AUTUMN(1),
        SILK_TRAPPER(2),

        ;

        private static final LeafkinVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
                comparingInt(LeafkinVariant::getId)).toArray(LeafkinVariant[]::new);
        private final int id;

        LeafkinVariant(int id) {
            this.id = id;
        }

        public String varName() {
            return name().toLowerCase(Locale.ROOT);
        }

        public int getId() {
            return this.id;
        }

        public static LeafkinVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }