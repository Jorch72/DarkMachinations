package com.elytradev.darkmachinations.gui;

import com.elytradev.darkmachinations.gui.container.ContainerSolidGenerator;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;

public class GuiSolidGenerator extends ConcreteGui{

	private final ContainerSolidGenerator generatorContainer;

	private WItemSlot itemSlots;
	public GuiSolidGenerator(ConcreteContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		this.generatorContainer = (ContainerSolidGenerator) inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

}
