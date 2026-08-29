package com.tiomadre.foragersinsight.data.server.recipes;

import com.google.common.base.Suppliers;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
public final class FIDiffusingRecipes {
    public static final int STANDARD_DURATION = 12000;
    public static final TextColor RADIUS_ACCENT_COLOR = TextColor.fromRgb(0xfabf29);
    public static final TextColor DURATION_ACCENT_COLOR = TextColor.fromRgb(0xc2daaf);

    private static final List<FIDiffusingRecipes> ALL = new ArrayList<>();
    private static final Map<ResourceLocation, FIDiffusingRecipes> BY_ID = new ConcurrentHashMap<>();
    private static final List<Supplier<FIDiffusingRecipes>> REGISTERED = new ArrayList<>();

    //ROSEY SCENT RECIPES
    public static final Supplier<FIDiffusingRecipes> ROSEY = register(
            "rosey",
            repeated(FIItems.ROSE_PETALS, 1, 3),
            ForagersInsight.rl("textures/scents/rosey.png"),
            "foragersinsight.diffuser.rosey",
            "foragersinsight.diffuser.rosey.description",
            8.0,
            () -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0),
            0);
    public static final Supplier<FIDiffusingRecipes> ROSEY_II = register(
            "rosey",
            repeated(FIBlocks.DENSE_ROSE_PETAL_MAT, 1, 3),
            ForagersInsight.rl("textures/scents/rosey_ii.png"),
            "foragersinsight.diffuser.rosey_ii",
            "foragersinsight.diffuser.rosey_ii.description",
            12.0,
            () -> new MobEffectInstance(MobEffects.REGENERATION, 800, 1),
            0);

    //CONIFEROUS SCENT RECIPES
    public static final Supplier<FIDiffusingRecipes> CONIFEROUS = register(
            "coniferous",
            repeated(FIItems.SPRUCE_TIPS, 1, 3),
            ForagersInsight.rl("textures/scents/coniferous.png"),
            "foragersinsight.diffuser.coniferous",
            "foragersinsight.diffuser.coniferous.description",
            8.0,
            () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 500, 0),
            1);
    public static final Supplier<FIDiffusingRecipes> CONIFEROUS_II = register(
            "coniferous",
            repeated(FIBlocks.DENSE_SPRUCE_TIP_MAT, 1, 3),
            ForagersInsight.rl("textures/scents/coniferous_ii.png"),
            "foragersinsight.diffuser.coniferous_ii",
            "foragersinsight.diffuser.coniferous_ii.description",
            12.0,
            () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1500, 0),
            1);

    //FLORAL SCENT RECIPES
    public static final Supplier<FIDiffusingRecipes> FLORAL = register(
            "floral",
            repeated(FIItems.LILAC_BLOOM, 1, 3),
            ForagersInsight.rl("textures/scents/floral.png"),
            "foragersinsight.diffuser.floral",
            "foragersinsight.diffuser.floral.description",
            8.0,
            () -> new MobEffectInstance(FIMobEffects.BLOOM, 1200, 0),
            0);

    public static final Supplier<FIDiffusingRecipes> FLORAL_II = register(
            "floral_ii",
            List.of(IngredientCount.of(Ingredient.of(FIBlocks.DENSE_ROSE_PETAL_MAT.get()), 1),
                    IngredientCount.of(Ingredient.of(FIBlocks.DENSE_LILAC_BLOOM_MAT.get()), 1),
                    IngredientCount.of(Ingredient.of(FIBlocks.DENSE_ROSELLE_PETAL_MAT.get()), 1)),

            ForagersInsight.rl("textures/scents/floral_ii.png"),
            "foragersinsight.diffuser.floral_ii",
            "foragersinsight.diffuser.floral_ii.description",
            12.0,
            () -> new MobEffectInstance(FIMobEffects.BLOOM, 2000, 1),
            4);

    //FOUL SCENT
    public static final Supplier<FIDiffusingRecipes> FOUL = register(
            "foul",
            repeated(FIBlocks.SKUNK_CABBAGE, 1, 3),
            ForagersInsight.rl("textures/scents/foul.png"),
            "foragersinsight.diffuser.foul",
            "foragersinsight.diffuser.foul.description",
            8.0,
            () -> new MobEffectInstance(FIMobEffects.ODOROUS, 100, 0),
            3);

    public static final Supplier<FIDiffusingRecipes> FOUL_II = register(
            "foul_ii",
            repeated(ModItems.ORGANIC_COMPOST, 1, 3),
            ForagersInsight.rl("textures/scents/foul_ii.png"),
            "foragersinsight.diffuser.foul_ii",
            "foragersinsight.diffuser.foul_ii.description",
            12.0,
            () -> new MobEffectInstance(FIMobEffects.ODOROUS, 1500, 0),
            3);

    public static void bootstrap() {
        REGISTERED.forEach(Supplier::get);
    }

    private final ResourceLocation id;
    private final List<IngredientCount> ingredients;
    private final ResourceLocation icon;
    private final int totalItemCount;
    private final String translationKey;
    private final String descriptionKey;
    private final double radius;
    private final Supplier<MobEffectInstance> effectSupplier;
    private final int networkId;

    private FIDiffusingRecipes(ResourceLocation id, List<IngredientCount> ingredients, ResourceLocation icon,
                               String translationKey, String descriptionKey, double radius, Supplier<MobEffectInstance> effectSupplier, int networkId) {

        this.id = Objects.requireNonNull(id, "id");
        this.ingredients = List.copyOf(ingredients);
        this.icon = Objects.requireNonNull(icon, "icon");
        this.totalItemCount = this.ingredients.stream().mapToInt(IngredientCount::count).sum();
        this.translationKey = Objects.requireNonNull(translationKey, "translationKey");
        this.descriptionKey = Objects.requireNonNull(descriptionKey, "descriptionKey");
        this.radius = radius;
        this.effectSupplier = Objects.requireNonNull(effectSupplier, "effectSupplier");
        this.networkId = networkId;
        ALL.add(this);
        BY_ID.put(this.id, this);
    }
    private static @NotNull Supplier<FIDiffusingRecipes> register(String name, List<IngredientCount> ingredients, ResourceLocation icon,
                                                                  String translationKey, String descriptionKey, double radius, Supplier<MobEffectInstance> effectSupplier, int networkId) {

        Supplier<FIDiffusingRecipes> supplier = Suppliers.memoize(() -> new FIDiffusingRecipes(ForagersInsight.rl(name), ingredients,
                icon, translationKey, descriptionKey, radius, effectSupplier, networkId));REGISTERED.add(supplier);
        return supplier;
    }


    private static List<IngredientCount> repeated(Supplier<? extends ItemLike> item, int count, int times) {
        List<IngredientCount> entries = new ArrayList<>(times);
        for (int i = 0; i < times; i++) {
            entries.add(IngredientCount.of(item, count));
        }
        return List.copyOf(entries);
    }


    public ResourceLocation id() {
        return this.id;
    }

    public List<IngredientCount> ingredients() {
        return this.ingredients;
    }

    public ResourceLocation icon() {
        return this.icon;
    }

    public static Optional<FIDiffusingRecipes> byId(ResourceLocation id) {
        return Optional.ofNullable(BY_ID.get(id));
    }

    public int networkId() {
        return this.networkId;
    }

    public static Optional<FIDiffusingRecipes> byNetworkId(int networkId) {
        if (networkId < 0 || networkId >= ALL.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(ALL.get(networkId));
    }

    public Component displayName() {
        return Component.translatable(this.translationKey);
    }

    public Component recipeName() {
        return Component.translatable(this.translationKey + ".scent");
    }

    public Component description() {
        return Component.translatable(this.descriptionKey).withStyle(ChatFormatting.GRAY);
    }

    public List<Component> tooltip() {
        List<Component> tooltip = new ArrayList<>(2);
        tooltip.add(this.displayName().copy().withStyle(style -> style.withColor(ChatFormatting.WHITE).withUnderlined(true)));

        tooltip.add(this.description());
        return tooltip;
    }

    public Optional<MobEffectInstance> createEffectInstance() {
        MobEffectInstance effect = this.effectSupplier.get();
        return Optional.ofNullable(effect);
    }
    public boolean usesEffect(MobEffect effect) {
        return createEffectInstance()
                .map(instance -> instance.getEffect() == effect)
                .orElse(false);
    }

    public double radius() {
        return this.radius;
    }

    public boolean matches(List<? extends ItemStack> stacks) {
        if (this.ingredients.isEmpty()) {
            return false;
        }
        int[] remaining = new int[this.ingredients.size()];
        for (int i = 0; i < this.ingredients.size(); i++) {
            remaining[i] = this.ingredients.get(i).count();
        }

        int totalItems = 0;
        for (ItemStack stack : stacks) {
            if (stack.isEmpty()) continue;

            totalItems += stack.getCount();
            boolean matched = false;
            for (int i = 0; i < this.ingredients.size(); i++) {
                IngredientCount ingredient = this.ingredients.get(i);
                if (remaining[i] <= 0) {
                    continue;
                }
                if (ingredient.ingredient().test(stack)) {
                    if (stack.getCount() > remaining[i]) {
                        continue;
                    }
                    remaining[i] -= stack.getCount();
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }
        if (totalItems != this.totalItemCount) {
            return false;
        }
        for (int r : remaining) {
            if (r != 0) {
                return false;
            }
        }
        return true;
    }

    public static final class IngredientCount {
        private final Supplier<Ingredient> ingredientSupplier;
        private Ingredient ingredient;
        private final int count;

        private IngredientCount(Supplier<Ingredient> ingredientSupplier, int count) {
            this.ingredientSupplier = ingredientSupplier;
            this.count = count;
        }

        public static IngredientCount of(Ingredient ingredient, int count) {
            return new IngredientCount(() -> ingredient, count);
        }

        public static IngredientCount of(ItemLike item, int count) {
            return new IngredientCount(() -> Ingredient.of(item), count);
        }

        public static IngredientCount of(Supplier<? extends ItemLike> item, int count) {
            return new IngredientCount(() -> Ingredient.of(item.get()), count);
        }

        public Ingredient ingredient() {
            if (this.ingredient == null) {
                this.ingredient = Objects.requireNonNull(this.ingredientSupplier.get(), "ingredient");
            }
            return this.ingredient;
        }

        public int count() {
            return this.count;
        }

        public boolean matches(ItemStack stack) {
            return this.ingredient().test(stack) && stack.getCount() >= this.count;
        }

        @Override
        public String toString() {
            return "IngredientCount[" + ingredient() + " x" + count + "]";
        }
    }

    public static Optional<FIDiffusingRecipes> findMatch(List<? extends ItemStack> stacks) {
        if (stacks == null || stacks.isEmpty()) {
            return Optional.empty();
        }

        for (FIDiffusingRecipes scent : ALL) {
            if (scent.matches(stacks)) {
                return Optional.of(scent);
            }
        }

        return Optional.empty();
    }
    public static List<FIDiffusingRecipes> all() {
        bootstrap();
        return List.copyOf(ALL);
    }
}