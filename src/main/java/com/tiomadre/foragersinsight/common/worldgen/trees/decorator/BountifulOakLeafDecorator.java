package com.tiomadre.foragersinsight.common.worldgen.trees.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.common.block.BountifulLeavesBlock;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FITreeDecoratorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BountifulOakLeafDecorator extends TreeDecorator {
    public static final MapCodec<BountifulOakLeafDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.INT.fieldOf("count").forGetter(decorator -> decorator.count)
            ).apply(instance, BountifulOakLeafDecorator::new));

    private final int count;

    public BountifulOakLeafDecorator(int count) {
        this.count = count;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return FITreeDecoratorTypes.BOUNTIFUL_OAK_LEAF_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        if (this.count <= 0) {
            return;
        }

        List<BlockPos> leaves = context.leaves();
        if (leaves.isEmpty()) {
            return;
        }

        BlockState bountifulState = FIBlocks.BOUNTIFUL_OAK_LEAVES.get().defaultBlockState();
        if (bountifulState.hasProperty(BountifulLeavesBlock.AGE)) {
            bountifulState = bountifulState.setValue(BountifulLeavesBlock.AGE, BountifulLeavesBlock.MAX_AGE);
        }

        RandomSource random = context.random();
        List<BlockPos> candidates = new ArrayList<>(leaves);
        int selections = Math.min(this.count, candidates.size());
        for (int i = 0; i < selections; i++) {
            int index = random.nextInt(candidates.size());
            BlockPos chosen = candidates.remove(index);
            context.setBlock(chosen, bountifulState);
        }
    }
}