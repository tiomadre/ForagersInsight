package com.doltandtio.naturesdelight.data.server.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.CRUSHED_ICE;
import static com.doltandtio.naturesdelight.data.server.tags.NDTags.ItemTag.*;

public class NDItemTags extends ItemTagsProvider {
    public NDItemTags(GatherDataEvent e, NDBlockTags blockTags) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), blockTags.contentsGetter());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(NDTags.ItemTag.ICE).add(Items.ICE, CRUSHED_ICE.get())
                .addOptional(new ResourceLocation("neapolitan", "ice_cubes"));

        registerForgeTags();
    }

    protected void registerForgeTags() {
        tag(STORAGE_BLOCK_ROSE_HIP).add(ROSE_HIP_SACK.get().asItem());
        tag(STORAGE_BLOCK_ROSE_PETALS).add(ROSE_PETALS_SACK.get().asItem());

        tag(STORAGE_BLOCK_POPPY_SEEDS).add(POPPY_SEEDS_SACK.get().asItem());
        tag(STORAGE_BLOCK_DANDELION_ROOT).add(DANDELION_ROOTS_CRATE.get().asItem());

        tag(STORAGE_BLOCK_SPRUCE_TIPS).add(SPRUCE_TIPS_SACK.get().asItem());
        tag(STORAGE_BLOCK_BLACK_ACORNS).add(BLACK_ACORN_SACK.get().asItem());
    }
}
