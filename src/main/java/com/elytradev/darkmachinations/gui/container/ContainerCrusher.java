package com.elytradev.darkmachinations.gui.container;

import com.elytradev.darkmachinations.tileentity.TileEntityCrusher;
import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.concrete.inventory.gui.widget.WLabel;
import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerCrusher extends ContainerBase<TileEntityCrusher> {

	public ContainerCrusher(TileEntityCrusher crusher, IInventory playerInventory, IInventory crusherInventory)
	{
		super(crusher, playerInventory, crusherInventory);

		WPlainPanel panel = new WPlainPanel();
		this.setRootPanel(panel);

		panel.add(WItemSlot.of(crusherInventory, StandardMachineSlots.INPUT), 4*18, 11);
		panel.add(new WItemSlot(crusherInventory, StandardMachineSlots.OUTPUT, 1, 1, true, true), 4*18, 60);
		panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
		panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
				new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg.png"),
				crusherInventory,
				TileEntityCrusher.FIELD_ENERGY_COUNT,
				TileEntityCrusher.FIELD_ENERGY_CAPACITY,
				WBar.Direction.UP).withTooltip("%d/%d FU"), 0, 10, 18, 68);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations", "textures/gui/machine/bubbles_bg.png"),
				new ResourceLocation("darkmachinations", "textures/gui/machine/bubbles_fg.png"),
				crusherInventory,
				TileEntityCrusher.FIELD_ITEM_PROCESSING_TIME,
				TileEntityCrusher.FIELD_ITEM_PROCESSING_MAX,
				WBar.Direction.DOWN), 4*18, 30, 12, 25);
		panel.add(new WLabel("Crusher"), 0, 0);
	}

}
