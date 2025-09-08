package com.doltandtio.foragersinsight.common.block;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class RoselleCropBlock extends RoseCropBlock {
    public RoselleCropBlock(Properties props, int isDoubleAfterAge) {
        super(props, isDoubleAfterAge);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return FIItems.ROSELLE_CALYX.get();
    }
}