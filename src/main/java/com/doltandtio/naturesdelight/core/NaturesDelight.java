package com.doltandtio.naturesdelight.core;

import com.doltandtio.naturesdelight.core.other.NDClientCompat;
import com.doltandtio.naturesdelight.core.other.NDDataUtil;
import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import com.doltandtio.naturesdelight.core.registry.NDFoliagePlacerType;
import com.doltandtio.naturesdelight.core.registry.NDLootModifiers;
import com.doltandtio.naturesdelight.core.registry.NDMobEffects;
import com.doltandtio.naturesdelight.data.client.NDBlockStates;
import com.doltandtio.naturesdelight.data.client.NDItemModels;
import com.doltandtio.naturesdelight.data.server.NDAdvancements;
import com.doltandtio.naturesdelight.data.server.NDLoot;
import com.doltandtio.naturesdelight.data.server.NDWorldgen;
import com.doltandtio.naturesdelight.data.server.recipes.NDCraftingRecipes;
import com.doltandtio.naturesdelight.data.server.tags.NDBiomeTags;
import com.doltandtio.naturesdelight.data.server.tags.NDBlockTags;
import com.doltandtio.naturesdelight.data.server.tags.NDItemTags;
import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NaturesDelight.MOD_ID)
public class NaturesDelight {
	public static final String MOD_ID = "naturesdelight";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public NaturesDelight() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		NDLootModifiers.LOOT_MODIFIERS.register(bus);
		NDMobEffects.MOB_EFFECTS.register(bus);
		NDFoliagePlacerType.FOLIAGE_PLACER_TYPE.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> NDBlocks::setupTabEditors);
	}

	public static ResourceLocation rl(String namespace) {
		return new ResourceLocation(MOD_ID, namespace);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			NDDataUtil.registerCompat();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			NDClientCompat.registerCompat();
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();

		boolean server = event.includeServer();
		NDBlockTags blockTags = new NDBlockTags(event);
		gen.addProvider(server, blockTags);
		gen.addProvider(server, new NDBiomeTags(event));
		gen.addProvider(server, new NDItemTags(event, blockTags));
		gen.addProvider(server, new NDLoot(event));
		gen.addProvider(server, new NDCraftingRecipes(event));
		gen.addProvider(server, new NDWorldgen(event));
		gen.addProvider(server, new NDAdvancements(event));

		boolean client = event.includeClient();
		gen.addProvider(client, new NDBlockStates(event));
		gen.addProvider(client, new NDItemModels(event));
	}
}