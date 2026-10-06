package com.tiomadre.foragersinsight.common.item;

import com.tiomadre.foragersinsight.core.registry.FIConfig;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MilkBucketItem extends Item {
    private static final int DRINK_DURATION = 32;
    private static final String TOOLTIP_KEY = "tooltip.foragersinsight.milk_bucket";
    private final boolean hasFoodEffectTooltip;

    public MilkBucketItem(Properties settings, boolean hasFoodEffectTooltip) {
        super(settings);
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (this.hasFoodEffectTooltip) {
            tooltip.add(Component.translatable(TOOLTIP_KEY).withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide) {
            if (FIConfig.COMMON.milkRemovesOdorous.get()) {
                entity.removeAllEffects();
            } else {
                new ArrayList<>(entity.getActiveEffects()).stream()
                        .map(MobEffectInstance::getEffect)
                        .filter(effect -> effect != FIMobEffects.ODOROUS.get())
                        .forEach(entity::removeEffect);
            }
        }

        if (entity instanceof Player player) {
            return ItemUtils.createFilledResult(stack, player, Items.BUCKET.getDefaultInstance());
        }

        return stack;
    }
}