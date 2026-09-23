package com.tiomadre.foragersinsight.core.registry;


import com.tiomadre.foragersinsight.common.enchantments.LuckOfTheTreesEnchantment;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.tag.ModTags;


public final class FIEnchantments {

    public static final ResourceKey<Enchantment> FARMHAND = key("farmhand");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        register(context, FARMHAND,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ModTags.Items.KNIFE_ENCHANTABLE),items.getOrThrow(Tags.Items.TOOLS_SHEAR),
                                        5, // weight
                                        3, // max level
                                        Enchantment.dynamicCost(15, 9),
                                        Enchantment.dynamicCost(50, 8),
                                        2, // anvil cost
                                        EquipmentSlotGroup.MAINHAND))
                        .withEffect(,
                                new MultiplyValue(LevelBasedValue.perLevel(1.4F, 0.2F))));


    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, name));
    }

}