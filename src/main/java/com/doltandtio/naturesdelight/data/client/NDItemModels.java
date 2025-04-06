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
        this.generatedItem(
                //BAKED GOODS, COOKIES AND SWEETS
                    //Baked Goods
                BLACK_FOREST_MUFFIN,POPPY_SEED_BAGEL,RED_VELVET_CUPCAKE,
                    //Cake and Pie Slices
                SLICE_OF_ACORN_CARROT_CAKE,
                    //Cookies
                ACORN_COOKIE,ROSE_COOKIE,
                    //Sweets

                //Crops
                BLACK_ACORN,DANDELION_ROOT,POPPY_SEEDS,ROSE_HIP,SPRUCE_TIPS,SUNFLOWER_KERNELS,
                //Cuts + Knife Drops
                APPLE_SLICE,COOKED_RABBIT_LEG,RAW_RABBIT_LEG,ROSE_PETALS,
                //Crushed + Ingredients
                ACORN_MEAL,COCOA_POWDER,CRUSHED_ICE,GREEN_SAUCE,POPPY_SEED_PASTE,SUNFLOWER_BUTTER,WHEAT_FLOUR,
                //Dishes
                    //Chilled
                ROSE_GRANITA,
                    //Comfort
                COD_AND_PUMPKIN_STEW,FORAGERS_GRANOLA,STEAMY_KELP_RICE,
                    //Medicinal

                    //Nourishment
                ACORN_NOODLES,ROSE_ROASTED_ROOTS,SEASIDE_SIZZLER,
                //Salad
                KELP_AND_BEET_SALAD,MEADOW_MEDLEY,
                //Sandwiches + Finger Foods
                DANDELION_FRIES,KELP_WRAP,SEED_BUTTER_JAMWICH,
                    //Comfort
                CREAMY_SALMON_BAGEL,JAMMY_BREAKFAST_SANDWICH,
                //Drinks
                ROSE_CORDIAL,
                    //Chilled

                    //Medicinal
                    DANDELION_ROOT_TEA,
                //Other
                ACORN_DOUGH,SEED_MILK_BOTTLE,SEED_MILK_BUCKET,
                //Tools and Workstations
                FLINT_SHEARS        );

    }
}
