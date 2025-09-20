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
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.*;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID)
public class FarmingXPEvents {
    private static final Map<ResourceKey<Level>, Map<BlockPos, PendingForage>> PENDING_FORAGING_DROPS = new HashMap<>();
    private static final int FORAGING_DROP_TIMEOUT_TICKS = 20;

//Experience event for Farming and Foraging
    @SubscribeEvent
    public static void onKnifeHarvest(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        ItemStack tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof KnifeItem)) return;

        BlockState state = event.getState();
        if (state.is(FITags.BlockTag.FORAGING)) {
            trackPotentialForageDrop(level, event.getPos(), state, player);
        }
    }
    //Forage
    @SubscribeEvent
    public static void onForageDrop(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!(event.getEntity() instanceof ItemEntity item)) return;

        PendingForage pending = popPendingForageNear(level, item.blockPosition());
        if (pending == null) return;
        if (pending.player.level() != level) return;
        if (item.getItem().isEmpty()) return;

        awardRandomXP(level, pending.player, 0, 2); // 0–1 XP
    }

    @SubscribeEvent
    public static void onCropHarvest(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Block block = state.getBlock();

        //Gourds attached to Stems
        if (block instanceof StemGrownBlock) {
            boolean connectedToStem = isConnectedToStem(level, pos);
            awardRandomXP(level, player, connectedToStem ? 1 : 0, 2);
            return;
        }

        Optional<IntegerProperty> ageProp = getAgeProp(state);
        if (ageProp.isPresent() && isMature(state, ageProp.get())) {

            if (!isIntactDoublePlant(level, pos, state)) return;
            awardRandomXP(level, player, 1, 3); // 1–2
        }
    }

    @SubscribeEvent
    public static void onRightClickHarvest(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BlockPos pos = event.getPos();
        ItemStack held = player.getItemInHand(event.getHand());
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        // Beehive
        if (block instanceof BeehiveBlock &&
                state.getValue(BeehiveBlock.HONEY_LEVEL) >= 5 &&
                ((held.getItem() instanceof ShearsItem) || held.is(Items.GLASS_BOTTLE))) {
            awardRandomXP(level, player, 2, 4); // 2–3
            return;
        }

        // Tapper
        if (block instanceof TapperBlock &&
                state.getValue(TapperBlock.HAS_TAPPER) &&
                state.getValue(TapperBlock.FILL) == 4 &&
                held.is(Items.BUCKET)) {
            awardRandomXP(level, player, 1, 3); // 1–2
            return;
        }

        // Mushroom Colony
        if (block instanceof MushroomColonyBlock) {
            int before = state.getValue(MushroomColonyBlock.COLONY_AGE);
            if (before > 0) {
                defer(level, () -> {
                    BlockState afterState = level.getBlockState(pos);
                    if (!(afterState.getBlock() instanceof MushroomColonyBlock)) return;
                    int after = afterState.getValue(MushroomColonyBlock.COLONY_AGE);
                    if (after < before) awardRandomXP(level, player, 0, 2); // 0–1
                });
                return;
            }
        }

        // Tomato
        if (block instanceof TomatoVineBlock vine) {
            int current = state.getValue(TomatoVineBlock.VINE_AGE);
            if (current >= vine.getMaxAge() && (held.isEmpty() || held.getItem() instanceof ShearsItem)) {
                defer(level, () -> {
                    BlockState after = level.getBlockState(pos);
                    if (!(after.getBlock() instanceof TomatoVineBlock)) return;
                    if (after.getValue(TomatoVineBlock.VINE_AGE) < current) {
                        awardRandomXP(level, player, 1, 3); // 1–2
                    }
                });
                return;
            }
        }

        // Right Click Harvests
        if (block instanceof BonemealableBlock && !(block instanceof CropBlock) &&
                !(block instanceof StemBlock) && !(block instanceof AttachedStemBlock)) {

            Optional<IntegerProperty> ageProp = getAgeProp(state);
            if (ageProp.isEmpty()) return;
            IntegerProperty age = ageProp.get();

            int current = state.getValue(age);
            if (!isMature(state, age)) return;

            defer(level, () -> {
                BlockState updated = level.getBlockState(pos);
                boolean harvested =
                        !updated.is(state.getBlock()) ||
                                (updated.hasProperty(age) && updated.getValue(age) < current);

                if (harvested) {
                    // Bountiful Tree
                    boolean leavesOrTips = state.getBlock() instanceof BountifulLeavesBlock ||
                            state.getBlock() instanceof SpruceTipBlock;
                    awardRandomXP(level, player, leavesOrTips ? 0 : 1, 2 + (leavesOrTips ? 0 : 1)); // leaves: 0–1, else: 1–2
                }
            });
        }
    }
    //Shearing Chickens and Sheep
    @SubscribeEvent
    public static void onAnimalShear(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        Level lvl = event.getLevel();
        if (lvl.isClientSide()) return;

        ItemStack tool = player.getItemInHand(event.getHand());
        if (!(tool.getItem() instanceof ShearsItem)) return;

        if (event.getTarget() instanceof Sheep sheep) {
            if (!sheep.isBaby() && !sheep.isSheared()) {
                awardRandomXP((ServerLevel) lvl, player, 1, 3);
            }
            return;
        }

        if (event.getTarget() instanceof Chicken chicken) {
            if (!chicken.isBaby()) {
                long now = lvl.getGameTime();
                long last = chicken.getPersistentData().getLong("ShearFeatherTime");
                if (now - last >= 2400L) {
                    awardRandomXP((ServerLevel) lvl, player, 1, 3);
                }
            }
        }
    }

    private static void awardRandomXP(ServerLevel level, ServerPlayer player, int minInclusive, int maxExclusive) {
        int delta = Math.max(0, maxExclusive - minInclusive);
        int val = (delta == 0) ? minInclusive : (minInclusive + player.getRandom().nextInt(delta));
        awardXPIfPositive(level, player, val);
    }

    private static void awardXPIfPositive(ServerLevel level, ServerPlayer player, int xp) {
        if (xp > 0) ExperienceOrb.award(level, player.position(), xp);
    }

    private static Optional<IntegerProperty> getAgeProp(BlockState state) {
        return state.getProperties().stream()
                .filter(p -> p instanceof IntegerProperty && p.getName().equals("age"))
                .map(p -> (IntegerProperty) p)
                .findFirst();
    }

    private static boolean isMature(BlockState state, IntegerProperty age) {
        int cur = state.getValue(age);
        int max = age.getPossibleValues()
                .stream()
                .mapToInt(i -> i)
                .max()
                .orElse(cur);
        return cur >= max;
    }

    private static boolean isIntactDoublePlant(Level level, BlockPos pos, BlockState state) {
        if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            BlockPos counterpart = (half == DoubleBlockHalf.UPPER) ? pos.below() : pos.above();
            return level.getBlockState(counterpart).is(state.getBlock());
        }
        for (Property<?> prop : state.getProperties()) {
            if ("half".equals(prop.getName()) && prop instanceof net.minecraft.world.level.block.state.properties.EnumProperty<?> ep) {
                Comparable<?> val = state.getValue(ep);
                if (val instanceof DoubleBlockHalf half) {
                    BlockPos counterpart = (half == DoubleBlockHalf.UPPER) ? pos.below() : pos.above();
                    return level.getBlockState(counterpart).is(state.getBlock());
                }
            }
        }
        return true;
    }

    private static void defer(ServerLevel level, Runnable r) {
        level.getServer().execute(r);
    }

    private static void trackPotentialForageDrop(ServerLevel level, BlockPos pos, BlockState state, ServerPlayer player) {
        ResourceKey<Level> dim = level.dimension();
        Map<BlockPos, PendingForage> map = PENDING_FORAGING_DROPS.computeIfAbsent(dim, d -> new HashMap<>());
        long now = level.getGameTime();
        cleanupExpiredEntries(map, now);

        BlockPos trackAt = adjustTrackedPosition(state, pos).immutable();
        map.put(trackAt, new PendingForage(player, now + FORAGING_DROP_TIMEOUT_TICKS));
    }

    private static PendingForage popPendingForageNear(ServerLevel level, BlockPos itemPos) {
        ResourceKey<Level> dim = level.dimension();
        Map<BlockPos, PendingForage> map = PENDING_FORAGING_DROPS.get(dim);
        if (map == null || map.isEmpty()) return null;

        long now = level.getGameTime();
        cleanupExpiredEntries(map, now);

        for (BlockPos probe : new BlockPos[]{itemPos, itemPos.below(), itemPos.above()}) {
            PendingForage got = map.remove(probe);
            if (got != null) {
                if (map.isEmpty()) PENDING_FORAGING_DROPS.remove(dim);
                return got.isExpired(now) ? null : got;
            }
        }
        if (map.isEmpty()) PENDING_FORAGING_DROPS.remove(dim);
        return null;
    }

    private static void cleanupExpiredEntries(Map<BlockPos, PendingForage> entries, long now) {
        entries.entrySet().removeIf(e -> e.getValue().isExpired(now));
    }

    private static BlockPos adjustTrackedPosition(BlockState state, BlockPos pos) {
        if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) &&
                state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
            return pos.below();
        }
        return pos;
    }

    private static boolean isConnectedToStem(Level level, BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos n = pos.relative(dir);
            BlockState s = level.getBlockState(n);
            Block b = s.getBlock();

            if (b instanceof StemBlock) return true;

            if (b instanceof AttachedStemBlock &&
                    s.hasProperty(AttachedStemBlock.FACING) &&
                    s.getValue(AttachedStemBlock.FACING) == dir.getOpposite()) {
                return true;
            }
        }
        return false;
    }

    private record PendingForage(ServerPlayer player, long expiryTick) {
        boolean isExpired(long now) {
            return now > expiryTick || !player.isAlive();
        }
    }
}
