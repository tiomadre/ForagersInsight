package com.doltandtio.foragersinsight.core.registry;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FIConfig {
    public static class Common {
        @ConfigKey("config")
        public final ForgeConfigSpec.DoubleValue chanceToGrowBountifulTree;
        public final ForgeConfigSpec.DoubleValue chanceToGrowSappyBirch;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Bountiful Trees");
            this.chanceToGrowBountifulTree = builder.comment("Chance for a tree's vanilla saplings to grow into its bountiful version. -1 to disable")
                    .defineInRange("Bountiful Mutations", 0.025d, -1.5, 1); //Default 2.5% Chance
            builder.pop();

            builder.push("Sappy Birch");
            this.chanceToGrowSappyBirch = builder.comment("Chance for birch saplings to grow into Sappy Variant. -1 to disable")
                    .defineInRange("Sappy Birch Mutations", 0.25d, -1.5, 1); //Default 25% chance
            builder.pop();
        }
    }
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static boolean shouldGrowBountifulTree(RandomSource rand) {
        double chance = COMMON.chanceToGrowBountifulTree.get();
        if (chance < 0) return false;
        return rand.nextDouble() < chance;
    }

    public static boolean shouldGrowSappyBirch(RandomSource rand) {
        double chance = COMMON.chanceToGrowSappyBirch.get();
        if (chance < 0) return false;
        return rand.nextDouble() < chance;
    }
}