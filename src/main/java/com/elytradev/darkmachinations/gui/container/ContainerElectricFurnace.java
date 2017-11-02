/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017:
 *     Ethan Brooks (CalmBit),
 *     and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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

	public ContainerElectricFurnace(TileEntityElectricFurnace furnace, IInventory playerInventory, IInventory furnaceInventory) {
		super(furnace, playerInventory, furnaceInventory);

		WPlainPanel panel = new WPlainPanel();
		this.setRootPanel(panel);

		panel.add(WItemSlot.of(furnaceInventory, StandardMachineSlots.INPUT), 4*18, (int)(1.75f*18));
		panel.add(WItemSlot.outputOf(furnaceInventory, StandardMachineSlots.OUTPUT), 7*18, (int)(1.75f*18));
		panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
		panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/energy_bar_bg.png"),
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/energy_bar_fg.png"),
				furnaceInventory,
				TileEntityCompressor.FIELD_ENERGY_COUNT,
				TileEntityCompressor.FIELD_ENERGY_CAPACITY,
				WBar.Direction.UP).withTooltip("%d/%d FU"), 0, 10, 18, 68);
	}
}
