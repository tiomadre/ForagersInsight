package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class FIAdvancements {

    public enum FIFrameType {
        TASK("task"),
        GOAL("goal"),
        CHALLENGE("challenge");

        public final String id;

        FIFrameType(String id) {
            this.id = id;
        }
    }

    public static final ResourceLocation ROOT = ForagersInsight.rl("adventure/foragers_insight");

    public static final ResourceLocation SPRING_CLEANING = ForagersInsight.rl("adventure/spring_cleaning");
    public static final ResourceLocation BRUSH_IT_OFF = ForagersInsight.rl("adventure/brush_it_off");
    public static final ResourceLocation RARE_FIND = ForagersInsight.rl("adventure/rare_find");
    public static final ResourceLocation WILD_FLOWERS = ForagersInsight.rl("adventure/wild_flowers");
    public static final ResourceLocation SHEARING_IS_CARING = ForagersInsight.rl("adventure/shearing_is_caring");
    public static final ResourceLocation GIVING_TREES = ForagersInsight.rl("adventure/giving_trees");
    public static final ResourceLocation STOP_HAMMER_TIME = ForagersInsight.rl("adventure/stop_hammer_time");
    public static final ResourceLocation WILL_IT_CRUSH = ForagersInsight.rl("adventure/will_it_crush");
    public static final ResourceLocation CRACK_IT = ForagersInsight.rl("adventure/crack_it");
    public static final ResourceLocation PETAL_TO_THE_METAL = ForagersInsight.rl("adventure/petal_to_the_metal");
    public static final ResourceLocation UH_FIX_IT = ForagersInsight.rl("adventure/uh_fix_it");
    public static final ResourceLocation TASTE_THE_RAINBOW_MOTHA = ForagersInsight.rl("adventure/taste_the_rainbow_motha");
    public static final ResourceLocation TASTE_THE_RAINBOW_MOTHA_ICON = ForagersInsight.rl("slice_of_rainbow_sandwich");

    public static final ResourceLocation ROOT_ICON = ForagersInsight.rl("handbasket");
    public static final ResourceLocation SPRING_CLEANING_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "brush");
    public static final ResourceLocation BRUSH_IT_OFF_ICON = ForagersInsight.rl("suspicious_leaf_litter");
    public static final ResourceLocation RARE_FIND_ICON = ForagersInsight.rl("blewit_mushroom");
    public static final ResourceLocation GIVING_TREES_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "apple");
    public static final ResourceLocation STINKY_SITUATION_ICON = ResourceLocation.fromNamespaceAndPath("farmersdelight", "organic_compost");
    public static final ResourceLocation TAP_THAT_ICON = ForagersInsight.rl("tapper");
    public static final ResourceLocation BIRCH_PLEASE_ICON = ForagersInsight.rl("birch_sap_bucket");
    public static final ResourceLocation SCENTSATIONAL_ICON = ForagersInsight.rl("diffuser");
    public static final ResourceLocation STOP_HAMMER_TIME_ICON = ForagersInsight.rl("flint_mallet");
    public static final ResourceLocation WILL_IT_CRUSH_ICON = ForagersInsight.rl("wheat_flour");
    public static final ResourceLocation CRACK_IT_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "cracked_stone_bricks");
    public static final ResourceLocation PETAL_TO_THE_METAL_ICON = ForagersInsight.rl("rose_petals");
    public static final ResourceLocation UH_FIX_IT_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "anvil");

    public static final ResourceLocation TAP_THAT = ForagersInsight.rl("adventure/tap_that");
    public static final ResourceLocation BIRCH_PLEASE = ForagersInsight.rl("adventure/birch_please");

    public static final ResourceLocation WILD_FLOWERS_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "rose_bush");
    public static final ResourceLocation SHEARING_IS_CARING_ICON = ForagersInsight.rl("flint_shears");

    public static final ResourceLocation SCENTSATIONAL = ForagersInsight.rl("adventure/scentsational");
    public static final ResourceLocation STINKY_SITUATION = ForagersInsight.rl("adventure/stinky_situation");

    public static final Node ROOT_NODE = node(ROOT, ROOT_ICON, 0, 0, FIFrameType.TASK);

    public static final List<Node> FIRST_ROW = List.of(
            node(WILD_FLOWERS, WILD_FLOWERS_ICON, -3, 0, FIFrameType.TASK),
            node(SPRING_CLEANING, SPRING_CLEANING_ICON, -1, 0, FIFrameType.TASK),
            node(GIVING_TREES, GIVING_TREES_ICON, 1, 0, FIFrameType.TASK),
            node(STOP_HAMMER_TIME, STOP_HAMMER_TIME_ICON, 3, 0, FIFrameType.TASK)
    );
    public static final List<Node> SECOND_ROW = List.of(
            node(SCENTSATIONAL, SCENTSATIONAL_ICON, -4, 1, FIFrameType.TASK),
            node(PETAL_TO_THE_METAL, PETAL_TO_THE_METAL_ICON, -3, 1, FIFrameType.GOAL),
            node(BRUSH_IT_OFF, BRUSH_IT_OFF_ICON, -1, 1, FIFrameType.TASK),
            node(RARE_FIND, RARE_FIND_ICON, 0, 1, FIFrameType.GOAL),
            node(TAP_THAT, TAP_THAT_ICON, 1, 1, FIFrameType.TASK),
            node(SHEARING_IS_CARING, SHEARING_IS_CARING_ICON, 2, 1, FIFrameType.TASK),
            node(WILL_IT_CRUSH, WILL_IT_CRUSH_ICON, 3, 1, FIFrameType.TASK),
            node(CRACK_IT, CRACK_IT_ICON, 4, 1, FIFrameType.TASK)
    );

    public static final List<Node> THIRD_ROW = List.of(
            node(STINKY_SITUATION, STINKY_SITUATION_ICON, -4, 2, FIFrameType.CHALLENGE),
            node(BIRCH_PLEASE, BIRCH_PLEASE_ICON, 1, 2, FIFrameType.TASK),
            node(UH_FIX_IT, UH_FIX_IT_ICON, 4, 2, FIFrameType.CHALLENGE)
    );

    public static final List<Node> STANDALONE = List.of(
            node(TASTE_THE_RAINBOW_MOTHA, TASTE_THE_RAINBOW_MOTHA_ICON, 0, 0, FIFrameType.GOAL)
    );

    private static final Map<ResourceLocation, Node> NODE_LOOKUP = createNodeLookup();

    private FIAdvancements() {
    }

    public static Node node(ResourceLocation id) {
        Node node = NODE_LOOKUP.get(id);
        if (node == null) {
            throw new IllegalArgumentException("Missing advancement node for id: " + id);
        }
        return node;
    }

    public record Node(
            ResourceLocation id,
            ResourceLocation icon,
            int x,
            int y,
            FIFrameType frame
    ) {
    }

    private static Node node(ResourceLocation id, ResourceLocation icon, int x, int y, FIFrameType frame) {
        return new Node(id, icon, x, y, frame);
    }

    private static Map<ResourceLocation, Node> createNodeLookup() {
        Map<ResourceLocation, Node> nodes = new LinkedHashMap<>();
        nodes.put(ROOT_NODE.id(), ROOT_NODE);
        for (Node node : FIRST_ROW) {
            nodes.put(node.id(), node);
        }
        for (Node node : SECOND_ROW) {
            nodes.put(node.id(), node);
        }
        for (Node node : THIRD_ROW) {
            nodes.put(node.id(), node);
        }
        for (Node node : STANDALONE) {
            nodes.put(node.id(), node);
        }
        return Map.copyOf(nodes);
    }
}