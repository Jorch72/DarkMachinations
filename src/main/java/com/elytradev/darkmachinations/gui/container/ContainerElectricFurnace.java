package com.elytradev.darkmachinations.gui.container;

import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;
import com.elytradev.darkmachinations.tileentity.TileEntityCompressor;
import com.elytradev.darkmachinations.tileentity.TileEntityElectricFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerElectricFurnace extends ContainerBase<TileEntityElectricFurnace> {

	public ContainerElectricFurnace(TileEntityElectricFurnace furnace, IInventory playerInventory, IInventory furnaceInventory)
	{
		super(furnace, playerInventory, furnaceInventory);

		WPlainPanel panel = new WPlainPanel();
		this.setRootPanel(panel);

		panel.add(WItemSlot.of(furnaceInventory, StandardMachineSlots.INPUT), 4*18, (int)(1.75f*18));
		panel.add(WItemSlot.outputOf(furnaceInventory, StandardMachineSlots.OUTPUT), 7*18, (int)(1.75f*18));
		panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
		panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg.png"),
				furnaceInventory,
				TileEntityCompressor.FIELD_ENERGY_COUNT,
				TileEntityCompressor.FIELD_ENERGY_CAPACITY,
				WBar.Direction.UP).withTooltip("%d/%d FU"), 0, 10, 18, 68);
	}
}
