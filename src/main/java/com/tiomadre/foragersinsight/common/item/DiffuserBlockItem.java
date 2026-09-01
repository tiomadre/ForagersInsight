package com.tiomadre.foragersinsight.common.item;

import com.tiomadre.foragersinsight.common.block.entity.DiffuserBlockEntity;
import com.tiomadre.foragersinsight.data.server.recipes.FIDiffusingRecipes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DiffuserBlockItem extends BlockItem {
    private static final int INGREDIENT_SLOT_COUNT = DiffuserBlockEntity.INPUT_SLOT_COUNT;

    public DiffuserBlockItem(Block block, Properties properties) {
        super(block, properties);
    }


    public void appendHoverText(@NotNull ItemStack stack, TooltipContext tooltipContext, @NotNull List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, tooltipContext, tooltip, flag);
        Optional<FIDiffusingRecipes> scent = DiffuserBlockEntity.getScentFromItem(stack);
        if (scent.isPresent()) {
            tooltip.add(Component.translatable("item.foragersinsight.diffuser.tooltip.scent", scent.get().displayName())
                    .withStyle(ChatFormatting.GOLD));
            int remainingDuration = DiffuserBlockEntity.getRemainingDurationFromItem(stack);
            int durationSeconds = (int) Math.round(remainingDuration / 20.0D);
            tooltip.add(Component.translatable("item.foragersinsight.diffuser.tooltip.remaining_duration", durationSeconds)
                    .withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("item.foragersinsight.diffuser.tooltip.ingredients").withStyle(ChatFormatting.GRAY));
        }

        List<ItemStack> ingredients = DiffuserBlockEntity.getIngredientsFromItem(stack);
        for (int slot = 0; slot < INGREDIENT_SLOT_COUNT; slot++) {
            ItemStack ingredient = ingredients.get(slot);
            if (ingredient.isEmpty()) {
                tooltip.add(Component.translatable("item.foragersinsight.diffuser.tooltip.empty_slot").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                tooltip.add(Component.literal("-").append(ingredient.getHoverName()).withStyle(ChatFormatting.WHITE));
            }
        }
    }
}