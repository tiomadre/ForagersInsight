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
        this.generatedItem(
                //Crops
                BLACK_ACORN,DANDELION_ROOT,POPPY_SEEDS,ROSE_HIP,SPRUCE_TIPS,SUNFLOWER_KERNELS,
                //Cuts + Knife Drops
                APPLE_SLICE,COOKED_RABBIT_LEG,RAW_RABBIT_LEG,ROSE_PETALS,
                //Crushed + Mallet Drops
                ACORN_MEAL,COCOA_POWDER,CRUSHED_ICE,POPPY_SEED_PASTE,WHEAT_FLOUR,
                //Dishes
                COD_AND_PUMPKIN_STEW, KELP_AND_BEET_SALAD, KELP_WRAP,MEADOW_MEDLEY,ROSE_GRANITA,
                ROSE_ROASTED_ROOTS,SEASIDE_SIZZLER,STEAMY_KELP_RICE,
                //Drinks
                ROSE_CORDIAL,SEED_MILK_BOTTLE,SEED_MILK_BUCKET,
                //Sweets
                ACORN_COOKIE,RED_VELVET_CUPCAKE,ROSE_COOKIE);
    }
}
