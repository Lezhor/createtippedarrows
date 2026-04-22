package com.lezhor.createtippedarrows;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue TIPPED_ARROW_REQUIRED_FILL_AMOUNT = BUILDER
            .comment("The amount of liquid potion (in mB) required to tip a single arrow.")
            .defineInRange("spoutFillAmount", 25, 1, 1000);

    static final ModConfigSpec SPEC = BUILDER.build();
}
