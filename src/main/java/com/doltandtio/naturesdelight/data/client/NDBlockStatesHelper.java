package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class NDBlockStatesHelper extends BlueprintBlockStateProvider {
    public NDBlockStatesHelper(PackOutput output, String modid, ExistingFileHelper helper) {
        super(output, modid, helper);
    }

    public static ResourceLocation loc(RegistryObject<? extends Block> block) {
        return loc(block.get());
    }

    public static ResourceLocation loc(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public static ResourceLocation modTexture(String string) {
        return NaturesDelight.rl("block/" + string);
    }

    public static ResourceLocation concatRL(ResourceLocation loc, String str) {
        return new ResourceLocation(loc.getNamespace(), loc.getPath() + str);
    }

    public static ResourceLocation modTexture(String str, String string) {
        return new ResourceLocation(str, "block/" + string);
    }

    public static ResourceLocation itemTexture(Block item) {
        ResourceLocation loc = loc(item);
        return new ResourceLocation(loc.getNamespace(), "item/" + loc.getPath());
    }
}
