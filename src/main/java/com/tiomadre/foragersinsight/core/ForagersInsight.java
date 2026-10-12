package com.tiomadre.foragersinsight.core;

import com.tiomadre.foragersinsight.data.server.FIAdvancementProvider;
import com.tiomadre.foragersinsight.core.registry.FIEnchantments;
import com.tiomadre.foragersinsight.core.other.FIClientCompat;
import com.tiomadre.foragersinsight.core.registry.FIDatamapProvider;
import com.tiomadre.foragersinsight.core.registry.*;
import com.tiomadre.foragersinsight.data.client.FIBlockStates;
import com.tiomadre.foragersinsight.data.client.FIItemModels;
import com.tiomadre.foragersinsight.data.client.FIParticles;
import com.tiomadre.foragersinsight.data.server.FIWorldgen;
import com.tiomadre.foragersinsight.data.server.recipes.FICraftingRecipes;
import com.tiomadre.foragersinsight.data.server.tags.FIBiomeTags;
import com.tiomadre.foragersinsight.data.server.tags.FIBlockTags;
import com.tiomadre.foragersinsight.data.server.tags.FIItemTags;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@Mod(ForagersInsight.MOD_ID)
public class ForagersInsight {
	public static final String MOD_ID = "foragersinsight";

	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public ForagersInsight(IEventBus modEventBus, ModContainer modContainer) {
		//NeoForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(modEventBus);
		FIBoatTypes.register();
		FIAdvancements.TRIGGERS.register(modEventBus);
		FIWoodTypes.register();
        FIBlocks.BLOCKS.register(modEventBus);
		FIBiomeFeatures.register(modEventBus);
		FIBlockEntityTypes.register(modEventBus);
		FIFoliagePlacerType.FOLIAGE_PLACER_TYPE.register(modEventBus);
		FILootModifiers.LOOT_MODIFIERS.register(modEventBus);
		FIMenuTypes.MENUS.register(modEventBus);
		FIMobEffects.MOB_EFFECTS.register(modEventBus);
		FIParticleTypes.register(modEventBus);
		FITabs.TABS.register(modEventBus);
		FITreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modEventBus);
		FIRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		FITappables.bootstrap();
        FIDataComponents.register(modEventBus);
        FIRecipeTypes.RECIPE_TYPES.register(modEventBus);


		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::clientSetup);


		modContainer.registerConfig(ModConfig.Type.COMMON, FIConfig.COMMON_SPEC);
	}

	public static ResourceLocation rl(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	private void commonSetup(FMLCommonSetupEvent event) {

	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(FIClientCompat::registerCompat);
	}


}
