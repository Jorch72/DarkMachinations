package com.CalmBit.DarkMachinations.machine;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCrusher extends GuiContainer {

    private final ContainerCrusher crusherContainer;
    public static final ResourceLocation guiLocation = new ResourceLocation("darkmachinations:textures/gui/machine/crusher.png");
    public GuiCrusher(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.crusherContainer = (ContainerCrusher)inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if(mouseX >= i+19 && mouseX < i+19+12 && mouseY >= j+9 && mouseY < j+9+70)
        {
            List list = new ArrayList();
            list.add("Energy: " + crusherContainer.tileEntity.getField(TileEntityCrusher.FIELD_ENERGY_COUNT) + "/" + crusherContainer.tileEntity.getField(TileEntityCrusher.FIELD_ENERGY_CAPACITY));
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
        this.drawTexturedModalRect(i+19, j+8+energyScale, this.xSize, 25, 12, 70-energyScale);
        if(this.crusherContainer.tileEntity.isActive) {
            int progressScale = getProgressScale(25);
            this.drawTexturedModalRect(i + 82, j + 29, this.xSize, 0, 12, progressScale);
        }
    }

    private int getProgressScale(int size)
    {
        int processingTime = this.crusherContainer.tileEntity.getField(TileEntityCrusher.FIELD_ITEM_PROCESSING_TIME);
        int processingMax = this.crusherContainer.tileEntity.getField(TileEntityCrusher.FIELD_ITEM_PROCESSING_MAX);

        float coeff = (processingMax-processingTime)/(float)processingMax;
        return (int)(coeff*size);
    }

    private int getEnergyScale(int size)
    {
        int energy = this.crusherContainer.tileEntity.getField(TileEntityCrusher.FIELD_ENERGY_COUNT);
        int capacity = this.crusherContainer.tileEntity.getField(TileEntityCrusher.FIELD_ENERGY_CAPACITY);

        float coeff = (capacity-energy)/(float)capacity;
        return (int)(coeff*size);
    }
}
