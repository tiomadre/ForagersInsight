package com.doltandtio.foragersinsight.core.other.toolevents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.ArrayList;
import java.util.List;

// Snip Interactions (Shears Right Click)
// Drops from Snipping will drop at the player's feet instead of normal drop behavior!
@Mod.EventBusSubscriber(modid = "foragersinsight", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShearsSnipInteractions {
    private static final long CHICKEN_SHEAR_COOLDOWN = 2_400L; // 2 min CD per chicken

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
        ServerLevel server = (ServerLevel) level;
    //Crops
        // Kelp
        if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT)) {
            List<BlockPos> kelpBlocks = new ArrayList<>();
            BlockPos.MutableBlockPos cursor = pos.mutable();

            // snip top 2 kelp off plant, plus fortune
            while (level.getBlockState(cursor).is(Blocks.KELP_PLANT) || level.getBlockState(cursor).is(Blocks.KELP)) {
                kelpBlocks.add(cursor.immutable());
                cursor.move(Direction.UP);
            }
            // don't snip base
            if (kelpBlocks.size() <= 1) return;
            kelpBlocks.remove(0);
            event.setCanceled(true);

            int maxBreak = Math.min(2, kelpBlocks.size());
            int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
            int extraDrops = 0;

            for (int i = 0; i < fortune; i++) {
                if (level.getRandom().nextFloat() < 0.2F) {
                    extraDrops++;
                }}
            for (int i = 0; i < maxBreak; i++) {
                BlockPos target = kelpBlocks.get(kelpBlocks.size() - 1 - i);
                BlockState targetState = level.getBlockState(target);
                ItemStack kelpDrop = new ItemStack(Items.KELP, 1 + extraDrops);
level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), kelpDrop));
                level.destroyBlock(target, false);
            }

            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.playSound(null, pos, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            return;
        }
        // Mushroom Colonies
        if (state.getBlock() instanceof MushroomColonyBlock mushroomColony) {
            int age = state.getValue(MushroomColonyBlock.COLONY_AGE);
            if (age > 0) {
                event.setCanceled(true);

                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                int baseDrops = 1;
                int extraDrops = 0;
            // snip 1 mushroom, plus fortune
                for (int i = 0; i < fortune; i++) {
                    if (level.getRandom().nextFloat() < 0.2F) {
                        extraDrops++;
                    }}
                int totalDrops = baseDrops + extraDrops;
                ItemStack drop = new ItemStack(mushroomColony.mushroomType.get(), totalDrops);
                level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), drop));
                level.setBlock(pos, state.setValue(MushroomColonyBlock.COLONY_AGE, age - 1), Block.UPDATE_ALL);

                level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }return;}
        // Sugar Cane
        if (state.is(Blocks.SUGAR_CANE)) {
            int count = 1;
            while (level.getBlockState(pos.above(count)).is(Blocks.SUGAR_CANE)) {
                count++;
            }
            if (count >= 2) {
                event.setCanceled(true);
                BlockPos top = pos.above(count - 1);
                server.setBlock(top, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                // snip 1 sugar cane, plus fortune
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                int extra = fortune > 0
                        ? level.getRandom().nextInt(fortune + 1)
                        : 0;
                int totalCane = 1 + extra;
                ItemStack drop = new ItemStack(Items.SUGAR_CANE, totalCane);
                level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), drop));
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.playSound(null, top, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }}
        // Sweet Berry Bush (mature)
        if (state.getBlock() instanceof SweetBerryBushBlock
                && state.getValue(SweetBerryBushBlock.AGE) >= SweetBerryBushBlock.MAX_AGE) {
            event.setCanceled(true);
            // snip to guarantee 3 berries, plus fortune
            int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
            int extra = (fortune > 0)
                    ? level.getRandom().nextInt(fortune + 1)
                    : 0;
            int total = 3 + extra;

            ItemStack drop = new ItemStack(Items.SWEET_BERRIES, total);
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), drop));
            int resetAge = SweetBerryBushBlock.MAX_AGE - 2;
            level.setBlock(pos, state.setValue(SweetBerryBushBlock.AGE, resetAge), Block.UPDATE_ALL);

            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 1.0F);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            return;}
        // Tomatoes
        if (state.getBlock() instanceof TomatoVineBlock tomatoVine) {
            event.setCanceled(true);
            int age = state.getValue(TomatoVineBlock.VINE_AGE);
            if (age == tomatoVine.getMaxAge()) {
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
        // snip 2 tomatoes, plus fortune
        int baseDrops = 2;
        int extraDrops = 0;
        if (fortune > 0) {
            for (int i = 0; i < fortune; i++) {
                if (level.getRandom().nextFloat() < 0.20F) {
                    extraDrops++;
                }}}
        int totalTomatoes = baseDrops + extraDrops;

                ItemStack tomatoDrop = new ItemStack(vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get(), totalTomatoes);
         level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), tomatoDrop));

         if (level.random.nextFloat() < 0.05) {
         ItemStack rottenTomatoDrop = new ItemStack(vectorwing.farmersdelight.common.registry.ModItems.ROTTEN_TOMATO.get());
         level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), rottenTomatoDrop));
}
                level.setBlock(pos, state.setValue(TomatoVineBlock.VINE_AGE, 0), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.playSound(null, pos, vectorwing.farmersdelight.common.registry.ModSounds.ITEM_TOMATO_PICK_FROM_BUSH.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                tool.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));}}}
