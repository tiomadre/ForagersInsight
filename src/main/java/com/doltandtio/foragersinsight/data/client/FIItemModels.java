package com.doltandtio.foragersinsight.data.client;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import static com.doltandtio.foragersinsight.core.registry.FIItems.*;

public class FIItemModels extends BlueprintItemModelProvider {

    public FIItemModels(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), ForagersInsight.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.generatedItem(
                //BAKED GOODS, COOKIES AND SWEETS
                    //Baked Goods
                BLACK_FOREST_MUFFIN,POPPY_SEED_BAGEL,RED_VELVET_CUPCAKE,
                    //Cake and Pie Slices
                SLICE_OF_ACORN_CARROT_CAKE,ACORN_CARROT_CAKE_ITEM,
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
                    //Comfort
                COD_AND_PUMPKIN_STEW,FORAGERS_GRANOLA,STEAMY_KELP_RICE,ROSE_HIP_SOUP,
                    //Medicinal

                    //Nourishment
                ACORN_NOODLES,GLAZED_PORKCHOP_AND_ACORN_GRITS,ROSE_ROASTED_ROOTS,SEASIDE_SIZZLER,SYRUP_TOAST_STACKS,
                //Salad
                KELP_AND_BEET_SALAD,MEADOW_MEDLEY,
                //Sandwiches + Finger Foods
                DANDELION_FRIES,KELP_WRAP,SEED_BUTTER_JAMWICH,
                    //Comfort
                CREAMY_SALMON_BAGEL,JAMMY_BREAKFAST_SANDWICH,
                //Drinks
                GLOWING_CARROT_JUICE,ROSE_CORDIAL,
                    //Chilled

                    //Medicinal
                    DANDELION_ROOT_TEA, FOREST_ELIXIR,
                //Other
                ACORN_DOUGH, BIRCH_SAP_BUCKET,BIRCH_SAP_BOTTLE, BIRCH_SYRUP_BUCKET,BIRCH_SYRUP_BOTTLE, SEED_MILK_BOTTLE,
                SEED_MILK_BUCKET, SWEET_ROASTED_RABBIT_LEG
                ,CARROT_POPPY_CHOWDER,HEARTY_SPRUCE_PILAF,WOODLAND_PASTA,
                //Tools and Workstations
                HANDBASKET, FLINT_MALLET, IRON_MALLET, GOLD_MALLET, DIAMOND_MALLET, NETHERITE_MALLET, FLINT_SHEARS, TAPPER

                );

    }
}
