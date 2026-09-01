package com.tiomadre.foragersinsight.common.worldgen.trees.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.common.block.SpruceTipBlock;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FITreeDecoratorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BountifulSpruceTipDecorator extends TreeDecorator {
    private final int count;

    public static final MapCodec<BountifulSpruceTipDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.INT.fieldOf("count").forGetter(decorator -> decorator.count)
            ).apply(instance, BountifulSpruceTipDecorator::new)
    );



    public BountifulSpruceTipDecorator(int count) {
        this.count = count;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return FITreeDecoratorTypes.BOUNTIFUL_SPRUCE_TIP_DECORATOR.get();
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

        Set<BlockPos> occupiedByTree = new HashSet<>(context.logs());
        occupiedByTree.addAll(leaves);

        BlockState tipState = FIBlocks.BOUNTIFUL_SPRUCE_TIPS.get().defaultBlockState()
                .setValue(SpruceTipBlock.AGE, SpruceTipBlock.MAX_AGE);

        RandomSource random = context.random();
        List<BlockPos> candidates = new ArrayList<>();

        for (BlockPos leafPos : leaves) {
            BlockPos belowPos = leafPos.below();
            if (occupiedByTree.contains(belowPos)) {
                continue;
            }

            candidates.add(belowPos);
        }

        if (candidates.isEmpty()) {
            return;
        }

        int selections = Math.min(this.count, candidates.size());
        for (int i = 0; i < selections; i++) {
            int index = random.nextInt(candidates.size());
            BlockPos chosen = candidates.remove(index);
            context.setBlock(chosen, tipState);
        }
    }
}