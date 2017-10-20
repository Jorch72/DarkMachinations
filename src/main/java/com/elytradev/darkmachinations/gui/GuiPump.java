package com.elytradev.darkmachinations.gui;

import com.elytradev.darkmachinations.gui.container.ContainerPump;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;

public class GuiPump extends ConcreteGui{

	private final ContainerPump pumpContainer;

	private WItemSlot itemSlots;
	public GuiPump(ConcreteContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		this.pumpContainer = (ContainerPump) inventorySlotsIn;
	}





}
