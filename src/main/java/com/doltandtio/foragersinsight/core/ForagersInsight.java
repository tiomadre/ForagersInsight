package com.doltandtio.foragersinsight.core;

import com.doltandtio.foragersinsight.core.registry.FIEnchantments;
import com.doltandtio.foragersinsight.core.other.FIClientCompat;
import com.doltandtio.foragersinsight.core.other.FIDataUtil;
import com.doltandtio.foragersinsight.core.registry.*;
import com.doltandtio.foragersinsight.data.client.FIBlockStates;
import com.doltandtio.foragersinsight.data.client.FIItemModels;
import com.doltandtio.foragersinsight.data.server.FIAdvancements;
import com.doltandtio.foragersinsight.data.server.FILoot;
import com.doltandtio.foragersinsight.data.server.FIWorldgen;
import com.doltandtio.foragersinsight.data.server.recipes.FICraftingRecipes;
import com.doltandtio.foragersinsight.data.server.tags.FIBiomeTags;
import com.doltandtio.foragersinsight.data.server.tags.FIBlockTags;
import com.doltandtio.foragersinsight.data.server.tags.FIItemTags;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ForagersInsight.MOD_ID)
public class ForagersInsight {
	public static final String MOD_ID = "foragersinsight";

	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public ForagersInsight() {
		ModLoadingContext context = ModLoadingContext.get();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		FIEnchantments.register();
		FIFoliagePlacerType.FOLIAGE_PLACER_TYPE.register(bus);
		FILootModifiers.LOOT_MODIFIERS.register(bus);
		FIMenuTypes.MENUS.register(bus);
		FIMobEffects.MOB_EFFECTS.register(bus);
		FIParticleTypes.PARTICLES.register(bus);
		FITabs.TABS.register(bus);
		FITreeDecoratorTypes.TREE_DECORATOR_TYPES.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		context.registerConfig(ModConfig.Type.COMMON, FIConfig.COMMON_SPEC);
	}

	public static ResourceLocation rl(String namespace) {
		return new ResourceLocation(MOD_ID, namespace);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(FIDataUtil::registerCompat);
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(FIClientCompat::registerCompat);
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();

		boolean server = event.includeServer();
		FIBlockTags blockTags = new FIBlockTags(event);
		gen.addProvider(server, blockTags);
		gen.addProvider(server, new FIBiomeTags(event));
		gen.addProvider(server, new FIItemTags(event, blockTags));
		gen.addProvider(server, new FILoot(event));
		gen.addProvider(server, new FICraftingRecipes(event));
		gen.addProvider(server, new FIWorldgen(event));
		gen.addProvider(server, new FIAdvancements(event));

		boolean client = event.includeClient();
		gen.addProvider(client, new FIBlockStates(event));
		gen.addProvider(client, new FIItemModels(event));
	}
}