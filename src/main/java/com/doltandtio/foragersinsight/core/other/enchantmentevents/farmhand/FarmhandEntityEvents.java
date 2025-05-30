package com.doltandtio.foragersinsight.core.other.enchantmentevents.farmhand;

import com.doltandtio.foragersinsight.core.registry.FIEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "foragersinsight", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FarmhandEntityEvents {

    @SubscribeEvent
    public static void onEntityShear(EntityInteractSpecific event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack tool = player.getItemInHand(hand);
        if (!(tool.getItem() instanceof ShearsItem)) return;
        if (tool.getEnchantmentLevel(FIEnchantments.FARMHAND.get()) <= 0) return;

        Entity target = event.getTarget();

        // Sheep shearing
        if (target instanceof Sheep sheep) {
            if (sheep.isSheared() || sheep.isBaby()) return;
            event.setCanceled(true);
            sheep.setSheared(true);
            int woolCount = 1 + level.random.nextInt(3);
            DyeColor color = sheep.getColor();
            Item woolItem = getWoolForColor(color);
            for (int i = 0; i < woolCount; i++) {
                ItemStack wool = new ItemStack(woolItem);
                if (!player.getInventory().add(wool)) player.drop(wool, false);
            }
            BlockPos pos = sheep.blockPosition();
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0f, 1.0f);
            tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }
    }

    private static Item getWoolForColor(DyeColor color) {
        return switch (color) {
            case ORANGE    -> Items.ORANGE_WOOL;
            case MAGENTA   -> Items.MAGENTA_WOOL;
            case LIGHT_BLUE-> Items.LIGHT_BLUE_WOOL;
            case YELLOW    -> Items.YELLOW_WOOL;
            case LIME      -> Items.LIME_WOOL;
            case PINK      -> Items.PINK_WOOL;
            case GRAY      -> Items.GRAY_WOOL;
            case LIGHT_GRAY-> Items.LIGHT_GRAY_WOOL;
            case CYAN      -> Items.CYAN_WOOL;
            case PURPLE    -> Items.PURPLE_WOOL;
            case BLUE      -> Items.BLUE_WOOL;
            case BROWN     -> Items.BROWN_WOOL;
            case GREEN     -> Items.GREEN_WOOL;
            case RED       -> Items.RED_WOOL;
            case BLACK     -> Items.BLACK_WOOL;
            default        -> Items.WHITE_WOOL;
        };
    }
}
