package com.elytradev.darkmachinations.gui;

import com.elytradev.darkmachinations.gui.container.ContainerCompressor;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;

public class GuiCompressor extends ConcreteGui{

	private final ContainerCompressor compressorContainer;

	public GuiCompressor(ConcreteContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		this.compressorContainer = (ContainerCompressor) inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

}
