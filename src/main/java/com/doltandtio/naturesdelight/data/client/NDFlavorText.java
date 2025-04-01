package com.doltandtio.naturesdelight.data.client;


import com.doltandtio.naturesdelight.core.registry.NDItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "naturesdelight", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NDFlavorText {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        // Dandelion Root Tea
        if (stack.getItem() == NDItems.DANDELION_ROOT_TEA.get()) {
            event.getToolTip().add(
                    Component.translatable(  "naturesdelight.flavortext.dandelion_root_tea")
                            .withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.YELLOW));
        }
        // Rose Cordial
        if (stack.getItem() == NDItems.ROSE_CORDIAL.get()) {
            event.getToolTip().add(
                    Component.translatable("naturesdelight.flavortext.rose_cordial")
                            .withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.RED));
        }
        // Seed Milk Bucket
        if (stack.getItem() == NDItems.SEED_MILK_BUCKET.get()) {
        event.getToolTip().add(
                Component.translatable("naturesdelight.flavortext.seed_milk_bucket")
                        .withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
    }
    }
}