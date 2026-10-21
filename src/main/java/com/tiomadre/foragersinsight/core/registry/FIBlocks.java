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
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import static com.tiomadre.foragersinsight.core.registry.FIItems.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FIBlocks {
    public static final BlockSubRegistryHelper BLOCKS = ForagersInsight.REGISTRY_HELPER.getBlockSubHelper();
    private static BlockBehaviour.Properties vanillaLeafProperties(Block vanillaLeaf) {
        return ofFullCopy(vanillaLeaf)
                .mapColor(MapColor.PLANT)
                .noOcclusion()
                .isValidSpawn((state, level, pos, type) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false);
    }


    //Cakes and Feasts
    public static final DeferredBlock<Block> ACORN_CARROT_CAKE = BLOCKS.createBlockNoItem("acorn_carrot_cake",
            () -> new SliceableCakeBlock(ofFullCopy(Blocks.CAKE), FIItems.SLICE_OF_ACORN_CARROT_CAKE));
    public static final DeferredBlock<Block> RAINBOW_SANDWICH = BLOCKS.createBlockNoItem("rainbow_sandwich",
            () -> new RainbowSandwichFeastBlock(ofFullCopy(Blocks.CAKE).noOcclusion(), FIItems.SLICE_OF_RAINBOW_SANDWICH));
    //Crops
        //Flower
    public static final DeferredBlock<Block> POPPY_BUSH = BLOCKS.createBlockNoItem("poppy_bush", () ->
            new PoppyBushBlock(ofFullCopy(Blocks.BEETROOTS)));
    public static final DeferredBlock<Block> DANDELION_BUSH = BLOCKS.createBlockNoItem("dandelion_bush", () ->
            new DandelionBushBlock(ofFullCopy(Blocks.POTATOES)));
    public static final DeferredBlock<Block> ROSE_CROP = BLOCKS.createBlockNoItem("rose_crop", () -> new RoseCropBlock(
            ofFullCopy(Blocks.WHEAT), 3));
    public static final DeferredBlock<Block> ROSELLE_CROP = BLOCKS.createBlockNoItem("roselle_crop", () -> new RoselleCropBlock(
            ofFullCopy(Blocks.WHEAT), 3));
    public static final DeferredBlock<Block> PHLOX = BLOCKS.createBlock("phlox", () ->
            new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS)));

    //Mushrooms
    public static final DeferredBlock<Block> BLEWIT_MUSHROOM_COLONY = BLOCKS.createBlockNoItem("blewit_mushroom_colony", () ->
            new MushroomColonyBlock(FIItems.BLEWIT_MUSHROOM,Block.Properties.ofFullCopy(ModBlocks.RED_MUSHROOM_COLONY.get())));
    public static final DeferredBlock<Block> BLEWIT_MUSHROOM = BLOCKS.createBlockNoItem("blewit_mushroom", () ->
            new WildMushroomBlock(ofFullCopy(Blocks.RED_MUSHROOM), BLEWIT_MUSHROOM_COLONY));
    public static final DeferredBlock<Block> WALL_RED_MUSHROOM_COLONY = BLOCKS.createBlockNoItem("wall_red_mushroom_colony", () ->
            new WallMushroomColonyBlock(ofFullCopy(ModBlocks.RED_MUSHROOM_COLONY.get()), Items.RED_MUSHROOM.builtInRegistryHolder()));
    public static final DeferredBlock<Block> WALL_BROWN_MUSHROOM_COLONY = BLOCKS.createBlockNoItem("wall_brown_mushroom_colony", () ->
            new WallMushroomColonyBlock(ofFullCopy(ModBlocks.BROWN_MUSHROOM_COLONY.get()), Items.BROWN_MUSHROOM.builtInRegistryHolder()));
    public static final DeferredBlock<Block> WALL_BLEWIT_MUSHROOM_COLONY = BLOCKS.createBlockNoItem("wall_blewit_mushroom_colony", () ->
            new WallMushroomColonyBlock(ofFullCopy(FIBlocks.BLEWIT_MUSHROOM_COLONY.get()), FIItems.BLEWIT_MUSHROOM));
    public static final DeferredBlock<Block> WALL_RED_MUSHROOM = BLOCKS.createBlockNoItem("wall_red_mushroom", () ->
            new WallMushroomBlock(ofFullCopy(Blocks.RED_MUSHROOM), WALL_RED_MUSHROOM_COLONY));
    public static final DeferredBlock<Block> WALL_BROWN_MUSHROOM = BLOCKS.createBlockNoItem("wall_brown_mushroom", () ->
            new WallMushroomBlock(ofFullCopy(Blocks.BROWN_MUSHROOM), WALL_BROWN_MUSHROOM_COLONY));
    public static final DeferredBlock<Block> WALL_BLEWIT_MUSHROOM = BLOCKS.createBlockNoItem("wall_blewit_mushroom", () ->
            new WallMushroomBlock(ofFullCopy(FIBlocks.BLEWIT_MUSHROOM.get()), WALL_BLEWIT_MUSHROOM_COLONY));
        //Unique Mushrooms
        public static final DeferredBlock<Block> TINDER_CONK = BLOCKS.createBlockNoItem("tinder_conk", () ->
            new TinderConkBlock(ofFullCopy(Blocks.BROWN_MUSHROOM).randomTicks().noOcclusion()));

    //Trees
    //Oak
    public static final DeferredBlock<Block> BOUNTIFUL_OAK_LEAVES = BLOCKS.createBlock("bountiful_oak_leaves", () ->
            new BountifulLeavesBlock(vanillaLeafProperties(Blocks.OAK_LEAVES), () -> Items.APPLE));
    //Dark Oak
    public static final DeferredBlock<Block> BOUNTIFUL_DARK_OAK_LEAVES = BLOCKS.createBlock("bountiful_dark_oak_leaves", () ->
            new BountifulLeavesBlock(vanillaLeafProperties(Blocks.DARK_OAK_LEAVES), BLACK_ACORN));
    //Spruce
    public static final DeferredBlock<Block> BOUNTIFUL_SPRUCE_LEAVES = BLOCKS.createBlock("bountiful_spruce_leaves", () ->
            new BountifulSpruceLeavesBlock(vanillaLeafProperties(Blocks.SPRUCE_LEAVES)));
    public static final DeferredBlock<Block> BOUNTIFUL_SPRUCE_TIPS = BLOCKS.createBlockNoItem("bountiful_spruce_tips", () ->
            new SpruceTipBlock(ofFullCopy(Blocks.SWEET_BERRY_BUSH).noCollission()));
    //Birch
    public static final DeferredBlock<Block> SAPPY_BIRCH_LOG = BLOCKS.createBlock("sappy_birch_log", () ->
            new LogBlock(FIBlocks.STRIPPED_SAPPY_BIRCH_LOG, ofFullCopy(Blocks.BIRCH_LOG)));
    public static final DeferredBlock<Block> STRIPPED_SAPPY_BIRCH_LOG = BLOCKS.createBlock("stripped_sappy_birch_log", () ->
            new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_BIRCH_LOG)));
    //Lilac
    public static final DeferredBlock<Block> LILAC_LEAVES = BLOCKS.createBlock("lilac_leaves", () ->
            new LeavesBlock(vanillaLeafProperties(Blocks.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> BLOSSOMING_LILAC_LEAVES = BLOCKS.createBlock("blossoming_lilac_leaves", () ->
            new BlossomingLilacLeavesBlock(vanillaLeafProperties(Blocks.FLOWERING_AZALEA_LEAVES)));
    public static final DeferredBlock<Block> HANGING_LILAC_LEAVES = BLOCKS.createBlockNoItem("hanging_lilac_leaves", () ->
            new HangingLilacLeavesBlock(ofFullCopy(Blocks.AZALEA_LEAVES).noCollission()));
    public static final DeferredBlock<Block> LILAC_LOG = BLOCKS.createBlock("lilac_log", () ->
            new ThinLogBlock(FIBlocks.STRIPPED_LILAC_LOG, ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<Block> STRIPPED_LILAC_LOG = BLOCKS.createBlock("stripped_lilac_log", () ->
            new ThinLogBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).noOcclusion()));
    //Wood Stuff
        //Lilac
    public static final DeferredBlock<Block> LILAC_PLANKS = BLOCKS.createBlock("lilac_planks", () ->
            new Block(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> LILAC_STAIRS = BLOCKS.createBlock("lilac_stairs", () ->
            new StairBlock(LILAC_PLANKS.get().defaultBlockState(), ofFullCopy(Blocks.OAK_STAIRS)));
    public static final DeferredBlock<Block> LILAC_SLAB = BLOCKS.createBlock("lilac_slab", () ->
            new SlabBlock(ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<Block> LILAC_FENCE = BLOCKS.createBlock("lilac_fence", () ->
            new FenceBlock(ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<Block> LILAC_FENCE_GATE = BLOCKS.createBlock("lilac_fence_gate", () ->
            new FenceGateBlock(FIWoodTypes.LILAC, ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<Block> LILAC_DOOR = BLOCKS.createBlock("lilac_door", () ->
            new DoorBlock(FIWoodTypes.LILAC.setType(), ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<Block> LILAC_TRAPDOOR = BLOCKS.createBlock("lilac_trapdoor", () ->
            new TrapDoorBlock( FIWoodTypes.LILAC.setType(),ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<Block> LILAC_PRESSURE_PLATE = BLOCKS.createBlock("lilac_pressure_plate", () ->
            new PressurePlateBlock( FIWoodTypes.LILAC.setType(), ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final DeferredBlock<Block> LILAC_BUTTON = BLOCKS.createBlock("lilac_button", () ->
            new ButtonBlock(FIWoodTypes.LILAC.setType(), 30,ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<BlueprintStandingSignBlock> LILAC_SIGN = BLOCKS.createBlockNoItem("lilac_sign", () ->
            new BlueprintStandingSignBlock(ofFullCopy(Blocks.OAK_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<BlueprintWallSignBlock> LILAC_WALL_SIGN = BLOCKS.createBlockNoItem("lilac_wall_sign", () ->
            new BlueprintWallSignBlock(ofFullCopy(Blocks.OAK_WALL_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<BlueprintCeilingHangingSignBlock> LILAC_HANGING_SIGN = BLOCKS.createBlockNoItem("lilac_hanging_sign", () ->
            new BlueprintCeilingHangingSignBlock(ofFullCopy(Blocks.OAK_HANGING_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<BlueprintWallHangingSignBlock> LILAC_WALL_HANGING_SIGN = BLOCKS.createBlockNoItem("lilac_wall_hanging_sign", () ->
            new BlueprintWallHangingSignBlock(ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), FIWoodTypes.LILAC));
    public static final DeferredBlock<CabinetBlock> LILAC_CABINET = BLOCKS.createBlock("lilac_cabinet", () ->
            new CabinetBlock(ofFullCopy(ModBlocks.OAK_CABINET.get())));

        //Syrup Tap
    public static final DeferredBlock<Block> TAPPER = BLOCKS.createBlockNoItem("tapper", () ->
            new TapperBlock(ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    //Diffuser
    public static final DeferredBlock<Block> DIFFUSER = BLOCKS.createBlockNoItem("diffuser",
            () -> new DiffuserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).noOcclusion()));
    //DECORATIVE
        //Foliage Mats
    public static final DeferredBlock<Block> SCATTERED_LILAC_BLOOM_MAT = BLOCKS.createBlock("scattered_lilac_blooms", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_ROSE_PETAL_MAT = BLOCKS.createBlock("scattered_rose_petals", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_ROSELLE_PETAL_MAT = BLOCKS.createBlock("scattered_roselle_petals", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_SPRUCE_TIP_MAT = BLOCKS.createBlock("scattered_spruce_tips", FoliageMatBlock::new);
    public static final DeferredBlock<Block> SCATTERED_STRAW_MAT = BLOCKS.createBlock("scattered_straw", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_LILAC_BLOOM_MAT = BLOCKS.createBlock("dense_lilac_blooms", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_STRAW_MAT = BLOCKS.createBlock("dense_straw", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_SPRUCE_TIP_MAT = BLOCKS.createBlock("dense_spruce_tips", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_ROSELLE_PETAL_MAT = BLOCKS.createBlock("dense_roselle_petals", FoliageMatBlock::new);
    public static final DeferredBlock<Block> DENSE_ROSE_PETAL_MAT = BLOCKS.createBlock("dense_rose_petals", FoliageMatBlock::new);
        //Traps
    public static final DeferredBlock<Block> SAP_TRAP = BLOCKS.createBlock("sap_trap", SapTrapBlock::new);
        //Wildflowers + Flora
    public static final DeferredBlock<Block> ROSELLE_BUSH = BLOCKS.createBlockNoItem("roselle_bush", () ->
          new TallFlowerBlock(ofFullCopy(Blocks.LILAC)));
    public static final DeferredBlock<Block> STOUT_BEACH_ROSE_BUSH = BLOCKS.createBlockNoItem("stout_beach_rose_bush", () ->
            new SandyFlowerBlock(MobEffects.REGENERATION, 5, ofFullCopy(Blocks.ROSE_BUSH)));
    public static final DeferredBlock<Block> TALL_BEACH_ROSE_BUSH = BLOCKS.createBlockNoItem("tall_beach_rose_bush", () ->
            new TallSandyFlowerBlock(ofFullCopy(Blocks.ROSE_BUSH)));
    public static final DeferredBlock<Block> WOODLAND_FERN = BLOCKS.createBlockNoItem("woodland_fern", () ->
            new GrassBlock(ofFullCopy(Blocks.FERN)));
    public static final DeferredBlock<Block> GHOST_PIPE = BLOCKS.createBlockNoItem("ghost_pipe", () ->
            new GrassBlock(ofFullCopy(Blocks.ALLIUM).lightLevel(state -> 8).noCollission()));
    public static final DeferredBlock<Block> SKUNK_CABBAGE = BLOCKS.createBlockNoItem("skunk_cabbage", () ->
            new SkunkCabbageBlock(ofFullCopy(Blocks.ALLIUM).noCollission()));
    //Lighting
    public static final DeferredBlock<GhostPipeTorchBlock> GHOST_PIPE_TORCH = BLOCKS.createBlockNoItem("ghost_pipe_torch", () ->
            new GhostPipeTorchBlock(ofFullCopy(Blocks.TORCH).lightLevel(state -> 11), FIParticleTypes.GHOST_PIPE));
    public static final DeferredBlock<GhostPipeWallTorchBlock> WALL_GHOST_PIPE_TORCH = BLOCKS.createBlockNoItem("wall_ghost_pipe_torch", () ->
            new GhostPipeWallTorchBlock(ofFullCopy(Blocks.WALL_TORCH).lightLevel(state -> 11), FIParticleTypes.GHOST_PIPE));
        //Other
    public static final DeferredBlock<Block> SUSPICIOUS_LEAF_LITTER = BLOCKS.createBlock("suspicious_leaf_litter", SuspiciousLitterBlock::new);
    public static final DeferredBlock<Block> HOLLOW_LOG = BLOCKS.createBlock("hollow_log", () ->
            new HollowLogBlock(ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<Block> CONDENSED_DIRT = BLOCKS.createBlock("condensed_dirt", () ->
            new ShallowBlock(ofFullCopy(Blocks.DIRT).noOcclusion()));
    public static final DeferredBlock<Block> CONDENSED_SAND = BLOCKS.createBlock("condensed_sand", () ->
            new ShallowFallingBlock(ofFullCopy(Blocks.SAND).noOcclusion()));
    public static final DeferredBlock<Block> SAP_SPLOTCH = BLOCKS.createBlock("sap_splotch", SapSplotchBlock::new);

    //STORAGE
        //Crop Storage
            // Crates
    public static final DeferredBlock<Block> APPLE_CRATE = BLOCKS.createBlock("apple_crate", () ->
            new Block(ofFullCopy(ModBlocks.BEETROOT_CRATE.get())));
    public static final DeferredBlock<Block> BLEWIT_CRATE = BLOCKS.createBlock("blewit_mushroom_crate", () ->
            new SlabBlock(ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<Block> LILAC_BLOOM_CRATE = BLOCKS.createBlock("lilac_crate", () ->
            new SlabBlock(ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<Block> TINDER_CONK_CRATE = BLOCKS.createBlock("tinder_conk_crate", () ->
            new Block(ofFullCopy(ModBlocks.BEETROOT_CRATE.get())));
            // Sacks
    public static final DeferredBlock<Block> BLACK_ACORN_SACK = BLOCKS.createBlock("black_acorn_sack", () ->
            new Block(ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> DANDELION_ROOT_SACK = BLOCKS.createBlock("dandelion_root_sack", () ->
            new Block(ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> POPPY_SEEDS_SACK = BLOCKS.createBlock("poppy_seeds_sack", () ->
            new Block(ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> ROSE_HIP_SACK = BLOCKS.createBlock("rose_hip_sack", () -> new Block(
            ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> ROSELLE_CALYX_SACK = BLOCKS.createBlock("roselle_calyx_sack", () -> new Block(
            ofFullCopy(ModBlocks.RICE_BAG.get())));
    public static final DeferredBlock<Block> SPRUCE_TIPS_SACK = BLOCKS.createBlock("spruce_tips_sack", () -> new Block(
            ofFullCopy(ModBlocks.RICE_BAG.get())));


}