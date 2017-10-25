package com.elytradev.darkmachinations.gui.container;

import com.elytradev.darkmachinations.tileentity.TileEntitySolidGenerator;
import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.gui.widget.*;
import com.elytradev.concrete.inventory.gui.widget.WBar.Direction;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerSolidGenerator extends ContainerBase<TileEntitySolidGenerator> {

	public ContainerSolidGenerator(TileEntitySolidGenerator generator, IInventory playerInventory, IInventory generatorInventory)
	{
		super(generator, playerInventory, generatorInventory);

		WPlainPanel panel = new WPlainPanel();
		this.setRootPanel(panel);

		panel.add(WItemSlot.of(generatorInventory, StandardMachineSlots.INPUT), 4*18, 18);
		panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
		panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg.png"),
				generatorInventory,
				TileEntitySolidGenerator.FIELD_ENERGY_COUNT,
				TileEntitySolidGenerator.FIELD_ENERGY_CAPACITY,
				WBar.Direction.UP).withTooltip("%d/%d FU"), 0, 10, 18, 68);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations", "textures/gui/machine/burning_bg.png"),
				new ResourceLocation("darkmachinations", "textures/gui/machine/burning_fg.png"),
				generatorInventory,
				TileEntitySolidGenerator.FIELD_ITEM_PROCESSING_TIME,
				TileEntitySolidGenerator.FIELD_ITEM_PROCESSING_MAX,
				Direction.UP), 4*18, 36, 14, 14);
		panel.add(new WLabel("Solid-Fuel Generator"), 0, 0);
	}

}