package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.common.block.BountifulLeavesBlock;
import com.doltandtio.naturesdelight.common.block.DoubleCropBlock;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;

public class NDBlockStates extends NDBlockStatesHelper {
    public NDBlockStates(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), NaturesDelight.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        doubleCrop(ROSE_HIP);
        this.crossCutout(BOUNTIFUL_OAK_SAPLING);
        this.crossCutout(BOUNTIFUL_DARK_OAK_SAPLING);

        this.sackBlock(ROSE_HIP_SACK);
        this.sackBlock(ROSE_PETALS_SACK);

        this.crateBlock(DANDELION_ROOTS_CRATE, "dandelion_root");
        this.sackBlock(SPRUCE_TIPS_SACK);
        this.sackBlock(BLACK_ACORN_SACK);
        this.sackBlock(POPPY_SEEDS_SACK);

        this.age5Crop(DANDELION_BUSH, NDItems.DANDELION_ROOT);
        this.age5Crop(POPPY_BUSH, NDItems.POPPY_SEEDS);

        this.bountifulLeaves(BOUNTIFUL_OAK_LEAVES, Blocks.OAK_LEAVES);
        this.bountifulLeaves(BOUNTIFUL_DARK_OAK_LEAVES, Blocks.DARK_OAK_LEAVES);
    }

    private void age5Crop(RegistryObject<Block> crop, RegistryObject<Item> seeds) {
        Block cropBlock = crop.get();
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(cropBlock);

        Predicate<Integer> shouldIncValue = i -> i != 1 && i != 3 && i != 5;
        int currentAge = 0;
        for (int i = 0; i < 8; i++) {
            builder.part().modelFile(models().cross("%s_stage%d".formatted(name(cropBlock), i), concatRL(blockTexture(cropBlock), "_stage%d".formatted(currentAge))).renderType("cutout"))
                    .addModel().condition(CropBlock.AGE, i).end();
            if (shouldIncValue.test(i)) {
                currentAge += 1;
            }
        }

        this.itemModels().basicItem(seeds.get());
    }

    public void crossCutout(RegistryObject<? extends Block> cross) {
        this.simpleBlock(cross.get(), this.models().cross(name(cross.get()), this.blockTexture(cross.get()))
                .renderType("cutout"));
        this.generatedItem(cross.get(), "block");
    }

    public void sackBlock(RegistryObject<? extends Block> block) {
        String name = name(block.get());
        this.simpleBlock(block.get(), models().cube(name,
                modTexture(name + "_bottom"), modTexture(name + "_top"),  modTexture(name + "_side"),
                modTexture(name + "_side_special"), modTexture(name + "_side"),
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

    private void bountifulLeaves(RegistryObject<? extends Block> block, Block base) {
        BountifulLeavesBlock leavesBlock = (BountifulLeavesBlock) block.get();

        this.getVariantBuilder(leavesBlock).forAllStatesExcept(state -> {
            int age = leavesBlock.getAge(state);

             return ConfiguredModel.builder().modelFile(models().withExistingParent("%s_stage%d".formatted(name(leavesBlock), age), "naturesdelight:block/leaves_with_overlay")
                     .texture("all", blockTexture(base)).texture("overlay", "%s_stage%d".formatted(blockTexture(leavesBlock), age))).build();

        }, LeavesBlock.DISTANCE, LeavesBlock.PERSISTENT, LeavesBlock.WATERLOGGED);

        this.itemModels().withExistingParent(name(leavesBlock), concatRL(blockTexture(leavesBlock), "_stage0"));
    }

    private void doubleCrop(RegistryObject<? extends Block> crop) {
        DoubleCropBlock block = (DoubleCropBlock) crop.get();

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
