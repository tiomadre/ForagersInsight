package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.core.registry.FIEnchantments;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(modid=ForagersInsight.MOD_ID)
public class FIEvents {
//Farmhand Enchant logic
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        ItemStack tool = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(FIEnchantments.FARMHAND.get(), tool);
        if (level <= 0) return;

        ServerLevel levelWorld = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        BlockEntity blockEntity = levelWorld.getBlockEntity(pos);

        event.setCanceled(true);
        levelWorld.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        //drops stuff direct into inventory
        List<ItemStack> drops = Block.getDrops(state, levelWorld, pos, blockEntity, player, tool);
        for (ItemStack drop : drops) {
            if (!player.getInventory().add(drop)) {
                player.drop(drop, false);
            }
        }

        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
    }

// Farming XP Gain
@SubscribeEvent
public static void onCropHarvest(BlockEvent.BreakEvent event) {
    if (!(event.getPlayer() instanceof ServerPlayer player)) return;

    Level level = event.getLevel() instanceof Level ? (Level) event.getLevel() : null;
    if (level == null || level.isClientSide()) return;

    BlockState state = event.getState();
    if (state.getBlock() instanceof StemGrownBlock) {
        player.giveExperiencePoints(1);
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
        if (property.getName().equals("half") && property instanceof net.minecraft.world.level.block.state.properties.EnumProperty<?> enumProp) {
            Comparable<?> halfValue = state.getValue(enumProp);
            if (halfValue.toString().equalsIgnoreCase("upper")) {
                return;
            }
        }
    }

    player.giveExperiencePoints(1);
}
//Farming XP gain on right click
    @SubscribeEvent
    public static void onRightClickHarvest(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        BlockState state = level.getBlockState(event.getPos());
        Block block = state.getBlock();
        if (!(block instanceof BonemealableBlock) || block instanceof CropBlock) return;

        Optional<IntegerProperty> agePropOpt = getAgeProperty(state);
        if (agePropOpt.isEmpty()) return;

        IntegerProperty ageProp = agePropOpt.get();
        int currentAge = state.getValue(ageProp);
        Set<Integer> possibleAges = (Set<Integer>) ageProp.getPossibleValues();
        int maxAge = possibleAges.stream().max(Integer::compareTo).orElse(currentAge);
        if (currentAge < maxAge) return;

        player.giveExperiencePoints(1);
    }

    private static Optional<IntegerProperty> getAgeProperty(BlockState state) {
        return state.getProperties().stream()
                .filter(p -> p.getName().equals("age") && p instanceof IntegerProperty)
                .map(p -> (IntegerProperty) p)
                .findFirst();
    }

}
