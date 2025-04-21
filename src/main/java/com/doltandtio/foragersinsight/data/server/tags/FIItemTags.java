package com.doltandtio.foragersinsight.data.server.tags;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import static com.doltandtio.foragersinsight.core.registry.FIBlocks.*;
import static com.doltandtio.foragersinsight.core.registry.FIItems.CRUSHED_ICE;
import static com.doltandtio.foragersinsight.data.server.tags.FITags.ItemTag.*;

public class FIItemTags extends ItemTagsProvider {
    public FIItemTags(GatherDataEvent e, FIBlockTags blockTags) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), blockTags.contentsGetter());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        //Other
        this.tag(FITags.ItemTag.ICE).add(Items.ICE, CRUSHED_ICE.get())
                .addOptional(new ResourceLocation("neapolitan", "ice_cubes"));
        this.tag(FITags.ItemTag.SEEDS).add(FIItems.BLACK_ACORN.get(), FIItems.POPPY_SEEDS.get(), FIItems.SUNFLOWER_KERNELS.get());
        this.tag(FITags.ItemTag.MILK_BUCKET).add(FIItems.SEED_MILK_BUCKET.get());
        this.tag(FITags.ItemTag.MILK_BOTTLE).add(FIItems.SEED_MILK_BOTTLE.get());
        //Crops
        this.tag(FITags.ItemTag.APPLE).add(FIItems.APPLE_SLICE.get(),Items.APPLE);
        this.tag(FITags.ItemTag.POPPY_SEEDS).add(FIItems.POPPY_SEEDS.get(), FIItems.POPPY_SEED_PASTE.get());
        this.tag(ACORN).add(FIItems.BLACK_ACORN.get(), FIItems.ACORN_MEAL.get());
        this.tag(COCOA).add(Items.COCOA_BEANS, FIItems.COCOA_POWDER.get());
        this.tag(ROOTS).add(Items.CARROT,Items.BEETROOT, FIItems.DANDELION_ROOT.get());
        this.tag(MUSHROOM).add(Items.RED_MUSHROOM,Items.BROWN_MUSHROOM);
        //

        registerForgeTags();
    }

    protected void registerForgeTags() {
        tag(STORAGE_BLOCK_ROSE_HIP).add(ROSE_HIP_SACK.get().asItem());

        tag(STORAGE_BLOCK_POPPY_SEEDS).add(POPPY_SEEDS_SACK.get().asItem());
        tag(STORAGE_BLOCK_DANDELION_ROOT).add(DANDELION_ROOTS_CRATE.get().asItem());

        tag(STORAGE_BLOCK_SPRUCE_TIPS).add(SPRUCE_TIPS_SACK.get().asItem());
        tag(STORAGE_BLOCK_BLACK_ACORNS).add(BLACK_ACORN_SACK.get().asItem());
    }
}
