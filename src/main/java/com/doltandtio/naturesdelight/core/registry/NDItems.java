package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.other.NDFoods;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.FoodValues;

@Mod.EventBusSubscriber(modid = NaturesDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NDItems {
    public static final ItemSubRegistryHelper HELPER = NaturesDelight.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> ROSE_PETALS = HELPER.createItem("rose_petals", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROSE_COOKIE = HELPER.createItem("rose_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final RegistryObject<Item> ROSE_GRANITA = HELPER.createItem("rose_granita", () -> new Item(new Item.Properties()
            .food(NDFoods.ROSE_GRANITA)));

}
