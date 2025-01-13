package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import static com.doltandtio.naturesdelight.core.registry.NaDItems.ROSE_PETALS;

public class NaDItemModels extends BlueprintItemModelProvider {

    public NaDItemModels(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), NaturesDelight.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.basicItem(ROSE_PETALS.get());
    }
}
