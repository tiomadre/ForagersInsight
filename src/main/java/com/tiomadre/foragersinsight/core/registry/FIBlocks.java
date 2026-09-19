package com.tiomadre.foragersinsight.core.registry;

import com.teamabnormals.blueprint.common.block.sign.BlueprintCeilingHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.tiomadre.foragersinsight.common.block.*;
import com.tiomadre.foragersinsight.common.block.feasts.RainbowSandwichFeastBlock;
import com.tiomadre.foragersinsight.common.block.feasts.SliceableCakeBlock;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.teamabnormals.blueprint.common.block.LogBlock;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import static com.tiomadre.foragersinsight.core.registry.FIItems.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FIBlocks {
    public static final BlockSubRegistryHelper HELPER = ForagersInsight.REGISTRY_HELPER.getBlockSubHelper();
    private static BlockBehaviour.Properties vanillaLeafProperties(Block vanillaLeaf) {
        return ofFullCopy(vanillaLeaf)
                .noOcclusion()
                .isValidSpawn((state, level, pos, type) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false);
    }

    //Cakes and Feasts
    public static final DeferredBlock<Block> ACORN_CARROT_CAKE = HELPER.createBlockNoItem("acorn_carrot_cake",
            () -> new SliceableCakeBlock(ofFullCopy(Blocks.CAKE), FIItems.SLICE_OF_ACORN_CARROT_CAKE));
    public static final DeferredBlock<Block> RAINBOW_SANDWICH = HELPER.createBlockNoItem("rainbow_sandwich",
            () -> new RainbowSandwichFeastBlock(ofFullCopy(Blocks.CAKE).noOcclusion(), FIItems.SLICE_OF_RAINBOW_SANDWICH));
    //Crops
        //Flower
    public static final DeferredBlock<Block> POPPY_BUSH = HELPER.createBlockNoItem("poppy_bush", () ->
            new PoppyBushBlock(ofFullCopy(Blocks.BEETROOTS)));
    public static final DeferredBlock<Block> DANDELION_BUSH = HELPER.createBlockNoItem("dandelion_bush", () ->
            new DandelionBushBlock(ofFullCopy(Blocks.POTATOES)));
    public static final DeferredBlock<Block> ROSE_CROP = HELPER.createBlockNoItem("rose_crop", () -> new RoseCropBlock(
            ofFullCopy(Blocks.WHEAT), 3));
    public static final DeferredBlock<Block> ROSELLE_CROP = HELPER.createBlockNoItem("roselle_crop", () -> new RoselleCropBlock(
            ofFullCopy(Blocks.WHEAT), 3));
    public static final DeferredBlock<Block> PHLOX = HELPER.createBlock("phlox", () ->
            new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS)));

    //Mushrooms
    public static final DeferredBlock<Block> BLEWIT_MUSHROOM_COLONY = HELPER.createBlock("blewit_mushroom_colony", () ->
            new MushroomColonyBlock(FIItems.BLEWIT_MUSHROOM,Block.Properties.ofFullCopy(Blocks.RED_MUSHROOM)));
    public static final DeferredBlock<Block> BLEWIT_MUSHROOM = HELPER.createBlockNoItem("blewit_mushroom", () ->
            new WildMushroomBlock(ofFullCopy(Blocks.RED_MUSHROOM), BLEWIT_MUSHROOM_COLONY));
    public static final DeferredBlock<Block> WALL_RED_MUSHROOM_COLONY = HELPER.createBlockNoItem("wall_red_mushroom_colony", () ->
            new WallMushroomColonyBlock(ofFullCopy(ModBlocks.RED_MUSHROOM_COLONY.get()), Items.RED_MUSHROOM.builtInRegistryHolder()));
    public static final DeferredBlock<Block> WALL_BROWN_MUSHROOM_COLONY = HELPER.createBlockNoItem("wall_brown_mushroom_colony", () ->
            new WallMushroomColonyBlock(ofFullCopy(ModBlocks.BROWN_MUSHROOM_COLONY.get()), Items.BROWN_MUSHROOM.builtInRegistryHolder()));
    public static final DeferredBlock<Block> WALL_BLEWIT_MUSHROOM_COLONY = HELPER.createBlockNoItem("wall_blewit_mushroom_colony", () ->
            new WallMushroomColonyBlock(ofFullCopy(FIBlocks.BLEWIT_MUSHROOM_COLONY.get()), FIItems.BLEWIT_MUSHROOM));
    public static final DeferredBlock<Block> WALL_RED_MUSHROOM = HELPER.createBlockNoItem("wall_red_mushroom", () ->
            new WallMushroomBlock(ofFullCopy(Blocks.RED_MUSHROOM), WALL_RED_MUSHROOM_COLONY));
    public static final DeferredBlock<Block> WALL_BROWN_MUSHROOM = HELPER.createBlockNoItem("wall_brown_mushroom", () ->
            new WallMushroomBlock(ofFullCopy(Blocks.BROWN_MUSHROOM), WALL_BROWN_MUSHROOM_COLONY));
    public static final DeferredBlock<Block> WALL_BLEWIT_MUSHROOM = HELPER.createBlockNoItem("wall_blewit_mushroom", () ->
            new WallMushroomBlock(ofFullCopy(FIBlocks.BLEWIT_MUSHROOM.get()), WALL_BLEWIT_MUSHROOM_COLONY));
        //Unique Mushrooms
        public static final DeferredBlock<Block> TINDER_CONK = HELPER.createBlockNoItem("tinder_conk", () ->
            new TinderConkBlock(ofFullCopy(Blocks.BROWN_MUSHROOM).randomTicks().noOcclusion()));

    //Trees
    //Oak
    public static final DeferredBlock<Block> BOUNTIFUL_OAK_LEAVES = HELPER.createBlock("bountiful_oak_leaves", () ->
            new BountifulLeavesBlock(vanillaLeafProperties(Blocks.OAK_LEAVES), () -> Items.APPLE));
    //Dark Oak
    public static final DeferredBlock<Block> BOUNTIFUL_DARK_OAK_LEAVES = HELPER.createBlock("bountiful_dark_oak_leaves", () ->
            new BountifulLeavesBlock(vanillaLeafProperties(Blocks.DARK_OAK_LEAVES), BLACK_ACORN));
    //Spruce
    public static final DeferredBlock<Block> BOUNTIFUL_SPRUCE_LEAVES = HELPER.createBlock("bountiful_spruce_leaves", () ->
            new BountifulSpruceLeavesBlock(vanillaLeafProperties(Blocks.SPRUCE_LEAVES)));
    public static final DeferredBlock<Block> BOUNTIFUL_SPRUCE_TIPS = HELPER.createBlockNoItem("bountiful_spruce_tips", () ->
            new SpruceTipBlock(ofFullCopy(Blocks.SWEET_BERRY_BUSH).noCollission()));
    //Birch
    public static final DeferredBlock<Block> SAPPY_BIRCH_LOG = HELPER.createBlock("sappy_birch_log", () ->
            new LogBlock(FIBlocks.STRIPPED_SAPPY_BIRCH_LOG, ofFullCopy(Blocks.BIRCH_LOG)));
    public static final DeferredBlock<Block> STRIPPED_SAPPY_BIRCH_LOG = HELPER.createBlock("stripped_sappy_birch_log", () ->
            new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_BIRCH_LOG)));
    //Lilac
    public static final DeferredBlock<Block> LILAC_LEAVES = HELPER.createBlock("lilac_leaves", () ->
            new LeavesBlock(vanillaLeafProperties(Blocks.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> BLOSSOMING_LILAC_LEAVES = HELPER.createBlock("blossoming_lilac_leaves", () ->
            new BlossomingLilacLeavesBlock(vanillaLeafProperties(Blocks.FLOWERING_AZALEA_LEAVES)));
    public static final DeferredBlock<Block> HANGING_LILAC_LEAVES = HELPER.createBlockNoItem("hanging_lilac_leaves", () ->
            new HangingLilacLeavesBlock(ofFullCopy(Blocks.AZALEA_LEAVES).noCollission()));
    public static final DeferredBlock<Block> LILAC_LOG = HELPER.createBlock("lilac_log", () ->
            new ThinLogBlock(FIBlocks.STRIPPED_LILAC_LOG, ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<Block> STRIPPED_LILAC_LOG = HELPER.createBlock("stripped_lilac_log", () ->
            new ThinLogBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).noOcclusion()));
    //Wood Stuff
        //Lilac
    public static final DeferredBlock<Block> LILAC_PLANKS = HELPER.createBlock("lilac_planks", () ->
            new Block(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> LILAC_STAIRS = HELPER.createBlock("lilac_stairs", () ->
            new StairBlock(LILAC_PLANKS.get().defaultBlockState(), ofFullCopy(Blocks.OAK_STAIRS)));
    public static final DeferredBlock<Block> LILAC_SLAB = HELPER.createBlock("lilac_slab", () ->
            new SlabBlock(ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<Block> LILAC_FENCE = HELPER.createBlock("lilac_fence", () ->
            new FenceBlock(ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<Block> LILAC_FENCE_GATE = HELPER.createBlock("lilac_fence_gate", () ->
            new FenceGateBlock(FIWoodTypes.LILAC, ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<Block> LILAC_DOOR = HELPER.createBlock("lilac_door", () ->
            new DoorBlock(FIWoodTypes.LILAC.setType(), ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<Block> LILAC_TRAPDOOR = HELPER.createBlock("lilac_trapdoor", () ->
            new TrapDoorBlock( FIWoodTypes.LILAC.setType(),ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<Block> LILAC_PRESSURE_PLATE = HELPER.createBlock("lilac_pressure_plate", () ->
            new PressurePlateBlock( FIWoodTypes.LILAC.setType(), ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<Block> LILAC_BUTTON = HELPER.createBlock("lilac_button", () ->
            new ButtonBlock(FIWoodTypes.LILAC.setType(), 30,ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<BlueprintStandingSignBlock> LILAC_SIGN = HELPER.createBlockNoItem("lilac_sign", () ->
            new BlueprintStandingSignBlock(ofFullCopy(Blocks.OAK_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<BlueprintWallSignBlock> LILAC_WALL_SIGN = HELPER.createBlockNoItem("lilac_wall_sign", () ->
            new BlueprintWallSignBlock(ofFullCopy(Blocks.OAK_WALL_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<BlueprintCeilingHangingSignBlock> LILAC_HANGING_SIGN = HELPER.createBlockNoItem("lilac_hanging_sign", () ->
            new BlueprintCeilingHangingSignBlock(ofFullCopy(Blocks.OAK_HANGING_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<BlueprintWallHangingSignBlock> LILAC_WALL_HANGING_SIGN = HELPER.createBlockNoItem("lilac_wall_hanging_sign", () ->
            new BlueprintWallHangingSignBlock(ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<CabinetBlock> LILAC_CABINET = HELPER.createBlock("lilac_cabinet", () ->
            new CabinetBlock(ofFullCopy(ModBlocks.OAK_CABINET.get())));

        //Syrup Tap
    public static final DeferredBlock<Block> TAPPER = HELPER.createBlockNoItem("tapper", () ->
            new TapperBlock(ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    //Diffuser
    public static final DeferredBlock<Block> DIFFUSER = HELPER.createBlockNoItem("diffuser",
            () -> new DiffuserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).noOcclusion()));
    //DECORATIVE
        //Foliage Mats
    public static final DeferredBlock<Block> SCATTERED_LILAC_BLOOM_MAT = HELPER.createBlock("scattered_lilac_blooms", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_ROSE_PETAL_MAT = HELPER.createBlock("scattered_rose_petals", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_ROSELLE_PETAL_MAT = HELPER.createBlock("scattered_roselle_petals", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_SPRUCE_TIP_MAT = HELPER.createBlock("scattered_spruce_tips", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_STRAW_MAT = HELPER.createBlock("scattered_straw", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_LILAC_BLOOM_MAT = HELPER.createBlock("dense_lilac_blooms", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_STRAW_MAT = HELPER.createBlock("dense_straw", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_SPRUCE_TIP_MAT = HELPER.createBlock("dense_spruce_tips", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_ROSELLE_PETAL_MAT = HELPER.createBlock("dense_roselle_petals", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_ROSE_PETAL_MAT = HELPER.createBlock("dense_rose_petals", FoliageMatBlock::new);
        //Traps
    public static final DeferredBlock<Block> SAP_TRAP = HELPER.createBlock("sap_trap", SapTrapBlock::new);
        //Wildflowers + Flora
    public static final DeferredBlock<Block> ROSELLE_BUSH = HELPER.createBlockNoItem("roselle_bush", () ->
          new TallFlowerBlock(ofFullCopy(Blocks.LILAC)));
    public static final DeferredBlock<Block> STOUT_BEACH_ROSE_BUSH = HELPER.createBlockNoItem("stout_beach_rose_bush", () ->
            new SandyFlowerBlock(MobEffects.REGENERATION, 5, ofFullCopy(Blocks.ROSE_BUSH)));
    public static final DeferredBlock<Block> TALL_BEACH_ROSE_BUSH = HELPER.createBlockNoItem("tall_beach_rose_bush", () ->
            new TallSandyFlowerBlock(ofFullCopy(Blocks.ROSE_BUSH)));
    public static final DeferredBlock<Block> WOODLAND_FERN = HELPER.createBlockNoItem("woodland_fern", () ->
            new GrassBlock(ofFullCopy(Blocks.FERN)));
    public static final DeferredBlock<Block> GHOST_PIPE = HELPER.createBlockNoItem("ghost_pipe", () ->
            new GrassBlock(ofFullCopy(Blocks.ALLIUM).lightLevel(state -> 8).noCollission()));
    public static final DeferredBlock<Block> SKUNK_CABBAGE = HELPER.createBlockNoItem("skunk_cabbage", () ->
            new SkunkCabbageBlock(ofFullCopy(Blocks.ALLIUM).noCollission()));
    //Lighting
    public static final DeferredBlock<GhostPipeTorchBlock> GHOST_PIPE_TORCH = HELPER.createBlockNoItem("ghost_pipe_torch", () ->
            new GhostPipeTorchBlock(ofFullCopy(Blocks.TORCH).lightLevel(state -> 11), FIParticleTypes.GHOST_PIPE));
    public static final DeferredBlock<GhostPipeWallTorchBlock> WALL_GHOST_PIPE_TORCH = HELPER.createBlockNoItem("wall_ghost_pipe_torch", () ->
            new GhostPipeWallTorchBlock(ofFullCopy(Blocks.WALL_TORCH).lightLevel(state -> 11), FIParticleTypes.GHOST_PIPE));
        //Other
    public static final DeferredBlock<Block> SUSPICIOUS_LEAF_LITTER = HELPER.createBlock("suspicious_leaf_litter", SuspiciousLitterBlock::new);
    public static final DeferredBlock<Block> HOLLOW_LOG = HELPER.createBlock("hollow_log", () ->
            new HollowLogBlock(ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<Block> CONDENSED_DIRT = HELPER.createBlock("condensed_dirt", () ->
            new ShallowBlock(ofFullCopy(Blocks.DIRT).noOcclusion()));
    public static final DeferredBlock<Block> CONDENSED_SAND = HELPER.createBlock("condensed_sand", () ->
            new ShallowFallingBlock(ofFullCopy(Blocks.SAND).noOcclusion()));
    public static final DeferredBlock<Block> SAP_SPLOTCH = HELPER.createBlock("sap_splotch", SapSplotchBlock::new);

    //STORAGE
        //Crop Storage
            // Crates
    public static final DeferredBlock<Block> APPLE_CRATE = HELPER.createBlock("apple_crate", () ->
            new Block(ofFullCopy(ModBlocks.BEETROOT_CRATE.get())));
    public static final DeferredBlock<Block> BLEWIT_CRATE = HELPER.createBlock("blewit_mushroom_crate", () ->
            new SlabBlock(ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<Block> LILAC_BLOOM_CRATE = HELPER.createBlock("lilac_crate", () ->
            new SlabBlock(ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<Block> TINDER_CONK_CRATE = HELPER.createBlock("tinder_conk_crate", () ->
            new Block(ofFullCopy(ModBlocks.BEETROOT_CRATE.get())));
            // Sacks
    public static final DeferredBlock<Block> BLACK_ACORN_SACK = HELPER.createBlock("black_acorn_sack", () ->
            new Block(ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> DANDELION_ROOT_SACK = HELPER.createBlock("dandelion_root_sack", () ->
            new Block(ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> POPPY_SEEDS_SACK = HELPER.createBlock("poppy_seeds_sack", () ->
            new Block(ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> ROSE_HIP_SACK = HELPER.createBlock("rose_hip_sack", () -> new Block(
            ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> ROSELLE_CALYX_SACK = HELPER.createBlock("roselle_calyx_sack", () -> new Block(
            ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> SPRUCE_TIPS_SACK = HELPER.createBlock("spruce_tips_sack", () -> new Block(
            ofFullCopy(ModBlocks.RICE_BAG.get())));


}