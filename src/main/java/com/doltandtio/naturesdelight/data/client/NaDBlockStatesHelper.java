package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class NaDBlockStatesHelper extends BlueprintBlockStateProvider {
    public NaDBlockStatesHelper(PackOutput output, String modid, ExistingFileHelper helper) {
        super(output, modid, helper);
    }

    public static ResourceLocation loc(RegistryObject<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getKey(block.get());
    }

    public static ResourceLocation modTexture(String string) {
        return NaturesDelight.rl("block/" + string);
    }

    public static ResourceLocation modTexture(String str, String string) {
        return new ResourceLocation(str, "block/" + string);
    }
}
