package com.tiomadre.foragersinsight.common.worldgen.trees.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.tiomadre.foragersinsight.common.block.WallMushroomBlock;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FITreeDecoratorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WallMushroomTreeDecorator extends TreeDecorator {
    public static final MapCodec<WallMushroomTreeDecorator> CODEC = MapCodec.unit(WallMushroomTreeDecorator::new);

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return FITreeDecoratorTypes.WALL_MUSHROOM_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        if (logs.size() < 2) {
            return;
        }

        List<BlockPos> candidates = new ArrayList<>(logs.subList(1, logs.size()));
        while (!candidates.isEmpty()) {
            BlockPos supportPos = candidates.remove(random.nextInt(candidates.size()));
            List<Direction> directions = new ArrayList<>(Direction.Plane.HORIZONTAL.stream().toList());

            while (!directions.isEmpty()) {
                Direction direction = directions.remove(random.nextInt(directions.size()));
                BlockPos mushroomPos = supportPos.relative(direction);
                if (!context.isAir(mushroomPos)) {
                    continue;
                }

                BlockState mushroom = (random.nextBoolean()
                        ? FIBlocks.WALL_BROWN_MUSHROOM.get()
                        : FIBlocks.WALL_RED_MUSHROOM.get())
                        .defaultBlockState()
                        .setValue(WallMushroomBlock.FACING, direction);
                context.setBlock(mushroomPos, mushroom);
                return;
            }
        }
    }
}