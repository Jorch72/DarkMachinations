package com.elytradev.darkmachinations.gui.container;

import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;
import com.elytradev.darkmachinations.gui.WOverloadedBar;
import com.elytradev.darkmachinations.gui.WOverloadedPanel;
import com.elytradev.darkmachinations.tileentity.TileEntityPowerCell;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerPowerCell extends ContainerBase<TileEntityPowerCell> {
	public ContainerPowerCell(TileEntityPowerCell powerCell, IInventory playerInventory, IInventory cellInventory) {
		super(powerCell, playerInventory, cellInventory);

		WOverloadedPanel panel = new WOverloadedPanel();
		this.setRootPanel(panel);

		panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
		panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
		panel.add(new WOverloadedBar(
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg_grey.png"),
				cellInventory,
				TileEntityPowerCell.FIELD_ENERGY_COUNT,
				TileEntityPowerCell.FIELD_ENERGY_CAPACITY,
				WBar.Direction.UP).withTooltip("∞/∞ FU"), 64, 10, 36, 68);
		//panel.add(new WLabel("Solid-Fuel Generator"), 0, 0);
	}
}
