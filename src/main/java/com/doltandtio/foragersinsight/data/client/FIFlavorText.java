package com.doltandtio.foragersinsight.data.client;

import com.doltandtio.foragersinsight.core.registry.FIConfig;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FIFlavorText {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!FIConfig.isFlavorTextEnabled()) {
            return;
        }
        ItemStack stack = event.getItemStack();
        // Dandelion Root Tea
        if (stack.getItem() == FIItems.DANDELION_ROOT_TEA.get()) {
            event.getToolTip().add(
                    Component.translatable(  "foragersinsight.flavortext.dandelion_root_tea")
                            .withStyle(ChatFormatting.ITALIC).withStyle(style -> style.withColor(TextColor.fromRgb(0xfed639)))); //Dandelion
        }
        // Rose Cordial
        if (stack.getItem() == FIItems.ROSE_CORDIAL.get()) {
            event.getToolTip().add(
                    Component.translatable("foragersinsight.flavortext.rose_cordial")
                            .withStyle(ChatFormatting.ITALIC).withStyle(style -> style.withColor(TextColor.fromRgb(0xf6537e)))); //Rose

        }
        // Roselle Juice
        if (stack.getItem() == FIItems.FOREST_ELIXIR.get()) {
            event.getToolTip().add(
                    Component.translatable("foragersinsight.flavortext.roselle_juice")
                            .withStyle(ChatFormatting.ITALIC).withStyle(style -> style.withColor(TextColor.fromRgb(0x8e204c)))); //Magenta
        }
        // Seed Milk Bucket
        if (stack.getItem() == FIItems.SEED_MILK_BUCKET.get()) {
        event.getToolTip().add(
                Component.translatable("foragersinsight.flavortext.seed_milk_bucket")
                        .withStyle(ChatFormatting.ITALIC).withStyle(style -> style.withColor(TextColor.fromRgb(0xd9ecdd)))); //Light Sage
        }
      // Forest's Elixir
        if (stack.getItem() == FIItems.FOREST_ELIXIR.get()) {
        event.getToolTip().add(
                Component.translatable("foragersinsight.flavortext.forest_elixir")
                        .withStyle(ChatFormatting.ITALIC).withStyle(style -> style.withColor(TextColor.fromRgb(0x0A5F38)))); //Spruce
        }
    }
}