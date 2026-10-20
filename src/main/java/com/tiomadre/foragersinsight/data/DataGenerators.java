package com.tiomadre.foragersinsight.data;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlockLoot;
import com.tiomadre.foragersinsight.core.registry.FIDatamapProvider;
import com.tiomadre.foragersinsight.core.registry.FIRecipes;
import com.tiomadre.foragersinsight.data.client.FIBlockStates;
import com.tiomadre.foragersinsight.data.client.FIItemModels;
import com.tiomadre.foragersinsight.data.client.FIParticles;
import com.tiomadre.foragersinsight.data.server.FIAdvancementProvider;
import com.tiomadre.foragersinsight.data.server.FIWorldgen;
import com.tiomadre.foragersinsight.data.server.recipes.FICraftingRecipes;
import com.tiomadre.foragersinsight.data.server.tags.FIBiomeTags;
import com.tiomadre.foragersinsight.data.server.tags.FIBlockTags;
import com.tiomadre.foragersinsight.data.server.tags.FIItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = ForagersInsight.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void dataSetup(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        boolean server = event.includeServer();
        FIBlockTags blockTags = new FIBlockTags(event);
        gen.addProvider(server, blockTags);
        gen.addProvider(server, new FIBiomeTags(event));
        gen.addProvider(server, new FIItemTags(event, blockTags));
        gen.addProvider(server, new LootTableProvider(event.getGenerator().getPackOutput(), Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(FIBlockLoot::new, LootContextParamSets.BLOCK)),event.getLookupProvider()));
        gen.addProvider(server, new FICraftingRecipes(event.getGenerator().getPackOutput() ,event.getLookupProvider()));
        //gen.addProvider(event.includeServer(), new FIRecipes(event.getGenerator().getPackOutput(),event.getLookupProvider()));
        gen.addProvider(server, new FIWorldgen(event));
        gen.addProvider(server, new FIAdvancementProvider(event.getGenerator().getPackOutput(),event.getLookupProvider(),event.getExistingFileHelper()));
        gen.addProvider(server, new FIDatamapProvider(gen.getPackOutput(), event.getLookupProvider()));


        boolean client = event.includeClient();
        gen.addProvider(client, new FIBlockStates(event));
        gen.addProvider(client, new FIItemModels(event));
        gen.addProvider(client, new FIParticles(event));
    }
}
