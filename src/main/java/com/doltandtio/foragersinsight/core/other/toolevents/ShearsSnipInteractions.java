package com.doltandtio.foragersinsight.core.other.toolevents;

import com.doltandtio.foragersinsight.common.block.BountifulLeavesBlock;
import com.doltandtio.foragersinsight.common.block.SpruceTipBlock;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIItems;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.ArrayList;
import java.util.List;

// Snip Interactions (Shears Right Click)
// Drops from snipping are neatly collected and dropped in front of the player.
@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShearsSnipInteractions {

    private static final long CHICKEN_SHEAR_COOLDOWN = 2_400L; // 2 min CD per chicken
    private static void dropItemInFront(Level level, Player player, ItemStack stack) {
        Vec3 look = player.getLookAngle().normalize();
        double distance = 2.5;
        double x = player.getX() + look.x * distance;
        double y = player.getY() + 0.5;
        double z = player.getZ() + look.z * distance;
        ItemEntity drop = new ItemEntity(level, x, y, z, stack);
        level.addFreshEntity(drop);
    }
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
        //Bountiful Crops
            // Bountiful Dark Oak and Oak Leaves
        if (state.getBlock() instanceof BountifulLeavesBlock leavesBlock) {
            int age = state.getValue(BountifulLeavesBlock.AGE);
            if (age >= BountifulLeavesBlock.MAX_AGE) {
                event.setCanceled(true);
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                    // snip 2 apple or acorns, plus fortune
                int extraDrops = 0;
                for (int i = 0; i < fortune; i++) {
                    if (level.getRandom().nextFloat() < 0.2F) {
                        extraDrops++;
                    }}

                Item bountyItem = leavesBlock.getBounty();
                ItemStack drop = new ItemStack(bountyItem, 2 + extraDrops);
                dropItemInFront(level, player, drop);

                BlockState updatedState = state.setValue(BountifulLeavesBlock.AGE, 0);
                level.setBlock(pos, updatedState, Block.UPDATE_CLIENTS);

                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
                level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1, 1);
                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));

                return;
            }
            // Bountiful Spruce Tips
            if (state.getBlock() instanceof SpruceTipBlock tip && tip.isRandomlyTicking(state)) {
                age = state.getValue(SpruceTipBlock.AGE);
                if (age >= SpruceTipBlock.MAX_AGE) {
                    event.setCanceled(true);
                    //snip 2 spruce tips plus fortune
                    int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                    int extra = fortune > 0 ? level.getRandom().nextInt(fortune + 1) : 0;
                    ItemStack drop = new ItemStack(FIItems.SPRUCE_TIPS.get(), 2 + extra);
                    dropItemInFront(level, player, drop);
                    level.setBlock(pos, state.setValue(SpruceTipBlock.AGE, 0), Block.UPDATE_ALL);
                    level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
                    level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1, 1);
                    tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                    return;
                }
            }
        }
        // Kelp
        if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT)) {
            List<BlockPos> kelpBlocks = new ArrayList<>();
            BlockPos.MutableBlockPos cursor = pos.mutable();

            while (level.getBlockState(cursor).is(Blocks.KELP_PLANT) ||
                    level.getBlockState(cursor).is(Blocks.KELP)) {
                kelpBlocks.add(cursor.immutable());
                cursor.move(Direction.UP);
            } //dont snip base of crop
            if (kelpBlocks.size() <= 1) return;
            kelpBlocks.remove(0);
            event.setCanceled(true);
            // snip 2 kelp, plus fortune
            int maxBreak = Math.min(2, kelpBlocks.size());
            int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
            int extraDrops = 0;
            for (int i = 0; i < fortune; i++) {
                if (level.getRandom().nextFloat() < 0.2F) extraDrops++;
            }

            for (int i = 0; i < maxBreak; i++) {
                BlockPos target = kelpBlocks.get(kelpBlocks.size() - 1 - i);
                ItemStack kelpDrop = new ItemStack(Items.KELP, 1 + extraDrops);
                dropItemInFront(level, player, kelpDrop);
                level.destroyBlock(target, false);
            }

            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
            level.playSound(null, pos, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 1, 1);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            return;
        }
        // Mushroom Colonies
        if (state.getBlock() instanceof MushroomColonyBlock mushroomColony) {
            int age = state.getValue(MushroomColonyBlock.COLONY_AGE);
            if (age > 0) {
                event.setCanceled(true);
                // snip 1 mushroom, plus fortune
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                int extraDrops = 0;
                for (int i = 0; i < fortune; i++) {
                    if (level.getRandom().nextFloat() < 0.2F) extraDrops++;
                }
                ItemStack drop = new ItemStack(mushroomColony.mushroomType.get(), 1 + extraDrops);
                dropItemInFront(level, player, drop);

                level.setBlock(pos,
                        state.setValue(MushroomColonyBlock.COLONY_AGE, age - 1),
                        Block.UPDATE_ALL);

                level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1, 1);
                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
            return;
        }
        // Sugar Cane
        if (state.is(Blocks.SUGAR_CANE)) {
            int count = 1;
            while (level.getBlockState(pos.above(count)).is(Blocks.SUGAR_CANE)) {
                count++;
            } // snip 1 sugar cane, plus fortune
            if (count >= 2) {
                event.setCanceled(true);
                BlockPos top = pos.above(count - 1);
                server.setBlock(top, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
                int extra = fortune > 0 ? level.getRandom().nextInt(fortune + 1) : 0;
                ItemStack drop = new ItemStack(Items.SUGAR_CANE, 1 + extra);
                dropItemInFront(level, player, drop);

                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
                level.playSound(null, top, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1, 1);

                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
        }
        // Sweet Berry Bush (mature)
        if (state.getBlock() instanceof SweetBerryBushBlock &&
                state.getValue(SweetBerryBushBlock.AGE) >= SweetBerryBushBlock.MAX_AGE) {
            event.setCanceled(true);
            // snip max berries (3), plus fortune
            int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
            int extra = fortune > 0 ? level.getRandom().nextInt(fortune + 1) : 0;
            ItemStack drop = new ItemStack(Items.SWEET_BERRIES, 3 + extra);
            dropItemInFront(level, player, drop);

            level.setBlock(pos,
                    state.setValue(SweetBerryBushBlock.AGE, SweetBerryBushBlock.MAX_AGE - 2),
                    Block.UPDATE_ALL);

            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1, 1);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            return;
        }
        // Tomatoes
        if (state.getBlock() instanceof TomatoVineBlock tomatoVine) {
            event.setCanceled(true);
            int age = state.getValue(TomatoVineBlock.VINE_AGE);
            if (age == tomatoVine.getMaxAge()) {
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);

                int extraDrops = 0;
                for (int i = 0; i < fortune; i++) {
                    if (level.getRandom().nextFloat() < 0.2F) {
                        extraDrops++;
                    }
                }
                // snip 2 tomatoes, plus fortune
                ItemStack tomatoDrop = new ItemStack(
                        vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get(),
                        2 + extraDrops
                );
                dropItemInFront(level, player, tomatoDrop);

                if (level.getRandom().nextFloat() < 0.05) { // 5% chance
                    ItemStack rotten = new ItemStack(
                            vectorwing.farmersdelight.common.registry.ModItems.ROTTEN_TOMATO.get()
                    );
                    dropItemInFront(level, player, rotten);
                }

                level.setBlock(pos,
                        state.setValue(TomatoVineBlock.VINE_AGE, 0),
                        Block.UPDATE_ALL);

                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
                level.playSound(null, pos,
                        vectorwing.farmersdelight.common.registry.ModSounds.ITEM_TOMATO_PICK_FROM_BUSH.get(),
                        SoundSource.BLOCKS, 1, 1);
                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
        }
    }

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
            int secondsLeft = (int)Math.ceil((CHICKEN_SHEAR_COOLDOWN - elapsed) / 20.0);
            player.displayClientMessage(
                    TextUtils.getTranslation("tool_interaction.shears.chicken", secondsLeft), true);
            event.setCanceled(true);
            return;
        }
        data.putLong("ShearFeatherTime", now);
        event.setCanceled(true);
        // snip 2-3 feathers, plus fortune
        int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
        int baseCount = 2 + event.getLevel().getRandom().nextInt(3);
        int extraFeathers = 0;
        for (int i = 0; i < fortune; i++) {
            if (event.getLevel().getRandom().nextFloat() < 0.2F) extraFeathers++;
        }

        ItemStack drop = new ItemStack(Items.FEATHER, baseCount + extraFeathers);
        dropItemInFront(event.getLevel(), player, drop);

        event.getLevel().playSound(
                null, chicken.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1, 1);
        event.getLevel().playSound(
                null, chicken.blockPosition(), SoundEvents.CHICKEN_AMBIENT, SoundSource.PLAYERS, 1, 1);

        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));}
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
        // snip 2-3 wool, plus fortune
        Level level = event.getLevel();
        int baseWoolCount = 2 + level.getRandom().nextInt(3);
        int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
        int extraWool = 0;
        for (int i = 0; i < fortuneLevel; i++) {
            if (level.getRandom().nextFloat() < 0.2F) extraWool++;
        }
        ItemStack woolDrop = new ItemStack(
                getWoolItemByColor(sheep.getColor()), baseWoolCount + extraWool);
        dropItemInFront(level, player, woolDrop);

        level.playSound(null, sheep, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1, 1);
        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }
}
