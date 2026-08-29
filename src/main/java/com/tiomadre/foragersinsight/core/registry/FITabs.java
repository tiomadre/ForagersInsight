package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FITabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ForagersInsight.MOD_ID);

    public static final Supplier<CreativeModeTab> FORAGERS_INSIGHT = TABS.register("foragersinsight", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.foragersinsight"))
                    .icon(() -> new ItemStack(FIBlocks.BLOSSOMING_LILAC_LEAVES.get()))
                    .displayItems((parameters, output) -> displayEntries(output))
                    .build());

    private static final List<Supplier<? extends ItemLike>> BLOCK_ENTRIES = List.of(

            FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES,
            FIBlocks.BOUNTIFUL_OAK_LEAVES,
            FIBlocks.BOUNTIFUL_SPRUCE_LEAVES,
            FIBlocks.BLOSSOMING_LILAC_LEAVES,
            FIBlocks.LILAC_LEAVES,

            FIBlocks.APPLE_CRATE,
            FIBlocks.BLEWIT_CRATE,
            FIBlocks.LILAC_BLOOM_CRATE,
            FIBlocks.TINDER_CONK_CRATE,
            FIBlocks.BLACK_ACORN_SACK,
            FIBlocks.DANDELION_ROOT_SACK,
            FIBlocks.POPPY_SEEDS_SACK,
            FIBlocks.ROSE_HIP_SACK,
            FIBlocks.ROSELLE_CALYX_SACK,
            FIBlocks.SPRUCE_TIPS_SACK,

            FIBlocks.DENSE_LILAC_BLOOM_MAT,
            FIBlocks.DENSE_ROSE_PETAL_MAT,
            FIBlocks.DENSE_ROSELLE_PETAL_MAT,
            FIBlocks.DENSE_SPRUCE_TIP_MAT,
            FIBlocks.DENSE_STRAW_MAT,
            FIBlocks.SCATTERED_LILAC_BLOOM_MAT,
            FIBlocks.SCATTERED_ROSE_PETAL_MAT,
            FIBlocks.SCATTERED_ROSELLE_PETAL_MAT,
            FIBlocks.SCATTERED_SPRUCE_TIP_MAT,
            FIBlocks.SCATTERED_STRAW_MAT,

            FIBlocks.GHOST_PIPE_TORCH,

            FIBlocks.BLEWIT_MUSHROOM,
            FIBlocks.BLEWIT_MUSHROOM_COLONY,
            FIItems.GHOST_PIPE_ITEM,
            FIItems.ROSELLE_BUSH_ITEM,
            FIItems.STOUT_BEACH_ROSE_BUSH_ITEM,
            FIItems.TALL_BEACH_ROSE_BUSH_ITEM,
            FIItems.WOODLAND_FERN_ITEM,
            FIItems.SKUNK_CABBAGE_ITEM,
            FIItems.TINDER_CONK,
            FIItems.TINDER_CONK_SPORES,


            FIBlocks.CONDENSED_DIRT,
            FIBlocks.CONDENSED_SAND,
            FIBlocks.HOLLOW_LOG,
            FIBlocks.SUSPICIOUS_LEAF_LITTER


    );

    private static final List<Supplier<? extends ItemLike>> INGREDIENT_ENTRIES = List.of(
            FIItems.AMADOU,
            FIItems.APPLE_SLICE,
            FIItems.BLEWIT_MUSHROOM,
            FIItems.COOKED_RABBIT_LEG,
            FIItems.RAW_RABBIT_LEG,
            FIItems.ROSE_PETALS,
            FIItems.ROSELLE_PETALS,
            FIItems.ACORN_MEAL,
            FIItems.COCOA_POWDER,
            FIItems.CRUSHED_ICE,
            FIItems.POPPY_SEED_PASTE,
            FIItems.WHEAT_FLOUR,
            FIItems.BLACK_ACORN,
            FIItems.DANDELION_ROOT,
            FIItems.LILAC_BLOOM,
            FIItems.POPPY_SEEDS,
            FIItems.ROSE_HIP,
            FIItems.ROSELLE_CALYX,
            FIItems.SPRUCE_TIPS,
            FIItems.ACORN_DOUGH,
            FIItems.GREEN_SAUCE,
            FIItems.SEED_BUTTER,
            FIItems.SEED_MILK_BOTTLE,
            FIItems.SEED_MILK_BUCKET,
            FIItems.BIRCH_SYRUP_BUCKET,
            FIItems.BIRCH_SYRUP_BOTTLE,
            FIItems.BIRCH_SAP_BUCKET,
            FIItems.BIRCH_SAP_BOTTLE
    );

    private static final List<Supplier<? extends ItemLike>> CUISINE_ENTRIES = List.of(
            FIItems.ACORN_COOKIE,
            FIItems.ROSE_COOKIE,
            FIItems.BLACK_FOREST_MUFFIN,
            FIItems.RED_VELVET_CUPCAKE,
            FIItems.POPPY_SEED_BAGEL,
            FIItems.LILAC_TEACAKE,
            FIItems.SLICE_OF_ACORN_CARROT_CAKE,
            FIItems.ACORN_CARROT_CAKE_ITEM,
            FIItems.SLICE_OF_RAINBOW_SANDWICH,
            FIItems.RAINBOW_SANDWICH_ITEM,
            FIItems.APPLE_DIPPERS,
            FIItems.CANDIED_CALYCES,
            FIItems.ACORN_NOODLES,
            FIItems.CARROT_POPPY_CHOWDER,
            FIItems.BLEWIT_BITES,
            FIItems.COD_AND_PUMPKIN_STEW,
            FIItems.GLAZED_PORKCHOP_AND_ACORN_GRITS,
            FIItems.FORAGERS_GRANOLA,
            FIItems.HEARTY_SPRUCE_PILAF,
            FIItems.KELP_AND_BEET_SALAD,
            FIItems.MEADOW_MEDLEY,
            FIItems.LILAC_SALAD,
            FIItems.ROSE_HIP_SOUP,
            FIItems.ROSE_ROASTED_ROOTS,
            FIItems.SAVORY_PASTA_ROLL,
            FIItems.SEASIDE_SIZZLER,
            FIItems.STEAMY_KELP_RICE,
            FIItems.SYRUP_TOAST_STACKS,
            FIItems.WOODLAND_PASTA,
            FIItems.TART_WHEAT_PILAF,
            FIItems.CREAMY_SALMON_BAGEL,
            FIItems.DANDELION_FRIES,
            FIItems.JAMMY_BREAKFAST_SANDWICH,
            FIItems.KELP_WRAP,
            FIItems.SEED_BUTTER_JAMWICH,
            FIItems.SWEET_ROASTED_RABBIT_LEG,
            FIItems.AUSPICIOUS_STEW,
            FIItems.DANDELION_ROOT_TEA,
            FIItems.FOREST_ELIXIR,
            FIItems.GLOWING_CARROT_JUICE,
            FIItems.ROSE_CORDIAL,
            FIItems.ROSELLE_JUICE
    );

    private static final List<Supplier<? extends ItemLike>> TOOL_ENTRIES = List.of(
            FIItems.DIFFUSER,
            FIItems.HANDBASKET,
            FIItems.FLINT_SHEARS,
            FIItems.FLINT_MALLET,
            FIItems.IRON_MALLET,
            FIItems.GOLD_MALLET,
            FIItems.DIAMOND_MALLET,
            FIItems.NETHERITE_MALLET,
            FIBlocks.SAP_TRAP,
            FIItems.STROP,
            FIItems.TAPPER,
            FIItems.AMADOU_CAP

    );
    private static final List<Supplier<? extends ItemLike>> WOODEN_ENTRIES = List.of(
            //BIRCH
            FIBlocks.SAPPY_BIRCH_LOG,
            FIBlocks.STRIPPED_SAPPY_BIRCH_LOG,
            //LILAC
            FIBlocks.LILAC_LOG,
            FIBlocks.STRIPPED_LILAC_LOG,
            FIBlocks.LILAC_PLANKS,
            FIBlocks.LILAC_STAIRS,
            FIBlocks.LILAC_SLAB,
            FIBlocks.LILAC_FENCE,
            FIBlocks.LILAC_FENCE_GATE,
            FIBlocks.LILAC_DOOR,
            FIBlocks.LILAC_TRAPDOOR,
            FIBlocks.LILAC_PRESSURE_PLATE,
            FIBlocks.LILAC_BUTTON,
            FIItems.LILAC_SIGN,
            FIItems.LILAC_HANGING_SIGN,
            FIBlocks.LILAC_CABINET,
            FIItems.LILAC_BOAT,
            FIItems.LILAC_CHEST_BOAT
    );

    private static final List<Supplier<MobEffect>> AUSPICIOUS_STEW_EFFECTS = List.of(
            FIMobEffects.BLOOM.,  MobEffects.DAMAGE_RESISTANCE, () -> MobEffects.HEALTH_BOOST, () -> MobEffects.REGENERATION);


    public static void register(IEventBus bus) {
        TABS.register(bus);
    }

    private static void displayEntries(CreativeModeTab.Output output) {
        Stream.of(BLOCK_ENTRIES, INGREDIENT_ENTRIES, CUISINE_ENTRIES, TOOL_ENTRIES, WOODEN_ENTRIES)
                .flatMap(Collection::stream)
                .map(Supplier::get)
                .map(ItemStack::new)
                .forEach(output::accept);

        AUSPICIOUS_STEW_EFFECTS.stream()
                .map(Supplier::get)
                .map(FITabs::createAuspiciousStew)
                .forEach(output::accept);

    }

    private static ItemStack createAuspiciousStew(MobEffect effect) {
        ItemStack stack = new ItemStack(FIItems.AUSPICIOUS_STEW.get());


        //Check back on this later, had to redo the method cause no more item stack compound tags
     DataComponentType<MobEffect> effectcomponent= DataComponentType.<MobEffect>builder().build();
        if (effect != null) {
            stack.getOrDefault(effectcomponent,effect);

        }
        return stack;
    }

}