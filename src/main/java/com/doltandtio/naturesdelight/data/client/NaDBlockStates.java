package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.common.block.DoubleCropBlock;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.ROSE_HIP;

public class NaDBlockStates extends BlueprintBlockStateProvider {
    private static final String ITEM_GENERATED = "item/generated";

    public NaDBlockStates(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), NaturesDelight.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        doubleCrop(ROSE_HIP);
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
