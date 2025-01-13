package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NaturesDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NaDItems {
    public static final ItemSubRegistryHelper HELPER = NaturesDelight.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> ROSE_PETALS = HELPER.createItem("rose_petals", () -> new Item(new Item.Properties()));
}
