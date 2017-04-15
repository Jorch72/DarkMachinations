package com.CalmBit.DarkMachinations.machine;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiGenerator extends GuiContainer{
    private final ContainerGenerator generatorContainer;
    public static final ResourceLocation guiLocation = new ResourceLocation("darkmachinations:textures/gui/machine/generator.png");
    public GuiGenerator(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.generatorContainer = (ContainerGenerator) inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if(mouseX >= i+19 && mouseX < i+19+12 && mouseY >= j+9 && mouseY < j+9+70)
        {
            List list = new ArrayList();
            list.add("Energy: " + generatorContainer.tileEntity.getField(TileEntityGenerator.FIELD_ENERGY_COUNT) + "/" + generatorContainer.tileEntity.getField(TileEntityGenerator.FIELD_ENERGY_CAPACITY));
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
        int progressScale = getProgressScale(14);
        this.drawTexturedModalRect(i+86, j+50+progressScale, this.xSize, progressScale, 14, 14-progressScale);
        this.drawTexturedModalRect(i+19, j+8+energyScale, this.xSize, 14, 12, 70-energyScale);
    }

    private int getProgressScale(int size)
    {
        int processingTime = this.generatorContainer.tileEntity.getField(TileEntityGenerator.FIELD_ITEM_PROCESSING_TIME);
        int processingMax = this.generatorContainer.tileEntity.getField(TileEntityGenerator.FIELD_ITEM_PROCESSING_MAX);

        float coeff = (processingMax-processingTime)/(float)processingMax;
        return (int)(coeff*size);
    }

    private int getEnergyScale(int size)
    {
        int energy = this.generatorContainer.tileEntity.getField(TileEntityGenerator.FIELD_ENERGY_COUNT);
        int capacity = this.generatorContainer.tileEntity.getField(TileEntityGenerator.FIELD_ENERGY_CAPACITY);

        float coeff = (capacity-energy)/(float)capacity;
        return (int)(coeff*size);
    }

    // Burn Indicator - 86,50 14x14
    // Power Indicator - 19,8 12x70

}
