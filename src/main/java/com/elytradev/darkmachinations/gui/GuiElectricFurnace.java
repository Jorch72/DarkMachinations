package com.elytradev.darkmachinations.gui;

import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.darkmachinations.gui.container.ContainerElectricFurnace;

public class GuiElectricFurnace extends ConcreteGui{

	private final ContainerElectricFurnace electricFurnaceContainer;

	public GuiElectricFurnace(ConcreteContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		this.electricFurnaceContainer = (ContainerElectricFurnace) inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

}
