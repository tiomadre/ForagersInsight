package com.doltandtio.foragersinsight.data.server.tags;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.ModTags;

import static com.doltandtio.foragersinsight.core.registry.FIBlocks.*;
import static com.doltandtio.foragersinsight.data.server.tags.FITags.BlockTag.*;

public class FIBlockTags extends BlockTagsProvider {

    public FIBlockTags(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), ForagersInsight.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.LOGS).add(SAPPY_BIRCH_LOG.get());
        this.tag(BlockTags.BIRCH_LOGS).add(SAPPY_BIRCH_LOG.get());
        this.tag(BlockTags.SAPLINGS).add(BOUNTIFUL_OAK_SAPLING.get(), BOUNTIFUL_DARK_OAK_SAPLING.get(), BOUNTIFUL_SPRUCE_SAPLING.get());
        this.tag(BlockTags.LEAVES).add(BOUNTIFUL_OAK_LEAVES.get(),BOUNTIFUL_DARK_OAK_LEAVES.get(),BOUNTIFUL_SPRUCE_LEAVES.get());
        this.tag(BlockTags.CROPS).add(ROSE_CROP.get(), DANDELION_BUSH.get(), POPPY_BUSH.get(),BOUNTIFUL_DARK_OAK_LEAVES.get()
        ,BOUNTIFUL_OAK_LEAVES.get(),BOUNTIFUL_SPRUCE_TIPS.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(DANDELION_BUSH.get(), POPPY_BUSH.get(),ROSE_CROP.get(),BOUNTIFUL_DARK_OAK_LEAVES.get(),BOUNTIFUL_OAK_LEAVES.get()
        ,BOUNTIFUL_SPRUCE_TIPS.get(),ROSE_CROP.get());

        registerForgeTags();
        registerMineables();
    }

    protected void registerMineables() {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(BOUNTIFUL_OAK_SAPLING.get(), BOUNTIFUL_DARK_OAK_SAPLING.get(), DANDELION_ROOT_SACK.get(), SAPPY_BIRCH_LOG.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(BOUNTIFUL_OAK_LEAVES.get(),BOUNTIFUL_DARK_OAK_LEAVES.get(),BOUNTIFUL_SPRUCE_LEAVES.get());
        this.tag(ModTags.MINEABLE_WITH_KNIFE).add(ROSE_HIP_SACK.get(), POPPY_SEEDS_SACK.get(), SPRUCE_TIPS_SACK.get(), BLACK_ACORN_SACK.get());
    }


    protected void registerForgeTags() {
        tag(STORAGE_BLOCK_ROSE_HIP).add(ROSE_HIP_SACK.get());
        tag(STORAGE_BLOCK_POPPY_SEEDS).add(POPPY_SEEDS_SACK.get());
        tag(STORAGE_BLOCK_DANDELION_ROOT).add(DANDELION_ROOT_SACK.get());
        tag(STORAGE_BLOCK_SPRUCE_TIPS).add(SPRUCE_TIPS_SACK.get());
        tag(STORAGE_BLOCK_BLACK_ACORNS).add(BLACK_ACORN_SACK.get());
    }


}