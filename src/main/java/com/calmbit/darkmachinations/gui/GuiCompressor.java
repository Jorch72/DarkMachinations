package com.calmbit.darkmachinations.gui;

import com.calmbit.darkmachinations.gui.container.ContainerCompressor;
import com.calmbit.darkmachinations.tileentity.TileEntityCompressor;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCompressor extends ConcreteGui{

    private final ContainerCompressor compressorContainer;

    public GuiCompressor(ConcreteContainer inventorySlotsIn) {
        super(inventorySlotsIn);
        this.compressorContainer = (ContainerCompressor) inventorySlotsIn;
    }


    // Progress Indicator - 100,33 24x17
    // Power Indicator - 19,8 12x70
    // Top Compressor - 72, 5 32x22
    // Bottom Compressor - 72, 53 32x22
}
