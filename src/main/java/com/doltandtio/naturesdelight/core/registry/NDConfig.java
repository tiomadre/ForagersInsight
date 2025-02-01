package com.doltandtio.naturesdelight.core.registry;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NDConfig {
    public static class Common {
        @ConfigKey("config")
        public final ForgeConfigSpec.DoubleValue chanceToGrowBountifulTree;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Bountiful Trees");
            this.chanceToGrowBountifulTree = builder.comment("Chance for a tree's vanilla saplings to grow into its bountiful version. -1 to disable")
                    .defineInRange("Bountiful Mutations", 0.025d, -1.5, 1);
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
}
