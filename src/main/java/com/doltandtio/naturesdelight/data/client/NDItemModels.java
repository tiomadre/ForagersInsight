package com.doltandtio.naturesdelight.data.client;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import static com.doltandtio.naturesdelight.core.registry.NDItems.*;

public class NDItemModels extends BlueprintItemModelProvider {

    public NDItemModels(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), NaturesDelight.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.generatedItem(ACORN_COOKIE, APPLE_SLICE, BLACK_ACORN, COD_AND_PUMPKIN_STEW, COOKED_RABBIT_LEG, CRUSHED_ICE,
                KELP_AND_BEET_SALAD, KELP_WRAP,MEADOW_MEDLEY,POPPY_SEEDS,POPPY_SEED_PASTE, RAW_RABBIT_LEG, RED_VELVET_CUPCAKE, ROSE_PETALS, ROSE_COOKIE,ROSE_CORDIAL,ROSE_HIP,ROSE_GRANITA,
                ROSE_ROASTED_ROOTS,SEASIDE_SIZZLER,SEED_MILK_BOTTLE,SEED_MILK_BUCKET,SPRUCE_TIPS,STEAMY_KELP_RICE,SUNFLOWER_KERNELS);

    }
}
