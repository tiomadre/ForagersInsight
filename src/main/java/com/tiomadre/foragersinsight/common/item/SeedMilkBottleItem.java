package com.tiomadre.foragersinsight.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.MilkBottleItem;

import java.util.List;

public class SeedMilkBottleItem extends MilkBottleItem {
    private static final String TOOLTIP_KEY = "tooltip.foragersinsight.seed_milk_bottle";
    private final boolean hasFoodEffectTooltip;

    public SeedMilkBottleItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties);
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context, @NotNull List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (this.hasFoodEffectTooltip) {
            tooltip.add(Component.translatable(TOOLTIP_KEY).withStyle(ChatFormatting.BLUE));
        }
    }
}