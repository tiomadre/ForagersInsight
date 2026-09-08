package com.tiomadre.foragersinsight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tiomadre.foragersinsight.common.block.entity.DiffuserBlockEntity;
import com.tiomadre.foragersinsight.data.server.recipes.FIDiffusingRecipes;
import com.tiomadre.foragersinsight.common.gui.DiffuserMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;


import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiffuserScreen extends AbstractContainerScreen<DiffuserMenu> {
    private static final ResourceLocation UNLIT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("foragersinsight", "textures/gui/diffuser.png");
    private static final ResourceLocation LIT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("foragersinsight", "textures/gui/diffuser_lit.png");

    private static final int ARROW_U = 177;
    private static final int ARROW_V = 0;
    private static final int ARROW_HEIGHT = 16;
    private static final int ARROW_WIDTH = 22;
    private static final int ARROW_X = 125;
    private static final int ARROW_Y = 33;

    private static final int POWER_BUTTON_SIZE = 6;
    private static final int POWER_BUTTON_X = 131;
    private static final int POWER_BUTTON_Y = 61;

    private static final int SCENT_CLOUD_Y = 24;
    private static final int SCENT_CLOUD_SIZE = 16;

    private static final int ICON_SIZE = 16;
    private Button powerButton;

    public DiffuserScreen(DiffuserMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;

        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 96 + 2;
    }
    @Override
    protected void init() {
        super.init();
        int buttonX = this.leftPos + POWER_BUTTON_X;
        int buttonY = this.topPos + POWER_BUTTON_Y;

        this.powerButton = Button.builder(Component.empty(), this::onPowerPressed)
                .bounds(buttonX, buttonY, POWER_BUTTON_SIZE, POWER_BUTTON_SIZE)
                .build();
        this.powerButton.setAlpha(0.0F);
        this.powerButton.setTooltip(Tooltip.create(
                Component.translatable("gui.foragersinsight.diffuser.power")));
        this.addRenderableWidget(this.powerButton);
        updateButtonState();
    }
    private void updateButtonState() {
        if (this.powerButton != null) {
            this.powerButton.active = this.menu.getActiveScent().isPresent();
        }
    }

    private void onPowerPressed(Button button) {
        if (this.minecraft == null || this.minecraft.gameMode == null) {
            return;
        }
        if (this.menu.getActiveScent().isEmpty()) {
            return;
        }
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, DiffuserMenu.BUTTON_POWER);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        updateButtonState();
    }


    @Override
    protected void renderBg(@NotNull GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
        ResourceLocation background = this.menu.isLit() ? LIT_TEXTURE : UNLIT_TEXTURE;
        RenderSystem.setShaderTexture(0, background);
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;

        gui.blit(background, left, top, 0, 0, this.imageWidth, this.imageHeight);
        int progress = Math.min(this.menu.getCraftProgress(), ARROW_WIDTH);
        if (progress > 0) {
            gui.blit(background,
                    left + ARROW_X, top + ARROW_Y,
                    ARROW_U, ARROW_V,
                    progress, ARROW_HEIGHT);
        }

        renderScentIcon(gui, left, top);
    }

    @Override
    public void render(@NotNull GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui, mouseX, mouseY, partialTicks);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
        this.renderScentTooltip(gui, mouseX, mouseY);
        this.renderScentCloudTooltip(gui, mouseX, mouseY);
    }

    private void renderScentIcon(GuiGraphics gui, int left, int top) {
        Optional<FIDiffusingRecipes> scent = this.menu.getActiveScent();
        if (scent.isEmpty()) {
            return;
        }
        Slot slot = this.menu.getSlot(DiffuserBlockEntity.RESULT_SLOT_INDEX);
        int iconX = left + slot.x;
        int iconY = top + slot.y;
        gui.blit(scent.get().icon(), iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }

    private void renderScentTooltip(GuiGraphics gui, int mouseX, int mouseY) {
        Optional<FIDiffusingRecipes> scent = this.menu.getActiveScent();
        if (scent.isEmpty()) {
            return;
        }
        Slot slot = this.menu.getSlot(DiffuserBlockEntity.RESULT_SLOT_INDEX);
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        int iconX = left + slot.x;
        int iconY = top + slot.y;
        if (mouseX >= iconX && mouseX < iconX + ICON_SIZE && mouseY >= iconY && mouseY < iconY + ICON_SIZE) {
            FIDiffusingRecipes recipe = scent.get();
            List<Component> tooltip = new ArrayList<>(2);
            tooltip.add(recipe.recipeName().copy().withStyle(ChatFormatting.GOLD));
            tooltip.add(recipe.description());
            gui.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
        }
    }

    private void renderScentCloudTooltip(GuiGraphics gui, int mouseX, int mouseY) {
        if (!this.menu.isLit()) {
            return;
        }

        Optional<FIDiffusingRecipes> scent = this.menu.getActiveScent();
        if (scent.isEmpty()) {
            return;
        }

        Slot slot = this.menu.getSlot(DiffuserBlockEntity.RESULT_SLOT_INDEX);
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        int infoX = left + slot.x;
        int infoY = top + slot.y - SCENT_CLOUD_Y;
        if (mouseX < infoX || mouseX >= infoX + SCENT_CLOUD_SIZE || mouseY < infoY || mouseY >= infoY + SCENT_CLOUD_SIZE) {
            return;
        }

        double radius = this.menu.getEffectiveRadius();
        int durationSeconds = (int) Math.round(Math.max(this.menu.getRemainingDuration(), 0) / 20.0D);
        List<Component> tooltip = new ArrayList<>(3);
        tooltip.add(Component.translatable(
                "gui.foragersinsight.diffuser.tooltip.radius",
                String.format(Locale.ROOT, "%.1f", radius)
        ).withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.translatable(
                "gui.foragersinsight.diffuser.tooltip.duration",
                durationSeconds
        ).withStyle(ChatFormatting.WHITE));

        DiffuserBlockEntity.Enhancement enhancement = this.menu.getActiveEnhancement();
        if (enhancement == DiffuserBlockEntity.Enhancement.RADIUS) {
            tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_radius").withStyle(style -> style.withColor(FIDiffusingRecipes.RADIUS_ACCENT_COLOR)));
        } else if (enhancement == DiffuserBlockEntity.Enhancement.RADIUS_BLOCK) {
            tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_radius_block").withStyle(style -> style.withColor(FIDiffusingRecipes.RADIUS_ACCENT_COLOR)));
        } else if (enhancement == DiffuserBlockEntity.Enhancement.DURATION) {
            tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_duration").withStyle(style -> style.withColor(FIDiffusingRecipes.DURATION_ACCENT_COLOR)));
        } else if (enhancement == DiffuserBlockEntity.Enhancement.DURATION_BUCKET) {
            tooltip.add(Component.translatable("gui.foragersinsight.diffuser.tooltip.enhanced_duration_bucket").withStyle(style -> style.withColor(FIDiffusingRecipes.DURATION_ACCENT_COLOR)));
        }

        gui.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
    }
}