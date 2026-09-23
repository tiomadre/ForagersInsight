package com.tiomadre.foragersinsight.core.compat.jei;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.compat.JEIComp;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DiffusingRecipeCategory implements IRecipeCategory<FIDiffusingRecipes> {
    private static final ResourceLocation TEXTURE = ForagersInsight.rl("textures/gui/jei_diffuser_ui.png");
    private static final int WIDTH = 176;
    private static final int HEIGHT = 66;
    private static final int SLOT_SIZE = 18;
    private static final int SLOT_SPACING = SLOT_SIZE;
    private static final int SLOT_ITEM_OFFSET = 1;
    private static final int INPUT_SLOT_Y = 35;
    private static final int INPUT_SLOT_START_X = 30;
    private static final int ENHANCEMENT_SLOT_X = INPUT_SLOT_START_X + SLOT_SPACING;
    private static final int ENHANCEMENT_SLOT_Y = 14;
    private static final int RESULT_SLOT_X = 121;
    private static final int RESULT_SLOT_Y = 30;
    private static final int SCENT_ICON_SIZE = 16;
    private static final int SCENT_CLOUD_X = RESULT_SLOT_X + SLOT_ITEM_OFFSET;
    private static final int SCENT_CLOUD_Y = 8;
    private static final int SCENT_CLOUD_SIZE = 16;
    private static final int ARROW_U = 177;
    private static final int ARROW_V = 0;
    private static final int ARROW_X = 125;
    private static final int ARROW_Y = 33;
    private static final int ARROW_WIDTH = 22;
    private static final int ARROW_HEIGHT = 16;

    private final IDrawableStatic background;
    private final IDrawableStatic arrow;
    private final IDrawable icon;

    public DiffusingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.arrow = guiHelper.createDrawable(TEXTURE, ARROW_U, ARROW_V, ARROW_WIDTH, ARROW_HEIGHT);
        this.icon = guiHelper.createDrawableItemLike(FIBlocks.DIFFUSER.get());
    }

    @Override
    public @NotNull RecipeType<FIDiffusingRecipes> getRecipeType() {
        return JEIComp.DIFFUSING;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.foragersinsight.diffusing");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull FIDiffusingRecipes recipe, @NotNull IFocusGroup focuses) {
        List<FIDiffusingRecipes.IngredientCount> ingredients = recipe.ingredients();
        for (int index = 0; index < ingredients.size(); index++) {
            FIDiffusingRecipes.IngredientCount ingredient = ingredients.get(index);
            builder.addSlot(RecipeIngredientRole.INPUT, INPUT_SLOT_START_X + index * SLOT_SPACING, INPUT_SLOT_Y)
                    .addItemStacks(stacksFor(ingredient));
        }

        builder.addSlot(RecipeIngredientRole.INPUT, ENHANCEMENT_SLOT_X, ENHANCEMENT_SLOT_Y)
                .addItemStacks(enhancementStacks());
    }

    @Override
    public void draw(@NotNull FIDiffusingRecipes recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.arrow.draw(guiGraphics, ARROW_X, ARROW_Y);
        guiGraphics.blit(recipe.icon(), RESULT_SLOT_X + SLOT_ITEM_OFFSET, RESULT_SLOT_Y + SLOT_ITEM_OFFSET, 0, 0, SCENT_ICON_SIZE, SCENT_ICON_SIZE, SCENT_ICON_SIZE, SCENT_ICON_SIZE);
    }

    @Override
    public @NotNull List<Component> getTooltipStrings(@NotNull FIDiffusingRecipes recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= RESULT_SLOT_X + SLOT_ITEM_OFFSET && mouseX < RESULT_SLOT_X + SLOT_ITEM_OFFSET + SCENT_ICON_SIZE
                && mouseY >= RESULT_SLOT_Y + SLOT_ITEM_OFFSET && mouseY < RESULT_SLOT_Y + SLOT_ITEM_OFFSET + SCENT_ICON_SIZE) {
            return List.of(
                    recipe.recipeName().copy().withStyle(ChatFormatting.GOLD),
                    recipe.description()
            );
        }
        if (mouseX >= SCENT_CLOUD_X && mouseX < SCENT_CLOUD_X + SCENT_CLOUD_SIZE
                && mouseY >= SCENT_CLOUD_Y && mouseY < SCENT_CLOUD_Y + SCENT_CLOUD_SIZE) {
            return scentCloudTooltip(recipe);
        }
        return List.of();
    }

    @Override
    public ResourceLocation getRegistryName(@NotNull FIDiffusingRecipes recipe) {
        return recipe.id();
    }

    private static List<ItemStack> enhancementStacks() {
        return List.of(
                new ItemStack(Items.HONEYCOMB),
                new ItemStack(Items.HONEYCOMB_BLOCK),
                new ItemStack(FIItems.BIRCH_SAP_BOTTLE.get()),
                new ItemStack(FIItems.BIRCH_SAP_BUCKET.get())
        );
    }

    private static List<Component> scentCloudTooltip(FIDiffusingRecipes recipe) {
        List<Component> tooltip = new ArrayList<>(6);
        tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.radius", recipe.radius()).withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.duration", FIDiffusingRecipes.STANDARD_DURATION / 20).withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_radius").withStyle(style -> style.withColor(FIDiffusingRecipes.RADIUS_ACCENT_COLOR)));
        tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_radius_block").withStyle(style -> style.withColor(FIDiffusingRecipes.RADIUS_ACCENT_COLOR)));
        tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_duration").withStyle(style -> style.withColor(FIDiffusingRecipes.DURATION_ACCENT_COLOR)));
        tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_duration_bucket").withStyle(style -> style.withColor(FIDiffusingRecipes.DURATION_ACCENT_COLOR)));
        return tooltip;
    }

    private static List<ItemStack> stacksFor(FIDiffusingRecipes.IngredientCount ingredient) {
        ItemStack[] stacks = ingredient.ingredient().getItems();
        List<ItemStack> sizedStacks = new ArrayList<>(stacks.length);
        for (ItemStack stack : stacks) {
            ItemStack sizedStack = stack.copy();
            sizedStack.setCount(ingredient.count());
            sizedStacks.add(sizedStack);
        }
        return sizedStacks;
    }
}