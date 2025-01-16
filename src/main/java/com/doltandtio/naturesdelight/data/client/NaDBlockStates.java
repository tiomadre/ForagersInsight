package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.common.block.DoubleCropBlock;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;

public class NaDBlockStates extends NaDBlockStatesHelper {
    private static final String ITEM_GENERATED = "item/generated";

    public NaDBlockStates(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), NaturesDelight.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        doubleCrop(ROSE_HIP);
        this.crateBlock(ROSE_HIP_CRATE, "rose_hip");
        this.sackBlock(ROSE_PETALS_SACK);
    }

    public void sackBlock(RegistryObject<? extends Block> block) {
        String name = name(block.get());
        this.simpleBlock(block.get(), models().cube(name,
                modTexture(name + "_bottom"), modTexture(name + "_top"),  modTexture(name + "_unique_side"),
                modTexture(name + "_unique_side"), modTexture(name + "_side"),
                modTexture(name + "_side")).texture("particle", modTexture(name + "_top")));

        this.blockItem(block.get());
    }

    public void crateBlock(RegistryObject<? extends Block> block, String cropName) {
        this.simpleBlock(block.get(),
                models().cubeBottomTop(name(block.get()), modTexture(cropName + "_crate_side"),
                        modTexture("crate_bottom"),
                        modTexture(cropName + "_crate_top")));

        this.blockItem(block.get());
    }

    private void doubleCrop(RegistryObject<? extends DoubleCropBlock> crop) {
        DoubleCropBlock block = crop.get();

        this.getVariantBuilder(block).forAllStates(state -> {
            int age;
            String half;
            if (DoubleCropBlock.isIllegalState(state)) {
                age = 0;
                half = "lower";
            }
            else {
                age = state.getValue(DoubleCropBlock.AGE);
                half = state.getValue(DoubleCropBlock.HALF) == DoubleBlockHalf.UPPER ? "upper" : "lower";
            }

            return ConfiguredModel.builder().modelFile(models().withExistingParent("%s_stage%d_%s".formatted(name(block), age, half), "block/cross")
                    .texture("cross", "%s_stage%d_%s".formatted(blockTexture(block), age, half)).renderType("cutout")).build();
        });

        this.itemModels().basicItem(crop.get().asItem());
    }
}
