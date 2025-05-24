package com.doltandtio.foragersinsight.core.other.toolevents;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
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

            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                    SoundSource.BLOCKS, 1.0F, 1.0F);
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
                level.playSound(null, top, SoundEvents.CROP_BREAK,
                        SoundSource.BLOCKS, 1.0F, 1.0F);
                tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
        }
    }

    @SubscribeEvent
    public static void onShearChicken(EntityInteract event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getTarget() instanceof Chicken chicken)) return;

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
        // drop 1-2 feathers
        int count = 1 + event.getLevel().getRandom().nextInt(2);
        ItemStack drop = new ItemStack(Items.FEATHER, count);
        if (!player.getInventory().add(drop)) {
            player.drop(drop, false);
        }
        event.getLevel().playSound(null, chicken.blockPosition(),
                SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS,
                1.0F, 1.0F);

        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
    }
}
