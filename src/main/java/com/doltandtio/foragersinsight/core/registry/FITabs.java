package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.core.registries.Registries;

public class FITabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ForagersInsight.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("foragersinsight", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.foragersinsight"))
                    .icon(() -> new ItemStack(FIItems.HANDBASKET.get()))
                    .displayItems((params, output) -> {
                        // Blocks
                        output.accept(FIBlocks.BOUNTIFUL_DARK_OAK_SAPLING.get());
                        output.accept(FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES.get());
                        output.accept(FIBlocks.BOUNTIFUL_OAK_SAPLING.get());
                        output.accept(FIBlocks.BOUNTIFUL_OAK_LEAVES.get());
                        output.accept(FIBlocks.BOUNTIFUL_SPRUCE_SAPLING.get());
                        output.accept(FIBlocks.BOUNTIFUL_SPRUCE_LEAVES.get());
                        output.accept(FIBlocks.BLACK_ACORN_SACK.get());
                        output.accept(FIBlocks.DANDELION_ROOTS_CRATE.get());
                        output.accept(FIBlocks.POPPY_SEEDS_SACK.get());
                        output.accept(FIBlocks.ROSE_HIP_SACK.get());
                        output.accept(FIBlocks.ROSELLE_CALYX_SACK.get());
                        output.accept(FIBlocks.SPRUCE_TIPS_SACK.get());
                        output.accept(FIBlocks.DENSE_ROSE_PETAL_MAT.get());
                        output.accept(FIBlocks.DENSE_ROSELLE_PETAL_MAT.get());
                        output.accept(FIBlocks.DENSE_SPRUCE_TIP_MAT.get());
                        output.accept(FIBlocks.SCATTERED_ROSE_PETAL_MAT.get());
                        output.accept(FIBlocks.SCATTERED_ROSELLE_PETAL_MAT.get());
                        output.accept(FIBlocks.SCATTERED_SPRUCE_TIP_MAT.get());
                        output.accept(FIBlocks.ROSELLE_BUSH.get());
                        output.accept(FIBlocks.SAPPY_BIRCH_LOG.get());
                        // Items
                            // Cuts and Crushed
                                //Cuts
                        output.accept(FIItems.APPLE_SLICE.get());
                        output.accept(FIItems.COOKED_RABBIT_LEG.get());
                        output.accept(FIItems.RAW_RABBIT_LEG.get());
                        output.accept(FIItems.ROSE_PETALS.get());
                        output.accept(FIItems.ROSELLE_PETALS.get());
                                //Crushed
                        output.accept(FIItems.ACORN_MEAL.get());
                        output.accept(FIItems.COCOA_POWDER.get());
                        output.accept(FIItems.CRUSHED_ICE.get());
                        output.accept(FIItems.POPPY_SEED_PASTE.get());
                        output.accept(FIItems.WHEAT_FLOUR.get());
                            // Crops
                        output.accept(FIItems.BLACK_ACORN.get());
                        output.accept(FIItems.DANDELION_ROOT.get());
                        output.accept(FIItems.POPPY_SEEDS.get());
                        output.accept(FIItems.ROSE_HIP.get());
                        output.accept(FIItems.ROSELLE_CALYX.get());
                        output.accept(FIItems.SPRUCE_TIPS.get());
                            // Ingredients
                        output.accept(FIItems.ACORN_DOUGH.get());
                        output.accept(FIItems.GREEN_SAUCE.get());
                        output.accept(FIItems.SEED_BUTTER.get());
                        output.accept(FIItems.SEED_MILK_BOTTLE.get());
                        output.accept(FIItems.SEED_MILK_BUCKET.get());
                        output.accept(FIItems.BIRCH_SYRUP_BUCKET.get());
                        output.accept(FIItems.BIRCH_SYRUP_BOTTLE.get());

                            // Baked Goods, Cakes, Cookies and Sweets
                        output.accept(FIItems.ACORN_COOKIE.get());
                        output.accept(FIItems.ROSE_COOKIE.get());
                        output.accept(FIItems.BLACK_FOREST_MUFFIN.get());
                        output.accept(FIItems.RED_VELVET_CUPCAKE.get());
                        output.accept(FIItems.POPPY_SEED_BAGEL.get());
                        output.accept(FIItems.SLICE_OF_ACORN_CARROT_CAKE.get().getDefaultInstance());
                        output.accept(FIItems.ACORN_CARROT_CAKE_ITEM.get().getDefaultInstance());
                        output.accept(FIItems.CANDIED_CALYCES.get());
                            // Dishes
                        output.accept(FIItems.ACORN_NOODLES.get());
                        output.accept(FIItems.CARROT_POPPY_CHOWDER.get());
                        output.accept(FIItems.COD_AND_PUMPKIN_STEW.get());
                        output.accept(FIItems.GLAZED_PORKCHOP_AND_ACORN_GRITS.get());
                        output.accept(FIItems.FORAGERS_GRANOLA.get());
                        output.accept(FIItems.HEARTY_SPRUCE_PILAF.get());
                        output.accept(FIItems.KELP_AND_BEET_SALAD.get());
                        output.accept(FIItems.MEADOW_MEDLEY.get());
                        output.accept(FIItems.ROSE_HIP_SOUP.get());
                        output.accept(FIItems.ROSE_ROASTED_ROOTS.get());
                        output.accept(FIItems.SEASIDE_SIZZLER.get());
                        output.accept(FIItems.STEAMY_KELP_RICE.get());
                        output.accept(FIItems.SYRUP_TOAST_STACKS.get());
                        output.accept(FIItems.WOODLAND_PASTA.get());
                            //Sandwiches and Finger Foods
                        output.accept(FIItems.CREAMY_SALMON_BAGEL.get());
                        output.accept(FIItems.DANDELION_FRIES.get());
                        output.accept(FIItems.JAMMY_BREAKFAST_SANDWICH.get());
                        output.accept(FIItems.KELP_WRAP.get());
                        output.accept(FIItems.SEED_BUTTER_JAMWICH.get());
                        output.accept(FIItems.SWEET_ROASTED_RABBIT_LEG.get());
                            // Drinks
                        output.accept(FIItems.DANDELION_ROOT_TEA.get());
                        output.accept(FIItems.FOREST_ELIXIR.get());
                        output.accept(FIItems.GLOWING_CARROT_JUICE.get());
                        output.accept(FIItems.ROSE_CORDIAL.get());
                            //Tools
                        output.accept(FIItems.HANDBASKET.get());
                        output.accept(FIItems.FLINT_MALLET.get());
                        output.accept(FIItems.IRON_MALLET.get());
                        output.accept(FIItems.GOLD_MALLET.get());
                        output.accept(FIItems.DIAMOND_MALLET.get());
                        output.accept(FIItems.NETHERITE_MALLET.get());
                        output.accept(FIItems.FLINT_SHEARS.get());
                        output.accept(FIItems.TAPPER.get());
                            //Other
                        output.accept(FIItems.BIRCH_SAP_BUCKET.get());
                        output.accept(FIItems.BIRCH_SAP_BOTTLE.get());


                    })
                    .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}