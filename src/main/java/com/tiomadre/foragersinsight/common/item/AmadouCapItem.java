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
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import com.google.common.collect.Multimap;
import java.util.UUID;
import java.util.List;


public class AmadouCapItem extends Item implements Equipable {

    public static int CAP_COLOR = 0xD39E85;
    private int rgb;


    private static final Multimap<Attribute, AttributeModifier> HEAD_ATTRIBUTE_MODIFIERS =
            ImmutableMultimap.of(Attributes.ARMOR.value(), new AttributeModifier(Attributes.ARMOR.getKey().location(),
                    1.0D, AttributeModifier.Operation.ADD_VALUE));

    public AmadouCapItem(Properties properties) {
        super(properties);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
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
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.HEAD ? HEAD_ATTRIBUTE_MODIFIERS : super.getDefaultAttributeModifiers();
    }

    public static int applyLuckOfTheTrees(Player player, int enchantmentLevel) {
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(FIItems.AMADOU_CAP.get())) {
            return Math.max(enchantmentLevel, 1);
        }
        return enchantmentLevel;
    }

    public int getColor(ItemStack stack) {
        return this.hasCustomColor(stack) ? lesscolor(DyeableLeatherItem.super.getColor(stack)) : 0xFFFFFF;
    }

    private static int lesscolor(int color) {
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        int gray = (int) (red * 0.3F + green * 0.59F + blue * 0.11F);

        red = desaturateChannel(red, gray);
        green = desaturateChannel(green, gray);
        blue = desaturateChannel(blue, gray);

        return red << 16 | green << 8 | blue;
    }

    private static int desaturateChannel(int channel, int gray) {
        return (int) (channel * 0.67F + gray * 0.33F);
    }


    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("enchantment.foragersinsight.luck_of_the_trees")
                .withStyle(ChatFormatting.BLUE));

    }

    private static Component getClosestDyeColorName(int color) {
        DyeColor closestColor = DyeColor.WHITE;
        int closestDistance = Integer.MAX_VALUE;
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;

        for (DyeColor dyeColor : DyeColor.values()) {
            float[] textureColors = dyeColor.getTextureDiffuseColors();
            int dyeRed = (int) (textureColors[0] * 255.0F);
            int dyeGreen = (int) (textureColors[1] * 255.0F);
            int dyeBlue = (int) (textureColors[2] * 255.0F);
            int redDifference = red - dyeRed;
            int greenDifference = green - dyeGreen;
            int blueDifference = blue - dyeBlue;
            int distance = redDifference * redDifference + greenDifference * greenDifference
                    + blueDifference * blueDifference;

            if (distance < closestDistance) {
                closestDistance = distance;
                closestColor = dyeColor;
            }
        }

        return Component.translatable("color.minecraft." + closestColor.getName());
    }
}