//Entities
    //Chickens
    @SubscribeEvent
    public static void onShearChicken(EntityInteract event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getTarget() instanceof Chicken chicken)) return;

        // no snip dee behbeh
        if (chicken.isBaby()) {
            event.getEntity().displayClientMessage(TextUtils.getTranslation("tool_interaction.shears.baby_chicken"), true);
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
            player.displayClientMessage(TextUtils.getTranslation("tool_interaction.shears.chicken",secondsLeft), true);
            event.setCanceled(true);
            return;
        }
        data.putLong("ShearFeatherTime", now);
        event.setCanceled(true);

        int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
        // snip 2-3 feathers, plus fortune
        int baseCount = 2 + event.getLevel().getRandom().nextInt(3);
        int extraFeathers = 0;
        for (int i = 0; i < fortune; i++) {
            if (event.getLevel().getRandom().nextFloat() < 0.2F) {
                extraFeathers++;
            }
        }
                int totalFeathers = baseCount + extraFeathers;
                ItemStack drop = new ItemStack(Items.FEATHER, totalFeathers);
                event.getLevel().addFreshEntity(new ItemEntity(event.getLevel(), player.getX(), player.getY(), player.getZ(), drop));
        // snip and cluck
        event.getLevel().playSound(null, chicken.blockPosition(),
                SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS,
                1.0F, 1.0F);
        event.getLevel().playSound(null, chicken.blockPosition(),
                SoundEvents.CHICKEN_AMBIENT, SoundSource.PLAYERS,
                1.0F, 1.0F);

        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }
// Sheep
    private static Item getWoolItemByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> Items.WHITE_WOOL;
            case ORANGE -> Items.ORANGE_WOOL;
            case MAGENTA -> Items.MAGENTA_WOOL;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_WOOL;
            case YELLOW -> Items.YELLOW_WOOL;
            case LIME -> Items.LIME_WOOL;
            case PINK -> Items.PINK_WOOL;
            case GRAY -> Items.GRAY_WOOL;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_WOOL;
            case CYAN -> Items.CYAN_WOOL;
            case PURPLE -> Items.PURPLE_WOOL;
            case BLUE -> Items.BLUE_WOOL;
            case BROWN -> Items.BROWN_WOOL;
            case GREEN -> Items.GREEN_WOOL;
            case RED -> Items.RED_WOOL;
            case BLACK -> Items.BLACK_WOOL;
        };}
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

        //fortune logic
        Level level = event.getLevel();
        int baseWoolCount = 2 + level.getRandom().nextInt(3); // 2-3 Wool
        int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
        int extraWool = 0;
        for (int i = 0; i < fortuneLevel; i++) {
            if (level.getRandom().nextFloat() < 0.2F) {
                extraWool++;
            }
        }
        int totalWool = baseWoolCount + extraWool;
        DyeColor sheepColor = sheep.getColor();
        ItemStack woolDrop = new ItemStack(getWoolItemByColor(sheepColor), totalWool);

level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), woolDrop));
        level.playSound(null, sheep, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }}