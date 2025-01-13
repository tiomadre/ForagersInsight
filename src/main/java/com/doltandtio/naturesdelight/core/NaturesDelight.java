package com.doltandtio.naturesdelight.core;

import com.doltandtio.naturesdelight.core.other.NaDDataUtil;
import com.doltandtio.naturesdelight.data.client.NaDBlockStates;
import com.doltandtio.naturesdelight.data.client.NaDItemModels;
import com.doltandtio.naturesdelight.data.server.tags.NaDBlockTags;
import com.doltandtio.naturesdelight.data.server.tags.NDLoot;
import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
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

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);
	}

	public static ResourceLocation rl(String namespace) {
		return new ResourceLocation(MOD_ID, namespace);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			NaDDataUtil.registerCompat();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();

		boolean server = event.includeServer();
		NaDBlockTags blockTags = new NaDBlockTags(event);
		gen.addProvider(server, blockTags);
		gen.addProvider(server, new NDLoot(event));

		boolean client = event.includeClient();
		gen.addProvider(client, new NaDBlockStates(event));
		gen.addProvider(client, new NaDItemModels(event));
	}
}