package com.doltandtio.foragersinsight.common.worldgen.trees.decorator;

import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FITreeDecoratorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SappyBirchLogDecorator extends TreeDecorator {
    public static final Codec<SappyBirchLogDecorator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("probability").forGetter(d -> d.probability)
            ).apply(instance, SappyBirchLogDecorator::new));

    private final float probability;

    public SappyBirchLogDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return FITreeDecoratorTypes.SAPPY_BIRCH_LOG_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        if (random.nextFloat() >= this.probability) {
            return;
        }

        List<BlockPos> logs = context.logs();
        if (logs.isEmpty()) return;

        Map<BlockPos, List<BlockPos>> columns = new HashMap<>();
        for (BlockPos pos : logs) {
            BlockPos columnPos = new BlockPos(pos.getX(), 0, pos.getZ());
            columns.computeIfAbsent(columnPos, ignored -> new ArrayList<>()).add(pos);
        }

        List<BlockPos> trunkColumn = columns.values().stream()
                .max(Comparator.comparingInt(List::size))
                .orElse(null);

        if (trunkColumn == null || trunkColumn.size() <= 1) return;

        trunkColumn.sort(Comparator.comparingInt(BlockPos::getY));
        BlockPos base = trunkColumn.get(0);

        List<BlockPos> trunk = new ArrayList<>();
        for (BlockPos pos : trunkColumn) {
            if (pos.getY() > base.getY()) {
                trunk.add(pos);
                if (trunk.size() >= 2) {
                    break;
                }
            }
        }

        if (trunk.isEmpty()) return;
        BlockPos chosen = trunk.get(random.nextInt(trunk.size()));
        BlockState state = FIBlocks.SAPPY_BIRCH_LOG.get().defaultBlockState();
        context.setBlock(chosen, state);
    }
}