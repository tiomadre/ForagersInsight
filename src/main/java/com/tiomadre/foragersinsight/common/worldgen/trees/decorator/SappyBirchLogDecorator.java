package com.tiomadre.foragersinsight.common.worldgen.trees.decorator;

import com.mojang.serialization.MapCodec;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FITreeDecoratorTypes;
import com.tiomadre.foragersinsight.common.block.TinderConkBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
    public static final MapCodec<SappyBirchLogDecorator> CODEC = RecordCodecBuilder.mapCodec(
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
        placeSapSplotch(context, base, random);
        placeTinderConk(context, chosen, random);
    }

    private static void placeSapSplotch(Context context, BlockPos basePos, RandomSource random) {
        List<Direction> directions = new ArrayList<>(Direction.Plane.HORIZONTAL.stream().toList());
        boolean placedFirst = placeRandomSapSplotch(context, basePos, directions, random);

        if (!placedFirst) {
            return;
        }

        if (random.nextFloat() < 0.25F) {
            placeRandomSapSplotch(context, basePos, directions, random);
        }
    }

    private static boolean placeRandomSapSplotch(Context context, BlockPos basePos, List<Direction> directions, RandomSource random) {

        while (!directions.isEmpty()) {
            Direction direction = directions.remove(random.nextInt(directions.size()));
            BlockPos splotchPos = findSapSplotchPosition(context, basePos.relative(direction));

            if (splotchPos != null) {
                context.setBlock(
                        splotchPos,
                        FIBlocks.SAP_SPLOTCH.get().defaultBlockState()
                );
                return true;
            }
        }

        return false;

    }

    private static BlockPos findSapSplotchPosition(Context context, BlockPos targetPos) {
        for (int offset = 2; offset >= -2; offset--) {
            BlockPos pos = targetPos.above(offset);
            if (context.isAir(pos) && !context.isAir(pos.below())) {
                return pos;
            }
        }

        return null;
    }

    private static void placeTinderConk(Context context, BlockPos supportPos, RandomSource random) {
        List<Direction> directions = new ArrayList<>(Direction.Plane.HORIZONTAL.stream().toList());
        while (!directions.isEmpty()) {
            Direction direction = directions.remove(random.nextInt(directions.size()));
            BlockPos conkPos = supportPos.relative(direction);
            BlockState conkState = FIBlocks.TINDER_CONK.get().defaultBlockState().setValue(TinderConkBlock.FACING, direction);
            if (context.isAir(conkPos)) {
                context.setBlock(conkPos, conkState);
                return;
            }
        }
    }
}