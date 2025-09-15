package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.common.block.BountifulLeavesBlock;
import com.doltandtio.foragersinsight.common.block.SpruceTipBlock;
import com.doltandtio.foragersinsight.common.block.TapperBlock;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID)
public class FarmingXPEvents {
    // XP GAIN FROM FARMING

    // Gain 1-2 XP per harvesting of mature crop
    @SubscribeEvent
    public static void onCropHarvest(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        Level level = event.getLevel() instanceof Level ? (Level) event.getLevel() : null;
        if (level == null || level.isClientSide()) return;

        BlockState state = event.getState();
        if (state.getBlock() instanceof StemGrownBlock) {
            int xp = 1 + player.getRandom().nextInt(2);
            ExperienceOrb.award((ServerLevel) level, player.position(), xp);
            return;
        }

        Optional<IntegerProperty> agePropOpt = getAgeProperty(state);
        if (agePropOpt.isEmpty()) return;

        IntegerProperty ageProp = agePropOpt.get();
        int currentAge = state.getValue(ageProp);
        Set<Integer> possibleAges = (Set<Integer>) ageProp.getPossibleValues();
        int maxAge = possibleAges.stream().max(Integer::compareTo).orElse(currentAge);
        if (currentAge < maxAge) return;

        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals("half") &&
                    property instanceof net.minecraft.world.level.block.state.properties.EnumProperty<?> enumProp) {
                Comparable<?> halfValue = state.getValue(enumProp);
                BlockState below = level.getBlockState(event.getPos().below());
                if (!below.is(state.getBlock())) {
                    return;
                }
            }
        }

        int xp = 1 + player.getRandom().nextInt(2);
        ExperienceOrb.award((ServerLevel) level, player.position(), xp);
    }

    // Right-Click Harvesting (Empty Hand/Shears)
    @SubscribeEvent
    public static void onRightClickHarvest(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;
        ItemStack held = player.getItemInHand(event.getHand());
        BlockState state = level.getBlockState(event.getPos());
        Block block = state.getBlock();

        //Beehive  2-3 XP
        if (block instanceof BeehiveBlock &&
                state.getValue(BeehiveBlock.HONEY_LEVEL) >= 5 &&
                (held.getItem() instanceof ShearsItem || held.is(Items.GLASS_BOTTLE))) {
            int xp = 2 + player.getRandom().nextInt(2); // 2–3 XP
            ExperienceOrb.award((ServerLevel) level, player.position(), xp);
            return;
        }
        //Tapper 1-2 XP
        if (block instanceof TapperBlock &&
                state.getValue(TapperBlock.HAS_TAPPER) &&
                state.getValue(TapperBlock.FILL) == 4 &&
                held.is(Items.BUCKET)) {
            int xp = 1 + player.getRandom().nextInt(2);
            ExperienceOrb.award((ServerLevel) level, player.position(), xp);
            return;

        }
        // Mushroom Colonies 0-1 XP
        if (block instanceof MushroomColonyBlock &&
                state.getValue(MushroomColonyBlock.COLONY_AGE) > 0) {
            int xp = player.getRandom().nextInt(2);
            if (xp > 0) {
                ExperienceOrb.award((ServerLevel) level, player.position(), xp);
            }
            return;
        }
        if (!(held.isEmpty() || held.getItem() instanceof ShearsItem)) {
            return;
        }
        state = level.getBlockState(event.getPos());
        block = state.getBlock();
        if (!(block instanceof BonemealableBlock) || block instanceof CropBlock) return;

        Optional<IntegerProperty> agePropOpt = getAgeProperty(state);
        if (agePropOpt.isEmpty()) return;

        IntegerProperty ageProp = agePropOpt.get();
        int currentAge = state.getValue(ageProp);
        Set<Integer> possibleAges = (Set<Integer>) ageProp.getPossibleValues();
        int maxAge = possibleAges.stream().max(Integer::compareTo).orElse(currentAge);
        if (currentAge < maxAge) return;

        int xp;
        if (block instanceof BountifulLeavesBlock || block instanceof SpruceTipBlock) {
            xp = player.getRandom().nextInt(2);
        } else {
            xp = 1 + player.getRandom().nextInt(2);
        }
        if (xp > 0) {
            ExperienceOrb.award((ServerLevel) level, player.position(), xp);
        }
    }
    // Milking Cows 1-2 XP
    @SubscribeEvent
    public static void onCowMilk(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        ItemStack held = player.getItemInHand(event.getHand());
        if (!held.is(Items.BUCKET)) return;

        if (event.getTarget() instanceof Cow cow) {
            if (cow.isBaby()) return;
            int xp = 1 + player.getRandom().nextInt(2);
            ExperienceOrb.award((ServerLevel) level, player.position(), xp);
        }
    }


    // Shearing Mobs 1-2 XP
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

    private static Optional<IntegerProperty> getAgeProperty(BlockState state) {
        return state.getProperties().stream()
                .filter(p -> p.getName().equals("age") && p instanceof IntegerProperty)
                .map(p -> (IntegerProperty) p)
                .findFirst();
    }
}