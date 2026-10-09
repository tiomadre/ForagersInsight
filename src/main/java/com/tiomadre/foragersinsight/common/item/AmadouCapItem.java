package com.tiomadre.foragersinsight.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.List;


public class AmadouCapItem extends Item implements Equipable {

    public AmadouCapItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack itemToRepair, @NotNull ItemStack repairItem) {
        return repairItem.is(FIItems.AMADOU.get());
    }



    public static int applyLuckOfTheTrees(Player player, int enchantmentLevel) {
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(FIItems.AMADOU_CAP.get())) {
            return Math.max(enchantmentLevel, 1);
        }
        return enchantmentLevel;
    }



    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("enchantment.foragersinsight.luck_of_the_trees")
                .withStyle(ChatFormatting.BLUE));

    }

}