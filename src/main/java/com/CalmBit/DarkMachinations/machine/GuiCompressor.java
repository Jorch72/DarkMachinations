package com.CalmBit.DarkMachinations.machine;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCompressor extends GuiContainer{
    private final ContainerCompressor compressorContainer;
    public static final ResourceLocation guiLocation = new ResourceLocation("divitae:textures/gui/machine/compressor.png");
    public GuiCompressor(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.compressorContainer = (ContainerCompressor) inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if(mouseX >= i+19 && mouseX < i+19+12 && mouseY >= j+9 && mouseY < j+9+70)
        {
            List list = new ArrayList();
            list.add("Energy: " + compressorContainer.tileEntity.getField(TileEntityCompressor.FIELD_ENERGY_COUNT) + "/" + compressorContainer.tileEntity.getField(TileEntityCompressor.FIELD_ENERGY_CAPACITY));
            this.drawHoveringText(list, mouseX-i, mouseY-j, this.fontRendererObj);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiLocation);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        int energyScale = getEnergyScale(72);
        this.drawTexturedModalRect(i+19, j+8+energyScale, this.xSize, 17, 12, 70-energyScale);
        if(TileEntityCompressor.isActive(this.compressorContainer.tileEntity)) {
            int progressScale = getProgressScale(24);
            this.drawTexturedModalRect(i + 100, j + 33, this.xSize, 0, progressScale, 17);
            progressScale = getProgressScale(22);
            this.drawTexturedModalRect(i + 72, j + 5, this.xSize, 87, 32, progressScale);
            this.drawTexturedModalRect(i + 72, j + 75 - progressScale, this.xSize, (109+22)-progressScale, 32, progressScale);
        }
    }

    private int getProgressScale(int size)
    {
        int processingTime = this.compressorContainer.tileEntity.getField(TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME);
        int processingMax = this.compressorContainer.tileEntity.getField(TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX);

        float coeff = (processingMax-processingTime)/(float)processingMax;
        return (int)(coeff*size);
    }

    private int getEnergyScale(int size)
    {
        int energy = this.compressorContainer.tileEntity.getField(TileEntityCompressor.FIELD_ENERGY_COUNT);
        int capacity = this.compressorContainer.tileEntity.getField(TileEntityCompressor.FIELD_ENERGY_CAPACITY);

        float coeff = (capacity-energy)/(float)capacity;
        return (int)(coeff*size);
    }

    // Progress Indicator - 100,33 24x17
    // Power Indicator - 19,8 12x70
    // Top Compressor - 72, 5 32x22
    // Bottom Compressor - 72, 53 32x22
}
