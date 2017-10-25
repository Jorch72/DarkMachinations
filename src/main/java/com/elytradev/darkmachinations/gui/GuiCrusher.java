package com.elytradev.darkmachinations.gui;

import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.darkmachinations.gui.container.ContainerCrusher;
import com.elytradev.darkmachinations.tileentity.TileEntityCrusher;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCrusher extends ConcreteGui {

	private final ContainerCrusher crusherContainer;

	public GuiCrusher(ConcreteContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		this.crusherContainer = (ContainerCrusher)inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

}
