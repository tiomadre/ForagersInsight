package com.doltandtio.naturesdelight.data.server.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import static com.doltandtio.naturesdelight.core.registry.NDItems.CRUSHED_ICE;

public class NDItemTags extends ItemTagsProvider {
    public NDItemTags(GatherDataEvent e, NDBlockTags blockTags) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), blockTags.contentsGetter());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(NDTags.ItemTag.ICE).add(Items.ICE, CRUSHED_ICE.get())
                .addOptional(new ResourceLocation("neapolitan", "ice_cubes"));
    }
}
