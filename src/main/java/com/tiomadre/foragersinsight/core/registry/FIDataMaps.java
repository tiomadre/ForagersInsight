package com.tiomadre.foragersinsight.core.registry;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.data.server.DiffuserInput;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = ForagersInsight.MOD_ID, value = Dist.CLIENT)
public class FIDataMaps {

    public static final DataMapType<Item, DiffuserInput> DIFFUSER_INPUT_DATA_MAP_TYPE = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath("foragersinsight", "diffuserrecipes"),
            Registries.ITEM,
            DiffuserInput.CODEC
    ).build();




    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(DIFFUSER_INPUT_DATA_MAP_TYPE);
    }
}