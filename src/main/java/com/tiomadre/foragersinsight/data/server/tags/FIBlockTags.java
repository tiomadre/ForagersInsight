package com.tiomadre.foragersinsight.data.server.tags;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.CommonTags;

import static com.tiomadre.foragersinsight.core.registry.FIBlocks.*;
import static com.tiomadre.foragersinsight.data.server.tags.FITags.BlockTag.*;


public class FIBlockTags extends BlockTagsProvider {

    public FIBlockTags(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), ForagersInsight.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.LOGS).add(SAPPY_BIRCH_LOG.get(), STRIPPED_SAPPY_BIRCH_LOG.get(), HOLLOW_LOG.get(), LILAC_LOG.get(), STRIPPED_LILAC_LOG.get());

        this.tag(BlockTags.LOGS_THAT_BURN).add(SAPPY_BIRCH_LOG.get(), STRIPPED_SAPPY_BIRCH_LOG.get(), HOLLOW_LOG.get(), LILAC_LOG.get(), STRIPPED_LILAC_LOG.get());

        this.tag(BlockTags.PLANKS).add(LILAC_PLANKS.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(LILAC_STAIRS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(LILAC_SLAB.get());
        this.tag(BlockTags.WOODEN_FENCES).add(LILAC_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(LILAC_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(LILAC_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(LILAC_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(LILAC_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(LILAC_BUTTON.get());
        this.tag(BlockTags.SIGNS).add(LILAC_SIGN.get(), LILAC_WALL_SIGN.get());
        this.tag(BlockTags.STANDING_SIGNS).add(LILAC_SIGN.get());
        this.tag(BlockTags.WALL_SIGNS).add(LILAC_WALL_SIGN.get());
        this.tag(BlockTags.ALL_HANGING_SIGNS).add(LILAC_HANGING_SIGN.get(), LILAC_WALL_HANGING_SIGN.get());
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(LILAC_HANGING_SIGN.get());
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(LILAC_WALL_HANGING_SIGN.get());

        this.tag(BlockTags.LEAVES).add(BOUNTIFUL_OAK_LEAVES.get(),BOUNTIFUL_DARK_OAK_LEAVES.get(),BOUNTIFUL_SPRUCE_LEAVES.get());
        this.tag(BlockTags.CROPS).add(ROSE_CROP.get(), DANDELION_BUSH.get(), POPPY_BUSH.get(),BOUNTIFUL_DARK_OAK_LEAVES.get(),BOUNTIFUL_OAK_LEAVES.get(),BOUNTIFUL_SPRUCE_TIPS.get(),HANGING_LILAC_LEAVES.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(STOUT_BEACH_ROSE_BUSH.get(), GHOST_PIPE.get());
        this.tag(BlockTags.TALL_FLOWERS).add(ROSELLE_BUSH.get(), TALL_BEACH_ROSE_BUSH.get());
        this.tag(BlockTags.FLOWERS).add(ROSELLE_BUSH.get(), STOUT_BEACH_ROSE_BUSH.get(), TALL_BEACH_ROSE_BUSH.get(), GHOST_PIPE.get(), DANDELION_BUSH.get(),
        POPPY_BUSH.get(), ROSE_CROP.get(), BOUNTIFUL_DARK_OAK_LEAVES.get(), BOUNTIFUL_OAK_LEAVES.get(), BOUNTIFUL_SPRUCE_TIPS.get());

        this.tag(SHEARABLE_CROPS)
                .add(HANGING_LILAC_LEAVES.get(), BOUNTIFUL_OAK_LEAVES.get(), BOUNTIFUL_DARK_OAK_LEAVES.get(), BOUNTIFUL_SPRUCE_LEAVES.get(), BOUNTIFUL_SPRUCE_TIPS.get())
                .add(Blocks.KELP, Blocks.KELP_PLANT, Blocks.SUGAR_CANE, Blocks.SWEET_BERRY_BUSH)
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "brown_mushroom_colony"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "red_mushroom_colony"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "tomato_vine"));
        this.tag(RICH_SOIL_TREE_SAPLINGS)
                .add(Blocks.LILAC)
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("minecraft", "saplings"));
        this.tag(FORAGING)
        //FD Wild Crops
        .add(ModBlocks.WILD_BEETROOTS.get(),ModBlocks.WILD_CABBAGES.get(),ModBlocks.WILD_CARROTS.get(),
        ModBlocks.WILD_ONIONS.get(),ModBlocks.WILD_POTATOES.get(),ModBlocks.WILD_TOMATOES.get(),ModBlocks.WILD_RICE.get(),
        //Grasses
        ModBlocks.SANDY_SHRUB.get(),Blocks.TALL_GRASS,Blocks.GRASS_BLOCK,
        //Vanilla & Forager Wild Flowers
        Blocks.ROSE_BUSH,Blocks.DANDELION,Blocks.POPPY,STOUT_BEACH_ROSE_BUSH.get(), TALL_BEACH_ROSE_BUSH.get(),ROSELLE_BUSH.get());
        registerNeoForgeTags();
        registerMineables();

    }

    protected void registerMineables() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TAPPER.get(), DIFFUSER.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(CONDENSED_DIRT.get(),(CONDENSED_SAND.get()));
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(APPLE_CRATE.get(), BLEWIT_CRATE.get(),TINDER_CONK_CRATE.get(), LILAC_BLOOM_CRATE.get(), SAPPY_BIRCH_LOG.get(),STRIPPED_SAPPY_BIRCH_LOG.get(),  LILAC_LOG.get(), STRIPPED_LILAC_LOG.get(), LILAC_PLANKS.get(), LILAC_STAIRS.get(),
                LILAC_SLAB.get(), LILAC_FENCE.get(), LILAC_FENCE_GATE.get(), LILAC_DOOR.get(), LILAC_TRAPDOOR.get(), LILAC_PRESSURE_PLATE.get(), LILAC_BUTTON.get(),
                LILAC_SIGN.get(), LILAC_WALL_SIGN.get(), LILAC_HANGING_SIGN.get(), LILAC_WALL_HANGING_SIGN.get(), LILAC_CABINET.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(BOUNTIFUL_OAK_LEAVES.get(),BOUNTIFUL_DARK_OAK_LEAVES.get(),BOUNTIFUL_SPRUCE_LEAVES.get());
        this.tag(CommonTags.Blocks.MINEABLE_WITH_KNIFE).add(DANDELION_ROOT_SACK.get(),ROSELLE_CALYX_SACK.get(), ROSE_HIP_SACK.get(), POPPY_SEEDS_SACK.get(), SPRUCE_TIPS_SACK.get(), BLACK_ACORN_SACK.get());
    }


    protected void registerNeoForgeTags() {
        tag(STORAGE_BLOCK_ROSE_HIP).add(ROSE_HIP_SACK.get());
        tag(STORAGE_BLOCK_POPPY_SEEDS).add(POPPY_SEEDS_SACK.get());
        tag(STORAGE_BLOCK_APPLE).add(Block.byItem(Items.APPLE));
        tag(STORAGE_BLOCK_DANDELION_ROOT).add(DANDELION_ROOT_SACK.get());
        tag(STORAGE_BLOCK_SPRUCE_TIPS).add(SPRUCE_TIPS_SACK.get());
        tag(STORAGE_BLOCK_BLACK_ACORNS).add(BLACK_ACORN_SACK.get());
        tag(STORAGE_BLOCK_BLEWIT_MUSHROOM).add(BLEWIT_CRATE.get());
        tag(STORAGE_BLOCK_TINDER_CONK).add(TINDER_CONK_CRATE.get());

    }


}