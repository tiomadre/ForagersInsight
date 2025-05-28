package com.doltandtio.foragersinsight.core.other.toolevents;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
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

@Mod.EventBusSubscriber(modid = "foragersinsight", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShearInteractionEvent {
    // 2 minutes (2400 ticks) cooldown per chicken
    private static final long CHICKEN_SHEAR_COOLDOWN = 2_400L;

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
        // Handle MushroomColonyBlock
        if (state.getBlock() instanceof MushroomColonyBlock mushroomColony) {
            int age = state.getValue(MushroomColonyBlock.COLONY_AGE);
            if (age > 0) {
                event.setCanceled(true);

                // Fortune logic
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                int baseDrops = 1; // Base drop for age > 0
                int extraDrops = 0;

                for (int i = 0; i < fortune; i++) {
                    if (level.getRandom().nextFloat() < 0.5F) { // Adjust probability as needed
                        extraDrops++;
                    }
                }
                int totalDrops = baseDrops + extraDrops;

                // Drop the items
                ItemStack drop = new ItemStack(mushroomColony.mushroomType.get(), totalDrops);
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }

                // Update block state
                level.setBlock(pos, state.setValue(MushroomColonyBlock.COLONY_AGE, age - 1), Block.UPDATE_ALL);

                // Play sound
                level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);

                // Damage tool
                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
            return;
        }
        // Sweet Berry Bush (mature)
        if (state.getBlock() instanceof SweetBerryBushBlock bush
                && state.getValue(SweetBerryBushBlock.AGE) >= SweetBerryBushBlock.MAX_AGE) {
            event.setCanceled(true);

            // right click w/ shears guarantees 3 berries from mature crop (affected by fortune)
            int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
            int extra = (fortune > 0)
                    ? level.getRandom().nextInt(fortune + 1)
                    : 0;
            int total = 3 + extra;

            ItemStack drop = new ItemStack(Items.SWEET_BERRIES, total);
            if (!player.getInventory().add(drop)) {
                player.drop(drop, false);
            }
            // reset stage
            int resetAge = SweetBerryBushBlock.MAX_AGE - 2;
            level.setBlock(pos, state.setValue(SweetBerryBushBlock.AGE, resetAge), Block.UPDATE_ALL);

            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 1.0F);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            return;
        }
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
                // right click w/ shears on sugar cane (affected by fortune)
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                int extra = fortune > 0
                        ? level.getRandom().nextInt(fortune + 1)
                        : 0;
                int totalCane = 1 + extra;
                ItemStack drop = new ItemStack(Items.SUGAR_CANE, totalCane);
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.playSound(null, top, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }

        }
    }
    //Entities
    @SubscribeEvent
    public static void onShearChicken(EntityInteract event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getTarget() instanceof Chicken chicken)) return;

        // no snip dee behbeh
        if (chicken.isBaby()) {
            event.getEntity().sendSystemMessage(Component.literal("You cannot shear baby chickens!"));
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
            player.sendSystemMessage(Component.literal("This chicken can be sheared again in " + secondsLeft + "s!"));
            event.setCanceled(true);
            return;
        }
        data.putLong("ShearFeatherTime", now);
        event.setCanceled(true);

        int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
        // right click to shear 2 feathers (affected by fortune)
        int baseCount = 2;
        int extraFeathers = (fortune > 0) ? event.getLevel().getRandom().nextInt(fortune + 1) : 0;
        int totalFeathers = baseCount + extraFeathers;

        ItemStack drop = new ItemStack(Items.FEATHER, totalFeathers);
        if (!player.getInventory().add(drop)) {
            player.drop(drop, false);
        }
        // snip and cluck
        event.getLevel().playSound(null, chicken.blockPosition(),
                SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS,
                1.0F, 1.0F);
        event.getLevel().playSound(null, chicken.blockPosition(),
                SoundEvents.CHICKEN_AMBIENT, SoundSource.PLAYERS,
                1.0F, 1.0F);

        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }

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

        // fortune logíco
        sheep.setSheared(true);
        Level level = event.getLevel();
        int baseWoolCount = 2 + level.getRandom().nextInt(3); // 2-3 Wool
        int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
        int extraWool = (fortuneLevel > 0) ? level.getRandom().nextInt(fortuneLevel + 1) : 0;
        int totalWool = baseWoolCount + extraWool;
        // wool drop stuffs
        DyeColor sheepColor = sheep.getColor();
        ItemStack woolDrop = new ItemStack(getWoolItemByColor(sheepColor), totalWool);
        BlockPos sheepPos = sheep.blockPosition();
        level.addFreshEntity(new ItemEntity(level, sheepPos.getX(), sheepPos.getY(), sheepPos.getZ(), woolDrop));
        level.playSound(null, sheep, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }
}