package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.common.block.BountifulLeavesBlock;
import com.doltandtio.foragersinsight.common.block.SpruceTipBlock;
import com.doltandtio.foragersinsight.common.block.TapperBlock;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID)
public class FarmingXPEvents {
    private static final Map<ResourceKey<Level>, Map<BlockPos, PendingForage>> PENDING_FORAGING_DROPS = new HashMap<>();
    private static final int FORAGING_DROP_TIMEOUT_TICKS = 20;

    @SubscribeEvent
    public static void onKnifeHarvest(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        if (!(event.getLevel() instanceof Level level) || level.isClientSide()) {
            return;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof KnifeItem)) {
            return;
        }

        BlockState state = event.getState();
        if (state.is(FITags.BlockTag.FORAGING)) {
            trackPotentialForageDrop(serverLevel, event.getPos(), state, player);
        }
    }

    @SubscribeEvent
    @SuppressWarnings("resource")
    public static void onForageDrop(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!(event.getEntity() instanceof ItemEntity itemEntity)) {
            return;
        }

        PendingForage pending = findPendingForage(serverLevel, itemEntity.blockPosition());
        if (pending == null) {
            return;
        }

        if (pending.player.level() != serverLevel) {
            return;
        }

        if (itemEntity.getItem().isEmpty()) {
            return;
        }

        awardKnifeXP(serverLevel, pending.player);
    }

    @SubscribeEvent
    public static void onCropHarvest(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        Level level = event.getLevel() instanceof Level ? (Level) event.getLevel() : null;
        if (level == null || level.isClientSide()) return;

        BlockState state = event.getState();
        if (state.getBlock() instanceof StemGrownBlock) {
            boolean connectedToStem = isConnectedToStem(level, event.getPos());
            int xp = connectedToStem ? 1 + player.getRandom().nextInt(2) : player.getRandom().nextInt(2);
            if (xp > 0) {
                ExperienceOrb.award((ServerLevel) level, player.position(), xp);
            }
            return;
        }

        Optional<IntegerProperty> agePropOpt = getAgeProperty(state);
        if (agePropOpt.isEmpty()) return;

        IntegerProperty ageProp = agePropOpt.get();
        int currentAge = state.getValue(ageProp);
        int maxAge = ageProp.getPossibleValues().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(currentAge);
        if (currentAge < maxAge) return;

        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals("half") &&
                    property instanceof net.minecraft.world.level.block.state.properties.EnumProperty<?> enumProp) {
                Comparable<?> halfValue = state.getValue(enumProp);
                if (halfValue instanceof DoubleBlockHalf half) {
                    BlockState counterpart = level.getBlockState(
                            half == DoubleBlockHalf.UPPER
                                    ? event.getPos().below()
                                    : event.getPos().above());
                    if (!counterpart.is(state.getBlock())) {
                        return;
                    }
                }
                break;
            }
        }

        int xp = 1 + player.getRandom().nextInt(2);
        ExperienceOrb.award((ServerLevel) level, player.position(), xp);
    }

    @SubscribeEvent
    public static void onRightClickHarvest(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Level level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) return;
        ItemStack held = player.getItemInHand(event.getHand());
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof BeehiveBlock) ||
                state.getValue(BeehiveBlock.HONEY_LEVEL) < 5 ||
                (!(held.getItem() instanceof ShearsItem) && !held.is(Items.GLASS_BOTTLE))) {
            if (block instanceof TapperBlock &&
                    state.getValue(TapperBlock.HAS_TAPPER) &&
                    state.getValue(TapperBlock.FILL) == 4 &&
                    held.is(Items.BUCKET)) {
                int xp = 1 + player.getRandom().nextInt(2);
                ExperienceOrb.award(serverLevel, player.position(), xp);
                return;
            }
            if (block instanceof MushroomColonyBlock) {
                int initialAge = state.getValue(MushroomColonyBlock.COLONY_AGE);
                if (initialAge > 0) {
                    serverLevel.getServer().execute(() -> {
                        BlockState updatedState = serverLevel.getBlockState(pos);
                        if (!(updatedState.getBlock() instanceof MushroomColonyBlock)) {
                            return;
                        }
                        int updatedAge = updatedState.getValue(MushroomColonyBlock.COLONY_AGE);
                        if (updatedAge < initialAge) {
                            int xp = player.getRandom().nextInt(2);
                            if (xp > 0) {
                                ExperienceOrb.award(serverLevel, player.position(), xp);
                            }
                        }
                    });
                    return;
                }
            }
            if (!(held.isEmpty() || held.getItem() instanceof ShearsItem)) {
                return;
            }
            state = level.getBlockState(pos);
            block = state.getBlock();
            if (block instanceof TomatoVineBlock tomatoVine) {
                int currentAge = state.getValue(TomatoVineBlock.VINE_AGE);
                if (currentAge < tomatoVine.getMaxAge()) {
                    return;
                }

                serverLevel.getServer().execute(() -> {
                    BlockState updatedState = serverLevel.getBlockState(pos);
                    if (!(updatedState.getBlock() instanceof TomatoVineBlock)) {
                        return;
                    }
                    if (updatedState.getValue(TomatoVineBlock.VINE_AGE) >= currentAge) {
                        return;
                    }

                    int xp = 1 + player.getRandom().nextInt(2);
                    ExperienceOrb.award(serverLevel, player.position(), xp);
                });
                return;
            }
            if (!(block instanceof BonemealableBlock) || block instanceof CropBlock) return;
            if (block instanceof StemBlock || block instanceof AttachedStemBlock) {
                return;
            }

            Optional<IntegerProperty> agePropOpt = getAgeProperty(state);
            if (agePropOpt.isEmpty()) return;

            IntegerProperty ageProp = agePropOpt.get();
            int currentAge = state.getValue(ageProp);
            int maxAge = ageProp.getPossibleValues().stream()
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(currentAge);
            if (currentAge < maxAge) return;

            BlockState initialState = state;
            serverLevel.getServer().execute(() -> {
                BlockState updatedState = serverLevel.getBlockState(pos);
                boolean harvested = false;
                if (!updatedState.is(initialState.getBlock())) {
                    harvested = true;
                } else if (updatedState.hasProperty(ageProp) && updatedState.getValue(ageProp) < currentAge) {
                    harvested = true;
                }

                if (!harvested) {
                    return;
                }

                int xp;
                if (initialState.getBlock() instanceof BountifulLeavesBlock || initialState.getBlock() instanceof SpruceTipBlock) {
                    xp = player.getRandom().nextInt(2);
                } else {
                    xp = 1 + player.getRandom().nextInt(2);
                }

                if (xp > 0) {
                    ExperienceOrb.award(serverLevel, player.position(), xp);
                }
            });
        } else {
            int xp = 2 + player.getRandom().nextInt(2);
            ExperienceOrb.award(serverLevel, player.position(), xp);
        }
    }

    @SubscribeEvent
    public static void onAnimalShear(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        ItemStack tool = player.getItemInHand(event.getHand());
        if (!(tool.getItem() instanceof ShearsItem)) return;

        if (event.getTarget() instanceof Sheep sheep) {
            if (sheep.isBaby() || sheep.isSheared()) return;
            int xp = 1 + player.getRandom().nextInt(2);
            ExperienceOrb.award((ServerLevel) level, player.position(), xp);
        } else if (event.getTarget() instanceof Chicken chicken) {
            if (chicken.isBaby()) return;
            long now = level.getGameTime();
            long last = chicken.getPersistentData().getLong("ShearFeatherTime");
            if (now - last < 2400L) return;
            int xp = 1 + player.getRandom().nextInt(2);
            ExperienceOrb.award((ServerLevel) level, player.position(), xp);
        }
    }

    private static void trackPotentialForageDrop(ServerLevel level, BlockPos pos, BlockState state, ServerPlayer player) {
        ResourceKey<Level> dimension = level.dimension();
        Map<BlockPos, PendingForage> entries = PENDING_FORAGING_DROPS.computeIfAbsent(dimension, ignored -> new HashMap<>());
        long now = level.getGameTime();
        cleanupExpiredEntries(entries, now);

        BlockPos trackedPos = adjustTrackedPosition(state, pos).immutable();
        entries.put(trackedPos, new PendingForage(player, now + FORAGING_DROP_TIMEOUT_TICKS));
    }

    private static PendingForage findPendingForage(ServerLevel level, BlockPos itemPos) {
        ResourceKey<Level> dimension = level.dimension();
        Map<BlockPos, PendingForage> entries = PENDING_FORAGING_DROPS.get(dimension);
        if (entries == null) {
            return null;
        }

        long now = level.getGameTime();
        cleanupExpiredEntries(entries, now);
        if (entries.isEmpty()) {
            PENDING_FORAGING_DROPS.remove(dimension);
            return null;
        }

        Iterator<Map.Entry<BlockPos, PendingForage>> iterator = entries.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<BlockPos, PendingForage> entry = iterator.next();
            if (isMatchingPosition(entry.getKey(), itemPos)) {
                PendingForage pending = entry.getValue();
                iterator.remove();
                if (entries.isEmpty()) {
                    PENDING_FORAGING_DROPS.remove(dimension);
                }
                if (pending.isExpired(now)) {
                    return null;
                }
                return pending;
            }
        }

        return null;
    }

    private static void cleanupExpiredEntries(Map<BlockPos, PendingForage> entries, long now) {
        entries.entrySet().removeIf(entry -> entry.getValue().isExpired(now));
    }

    private static boolean isMatchingPosition(BlockPos trackedPos, BlockPos itemPos) {
        if (trackedPos.equals(itemPos)) {
            return true;
        }
        if (trackedPos.equals(itemPos.below())) {
            return true;
        }
        return trackedPos.equals(itemPos.above());
    }

    private static BlockPos adjustTrackedPosition(BlockState state, BlockPos pos) {
        if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)
                && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
            return pos.below();
        }
        return pos;
    }

    private record PendingForage(ServerPlayer player, long expiryTick) {
        boolean isExpired(long now) {
            return now > expiryTick || !player.isAlive();
        }
    }

    private static void awardKnifeXP(ServerLevel level, ServerPlayer player) {
        int xp = player.getRandom().nextInt(2);
        if (xp > 0) {
            ExperienceOrb.award(level, player.position(), xp);
        }
    }

    private static Optional<IntegerProperty> getAgeProperty(BlockState state) {
        return state.getProperties().stream()
                .filter(p -> p.getName().equals("age") && p instanceof IntegerProperty)
                .map(p -> (IntegerProperty) p)
                .findFirst();
    }

    private static boolean isConnectedToStem(Level level, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            Block neighborBlock = neighborState.getBlock();

            if (neighborBlock instanceof StemBlock) {
                return true;
            }

            if (neighborBlock instanceof AttachedStemBlock) {
                if (neighborState.hasProperty(AttachedStemBlock.FACING)
                        && neighborState.getValue(AttachedStemBlock.FACING) == direction.getOpposite()) {
                    return true;
                }
            }
        }
        return false;
    }
}
