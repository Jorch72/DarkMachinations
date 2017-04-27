package com.calmbit.darkmachinations.gui;

import com.calmbit.darkmachinations.gui.container.ContainerPump;
import com.calmbit.darkmachinations.tileentity.TileEntityPump;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class GuiPump extends GuiContainer{
    private final ContainerPump pumpContainer;
    public static final ResourceLocation guiLocation = new ResourceLocation("darkmachinations:textures/gui/tileentity/pump.png");
    public GuiPump(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.pumpContainer = (ContainerPump) inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if(mouseX >= i+19 && mouseX < i+19+12 && mouseY >= j+9 && mouseY < j+9+70)
        {
            List list = new ArrayList();
            list.add("Energy: " + pumpContainer.tileEntity.getField(TileEntityPump.FIELD_ENERGY_COUNT) + "/" + pumpContainer.tileEntity.getField(TileEntityPump.FIELD_ENERGY_CAPACITY));
            this.drawHoveringText(list, mouseX-i, mouseY-j, this.fontRendererObj);
        }
        if(mouseX >= i+39 && mouseX < i+39+12 && mouseY >= j+9 && mouseY < j+9+70)
        {
            List list = new ArrayList();
            if(pumpContainer.tileEntity.fluidTank.getFluid() != null)
                list.add(pumpContainer.tileEntity.fluidTank.getFluid().getLocalizedName() + ": " + pumpContainer.tileEntity.getField(TileEntityPump.FIELD_FLUID_LEVEL) + "/" + pumpContainer.tileEntity.getField(TileEntityPump.FIELD_FLUID_CAPACITY));
            else
                list.add("No Fluid");
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
        int fluidScale = 70-getFluidScale(72);
        this.drawTexturedModalRect(i+86, j+50+progressScale, this.xSize, progressScale, 14, 14-progressScale);
        this.drawTexturedModalRect(i+19, j+8+energyScale, this.xSize, 14, 12, 70-energyScale);
        if(this.pumpContainer.tileEntity.fluidTank.getFluid() != null) {
            Fluid fluid = this.pumpContainer.tileEntity.fluidTank.getFluid().getFluid();
            ResourceLocation resourceLocation = fluid.getStill(this.pumpContainer.tileEntity.fluidTank.getFluid());
            TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(resourceLocation.toString());
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            for(int num = fluidScale;num >= 0;num-=16)
            {
                this.drawTexturedModalRect(i + 39, j +78-num, textureAtlasSprite, 12, Math.min(num, 16));
            }
        }
    }

    private int getProgressScale(int size)
    {
        int processingTime = this.pumpContainer.tileEntity.getField(TileEntityPump.FIELD_PUMP_TIMER);
        int processingMax = this.pumpContainer.tileEntity.getField(TileEntityPump.FIELD_PUMP_TIMER_MAX);

        float coeff = (processingMax-processingTime)/(float)processingMax;
        return (int)(coeff*size);
    }

    private int getEnergyScale(int size)
    {
        int energy = this.pumpContainer.tileEntity.getField(TileEntityPump.FIELD_ENERGY_COUNT);
        int capacity = this.pumpContainer.tileEntity.getField(TileEntityPump.FIELD_ENERGY_CAPACITY);

        float coeff = (capacity-energy)/(float)capacity;
        return (int)(coeff*size);
    }

    private int getFluidScale(int size)
    {
        int energy = this.pumpContainer.tileEntity.getField(TileEntityPump.FIELD_FLUID_LEVEL);
        int capacity = this.pumpContainer.tileEntity.getField(TileEntityPump.FIELD_FLUID_CAPACITY);

        float coeff = (capacity-energy)/(float)capacity;
        return (int)(coeff*size);
    }

    // Fluid Tank - 40, 9 (12x70)
}
