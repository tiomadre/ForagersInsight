package com.doltandtio.foragersinsight.core.other.toolevents;

import com.doltandtio.foragersinsight.common.block.BountifulLeavesBlock;
import com.doltandtio.foragersinsight.common.block.SpruceTipBlock;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;
import vectorwing.farmersdelight.common.utility.TextUtils;
import net.minecraft.world.item.DyeColor; // for getWoolItemByColor

import java.util.ArrayDeque;
import java.util.function.Predicate;

// Snip Interactions (Shears Right Click)
// Drops from snipping are collected and dropped near the player.
@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShearsSnipInteractions {

    private static final long CHICKEN_SHEAR_COOLDOWN = 2_400L; // 2 min CD per chicken
    private static final float FORTUNE_PROC_CHANCE = 0.20F;

    private static int getFortuneLevel(ItemStack tool) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
    }

    /** Each fortune level has 20% to add +1 */
    private static int rollFortuneExtras(RandomSource rand, int fortuneLevels) {
        int extra = 0;
        for (int i = 0; i < fortuneLevels; i++) {
            if (rand.nextFloat() < FORTUNE_PROC_CHANCE) extra++;
        }
        return extra;
    }

    private static void play(Level level, BlockPos pos, SoundEvent sound) {
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    private static void damageTool(ItemStack tool, Player player, InteractionHand hand) {
        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }
    private static int harvestVerticalTop(Level level, BlockPos basePos, Predicate<BlockState> isSameCrop, int maxBreak) {
        // Build stack from base upward
        ArrayDeque<BlockPos> stack = new ArrayDeque<>();
        BlockPos.MutableBlockPos cursor = basePos.mutable();
        while (isSameCrop.test(level.getBlockState(cursor))) {
            stack.addLast(cursor.immutable());
            cursor.move(Direction.UP);
        }
        if (stack.size() <= 1) return 0; // nothing to cut if only base exists
        stack.removeFirst(); // never break base

        int broken = 0;
        while (broken < maxBreak && !stack.isEmpty()) {
            BlockPos top = stack.removeLast(); // break from the top down
            level.destroyBlock(top, false);
            broken++;
        }
        return broken;
    }

    public static void dropItemInFront(Level level, Player player, ItemStack stack) {
        dropItemInFront(level, player, stack, 3.25, 0.6);
    }

    public static void dropItemInFront(Level level, Player player, ItemStack stack, double distance) {
        dropItemInFront(level, player, stack, distance, 0.6);
    }

    public static void dropItemInFront(Level level, Player player, ItemStack stack, double distance, double heightOffset) {
        Vec3 look = player.getLookAngle().normalize();
        distance = Math.max(2.0, Math.min(distance, 4.5));
        double x = player.getX() + look.x * distance;
        double y = player.getY() + heightOffset;
        double z = player.getZ() + look.z * distance;

        ItemEntity drop = new ItemEntity(level, x, y, z, stack);
        drop.setPickUpDelay(5);

        if (!level.noCollision(drop.getBoundingBox())) {
            double fallback = 2.5;
            drop.setPos(player.getX() + look.x * fallback, y, player.getZ() + look.z * fallback);
        }

        level.addFreshEntity(drop);
    }

    /* --------------------------- block interactions --------------------------- */

    @SubscribeEvent
    public static void onShearCrop(RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack tool = player.getItemInHand(hand);
        if (!(tool.getItem() instanceof ShearsItem)) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        RandomSource rand = level.getRandom();

        //Bountiful Crops
        // Bountiful Dark Oak and Oak Leaves
        if (state.getBlock() instanceof BountifulLeavesBlock leavesBlock) {
            int age = state.getValue(BountifulLeavesBlock.AGE);
            if (age >= BountifulLeavesBlock.MAX_AGE) {
                event.setCanceled(true);

                int extraDrops = rollFortuneExtras(rand, getFortuneLevel(tool));
                // snip 2 apple or acorns, plus fortune
                Item bountyItem = leavesBlock.getBounty();
                ItemStack drop = new ItemStack(bountyItem, 2 + extraDrops);
                dropItemInFront(level, player, drop);

                BlockState updatedState = state.setValue(BountifulLeavesBlock.AGE, 0);
                level.setBlock(pos, updatedState, Block.UPDATE_CLIENTS);

                play(level, pos, SoundEvents.SHEEP_SHEAR);
                play(level, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES);
                damageTool(tool, player, hand);
                return;
            }

            // Bountiful Spruce Tips
            if (state.getBlock() instanceof SpruceTipBlock tip && tip.isRandomlyTicking(state)) {
                age = state.getValue(SpruceTipBlock.AGE);
                if (age >= SpruceTipBlock.MAX_AGE) {
                    event.setCanceled(true);
                    //snip 2 spruce tips plus fortune
                    int fortune = getFortuneLevel(tool);
                    int extra = fortune > 0 ? rand.nextInt(fortune + 1) : 0;
                    ItemStack drop = new ItemStack(FIItems.SPRUCE_TIPS.get(), 2 + extra);
                    dropItemInFront(level, player, drop);

                    level.setBlock(pos, state.setValue(SpruceTipBlock.AGE, 0), Block.UPDATE_ALL);
                    play(level, pos, SoundEvents.SHEEP_SHEAR);
                    play(level, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES);
                    damageTool(tool, player, hand);
                    return;
                }
            }
        }

        // Kelp
        if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT)) {
            event.setCanceled(true);

            int broken = harvestVerticalTop(
                    level, pos,
                    bs -> bs.is(Blocks.KELP) || bs.is(Blocks.KELP_PLANT),
                    2 // snip up to 2 segments
            );
            if (broken <= 0) return;

            int extraDrops = rollFortuneExtras(rand, getFortuneLevel(tool));
            for (int i = 0; i < broken; i++) {
                ItemStack kelpDrop = new ItemStack(Items.KELP, 1 + extraDrops);
                dropItemInFront(level, player, kelpDrop);
            }

            play(level, pos, SoundEvents.SHEEP_SHEAR);
            play(level, pos, SoundEvents.WATER_AMBIENT);
            damageTool(tool, player, hand);
            return;
        }

        // Mushroom Colonies
        if (state.getBlock() instanceof MushroomColonyBlock mushroomColony) {
            int age = state.getValue(MushroomColonyBlock.COLONY_AGE);
            if (age > 0) {
                event.setCanceled(true);

                int extraDrops = rollFortuneExtras(rand, getFortuneLevel(tool));
                // snip 1 mushroom, plus fortune
                ItemStack drop = new ItemStack(mushroomColony.mushroomType.get(), 1 + extraDrops);
                dropItemInFront(level, player, drop);

                level.setBlock(pos, state.setValue(MushroomColonyBlock.COLONY_AGE, age - 1), Block.UPDATE_ALL);

                play(level, pos, SoundEvents.MOOSHROOM_SHEAR);
                damageTool(tool, player, hand);
            }
            return;
        }

        // Sugar Cane
        if (state.is(Blocks.SUGAR_CANE)) {
            // snip 1 top segment if stack height >= 2
            int broken = harvestVerticalTop(level, pos, bs -> bs.is(Blocks.SUGAR_CANE), 1);
            if (broken > 0) {
                event.setCanceled(true);

                int fortune = getFortuneLevel(tool);
                int extra = fortune > 0 ? rand.nextInt(fortune + 1) : 0;
                ItemStack drop = new ItemStack(Items.SUGAR_CANE, 1 + extra);
                dropItemInFront(level, player, drop);

                play(level, pos, SoundEvents.SHEEP_SHEAR);
                play(level, pos, SoundEvents.CROP_BREAK);
                damageTool(tool, player, hand);
            }
            return;
        }

        // Sweet Berry Bush (mature)
        if (state.getBlock() instanceof SweetBerryBushBlock &&
                state.getValue(SweetBerryBushBlock.AGE) >= SweetBerryBushBlock.MAX_AGE) {

            event.setCanceled(true);
            // snip max berries (3), plus fortune
            int fortune = getFortuneLevel(tool);
            int extra = fortune > 0 ? rand.nextInt(fortune + 1) : 0;
            ItemStack drop = new ItemStack(Items.SWEET_BERRIES, 3 + extra);
            dropItemInFront(level, player, drop);

            level.setBlock(pos,
                    state.setValue(SweetBerryBushBlock.AGE, SweetBerryBushBlock.MAX_AGE - 2),
                    Block.UPDATE_ALL);

            play(level, pos, SoundEvents.SHEEP_SHEAR);
            play(level, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES);
            damageTool(tool, player, hand);
            return;
        }

        // Tomatoes
        if (state.getBlock() instanceof TomatoVineBlock tomatoVine) {
            event.setCanceled(true);
            int age = state.getValue(TomatoVineBlock.VINE_AGE);
            if (age == tomatoVine.getMaxAge()) {
                int extraDrops = rollFortuneExtras(rand, getFortuneLevel(tool));
                // snip 2 tomatoes, plus fortune
                ItemStack tomatoDrop = new ItemStack(
                        vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get(),
                        2 + extraDrops
                );
                dropItemInFront(level, player, tomatoDrop);

                if (rand.nextFloat() < 0.05F) { // 5% chance
                    ItemStack rotten = new ItemStack(
                            vectorwing.farmersdelight.common.registry.ModItems.ROTTEN_TOMATO.get()
                    );
                    dropItemInFront(level, player, rotten);
                }

                level.setBlock(pos, state.setValue(TomatoVineBlock.VINE_AGE, 0), Block.UPDATE_ALL);

                play(level, pos, SoundEvents.SHEEP_SHEAR);
                level.playSound(null, pos,
                        vectorwing.farmersdelight.common.registry.ModSounds.ITEM_TOMATO_PICK_FROM_BUSH.get(),
                        SoundSource.BLOCKS, 1, 1);
                damageTool(tool, player, hand);
            }
        }
    }

    /* --------------------------- entity interactions --------------------------- */

    // Entities
    // Chickens
    @SubscribeEvent
    public static void onShearChicken(EntityInteract event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getTarget() instanceof Chicken chicken)) return;

        // no snip dee behbeh
        if (chicken.isBaby()) {
            event.getEntity().displayClientMessage(
                    TextUtils.getTranslation("tool_interaction.shears.baby_chicken"), true);
            event.setCanceled(true);
            return;
        }

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack tool = player.getItemInHand(hand);
        if (!(tool.getItem() instanceof ShearsItem)) return;

        long now = event.getLevel().getGameTime();
        CompoundTag data = chicken.getPersistentData();
        long last = data.getLong("ShearFeatherTime");
        long elapsed = now - last;

        if (elapsed < CHICKEN_SHEAR_COOLDOWN) {
            int secondsLeft = (int) Math.ceil((CHICKEN_SHEAR_COOLDOWN - elapsed) / 20.0);
            player.displayClientMessage(
                    TextUtils.getTranslation("tool_interaction.shears.chicken", secondsLeft), true);
            event.setCanceled(true);
            return;
        }

        data.putLong("ShearFeatherTime", now);
        event.setCanceled(true);

        // snip 2-3 feathers, plus fortune
        RandomSource rand = event.getLevel().getRandom();
        int baseCount = 2 + rand.nextInt(3);
        int extraFeathers = rollFortuneExtras(rand, getFortuneLevel(tool));

        ItemStack drop = new ItemStack(Items.FEATHER, baseCount + extraFeathers);
        dropItemInFront(event.getLevel(), player, drop);

        event.getLevel().playSound(null, chicken.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1, 1);
        event.getLevel().playSound(null, chicken.blockPosition(), SoundEvents.CHICKEN_AMBIENT, SoundSource.PLAYERS, 1, 1);

        damageTool(tool, player, hand);
    }

    // Sheep
    private static Item getWoolItemByColor(DyeColor color) {
        return switch (color) {
            case WHITE      -> Items.WHITE_WOOL;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_WOOL;
            case GRAY       -> Items.GRAY_WOOL;
            case BLACK      -> Items.BLACK_WOOL;
            case BROWN      -> Items.BROWN_WOOL;
            case RED        -> Items.RED_WOOL;
            case ORANGE     -> Items.ORANGE_WOOL;
            case YELLOW     -> Items.YELLOW_WOOL;
            case LIME       -> Items.LIME_WOOL;
            case GREEN      -> Items.GREEN_WOOL;
            case CYAN       -> Items.CYAN_WOOL;
            case MAGENTA    -> Items.MAGENTA_WOOL;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_WOOL;
            case BLUE       -> Items.BLUE_WOOL;
            case PURPLE     -> Items.PURPLE_WOOL;
            case PINK       -> Items.PINK_WOOL;
        };
    }

    @SubscribeEvent
    public static void onShearSheep(EntityInteract event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getTarget() instanceof net.minecraft.world.entity.animal.Sheep sheep)) return;

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack tool = player.getItemInHand(hand);
        if (!(tool.getItem() instanceof ShearsItem)) return;
        if (!sheep.isAlive() || sheep.isSheared() || sheep.isBaby()) return;

        event.setCanceled(true);
        sheep.setSheared(true);

        // snip 2-3 wool, plus fortune
        Level level = event.getLevel();
        RandomSource rand = level.getRandom();
        int baseWoolCount = 2 + rand.nextInt(3);
        int extraWool = rollFortuneExtras(rand, getFortuneLevel(tool));

        ItemStack woolDrop = new ItemStack(getWoolItemByColor(sheep.getColor()), baseWoolCount + extraWool);
        dropItemInFront(level, player, woolDrop);

        level.playSound(null, sheep, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1, 1);
        damageTool(tool, player, hand);
    }
}
