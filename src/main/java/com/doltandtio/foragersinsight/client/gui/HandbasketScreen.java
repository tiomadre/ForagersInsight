package com.doltandtio.foragersinsight.client.gui;

import com.doltandtio.foragersinsight.common.gui.HandbasketMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class HandbasketScreen extends AbstractContainerScreen<HandbasketMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("foragersinsight", "textures/gui/handbasket.png");

    public HandbasketScreen(HandbasketMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth  = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics gui, float partial, int x, int y) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int left = (width - imageWidth) / 2;
        int top  = (height - imageHeight) / 2;
        gui.blit(TEXTURE, left, top, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }
}
