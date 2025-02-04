package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import static com.doltandtio.naturesdelight.core.registry.NDItems.*;

public class NDItemModels extends BlueprintItemModelProvider {

    public NDItemModels(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), NaturesDelight.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.generatedItem(BLACK_ACORN, APPLE_SLICE, CRUSHED_ICE, SPRUCE_TIPS,
                ROSE_PETALS, ROSE_COOKIE, ROSE_GRANITA, ROSE_ROASTED_ROOTS, ROSE_CORDIAL);

    }
}
