package com.doltandtio.foragersinsight.data.client;

import com.doltandtio.foragersinsight.common.block.BountifulLeavesBlock;
import com.doltandtio.foragersinsight.common.block.RoseCropBlock;
import com.doltandtio.foragersinsight.common.block.SunflowerCropBlock;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIItems;
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

import static com.doltandtio.foragersinsight.core.registry.FIBlocks.*;

public class FIBlockStates extends FIBlockStatesHelper {
    public FIBlockStates(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), ForagersInsight.MOD_ID, e.getExistingFileHelper());
    }
    @Override
    protected void registerStatesAndModels() {
        this.doubleCrop(ROSE_CROP);
        this.doubleSunflowerCrop(SUNFLOWER_CROP);
        this.sackBlock(ROSE_HIP_SACK);
        this.crossCutout(BOUNTIFUL_OAK_SAPLING);
        this.bountifulLeaves(BOUNTIFUL_OAK_LEAVES, Blocks.OAK_LEAVES);
        this.crossCutout(BOUNTIFUL_DARK_OAK_SAPLING);
        this.bountifulLeaves(BOUNTIFUL_DARK_OAK_LEAVES, Blocks.DARK_OAK_LEAVES);
        this.sackBlock(BLACK_ACORN_SACK);
        this.age5Crop(DANDELION_BUSH, FIItems.DANDELION_ROOT);
        this.crateBlock(DANDELION_ROOTS_CRATE, "dandelion_root");
        this.sackBlock(SPRUCE_TIPS_SACK);
        this.sackBlock(POPPY_SEEDS_SACK);
        this.age5Crop(POPPY_BUSH, FIItems.POPPY_SEEDS);
        this.matBlock(SCATTERED_ROSE_PETAL_MAT, "scattered_rose_petals");
        this.matBlock(SCATTERED_SPRUCE_TIP_MAT, "scattered_spruce_tips");
        this.matBlock(DENSE_SPRUCE_TIP_MAT, "dense_spruce_tips");
        this.matBlock(DENSE_ROSE_PETAL_MAT, "dense_rose_petals");
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

             return ConfiguredModel.builder().modelFile(models().withExistingParent("%s_stage%d".formatted(name(leavesBlock), age), "ForagersInsight:block/leaves_with_overlay")
                     .texture("all", blockTexture(base)).texture("overlay", "%s_stage%d".formatted(blockTexture(leavesBlock), age))).build();

        }, LeavesBlock.DISTANCE, LeavesBlock.PERSISTENT, LeavesBlock.WATERLOGGED);

        this.itemModels().withExistingParent(name(leavesBlock), concatRL(blockTexture(leavesBlock), "_stage0"));
    }

    public void doubleCrop(RegistryObject<? extends Block> crop) {
        RoseCropBlock block = (RoseCropBlock) crop.get();

        this.getVariantBuilder(block).forAllStates(state -> {
            int age;
            String half;
            if (RoseCropBlock.isIllegalState(state)) {
                age = 0;
                half = "lower";
            }
            else {
                age = state.getValue(RoseCropBlock.AGE);
                half = state.getValue(RoseCropBlock.HALF) == DoubleBlockHalf.UPPER ? "upper" : "lower";
            }

            return ConfiguredModel.builder().modelFile(models().withExistingParent("%s_stage%d_%s".formatted(name(block), age, half), "block/cross")
                    .texture("cross", "%s_stage%d_%s".formatted(blockTexture(block), age, half)).renderType("cutout")).build();
        });

        this.itemModels().basicItem(crop.get().asItem());
    }
    public void doubleSunflowerCrop(RegistryObject<? extends Block> crop) {
        SunflowerCropBlock block = (SunflowerCropBlock) crop.get();

        this.getVariantBuilder(block).forAllStates(state -> {
            int age;
            String half;
            if (state.getValue(SunflowerCropBlock.AGE) < 0 || !state.hasProperty(SunflowerCropBlock.HALF)) {
                age = 0;
                half = "lower";
            }
            else {
                age = state.getValue(SunflowerCropBlock.AGE);
                half = state.getValue(SunflowerCropBlock.HALF) == DoubleBlockHalf.UPPER ? "upper" : "lower";
            }

            return ConfiguredModel.builder()
                    .modelFile(
                            models()
                                    .withExistingParent(
                                            "%s_stage%d_%s".formatted(name(block), age, half),
                                            mcLoc("block/sunflower")
                                    )
                                    .texture("cross", modLoc("%s_stage%d_%s".formatted(blockTexture(block), age, half)))
                                    .renderType("cutout")
                    )
                    .build();
        });

        this.itemModels().basicItem(crop.get().asItem());
    }
    public void matBlock(RegistryObject<? extends Block> block, String texture) {
        this.simpleBlock(block.get(),
                models().withExistingParent(name(block.get()), mcLoc("block/carpet"))
                        .texture("foliage", modTexture(texture))
                        .renderType("cutout"));
        this.blockItem(block.get());
    }
}
