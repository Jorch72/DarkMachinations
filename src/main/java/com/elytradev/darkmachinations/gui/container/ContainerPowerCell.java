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

import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;
import com.elytradev.darkmachinations.gui.widget.WOverloadedBar;
import com.elytradev.darkmachinations.tileentity.TileEntityPowerCell;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerPowerCell extends ContainerBase<TileEntityPowerCell> {
	public ContainerPowerCell(TileEntityPowerCell powerCell, IInventory playerInventory, IInventory cellInventory) {
		super(powerCell, playerInventory, cellInventory);

		WPlainPanel panel = new WPlainPanel();
		this.setRootPanel(panel);

		panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
		panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);

		WBar energyBar = null;
		if (powerCell.isCreative) {
			energyBar = new WOverloadedBar(
					new ResourceLocation("darkmachinations",
							"textures/gui/machine/energy_bar_bg.png"),
					new ResourceLocation("darkmachinations",
							"textures/gui/machine/energy_bar_fg_grey.png"),
					cellInventory,
					TileEntityPowerCell.FIELD_ENERGY_COUNT,
					TileEntityPowerCell.FIELD_ENERGY_CAPACITY,
					WBar.Direction.UP).withTooltip("∞/∞ FU");
		} else {
			energyBar = new WBar(
					new ResourceLocation("darkmachinations",
							"textures/gui/machine/energy_bar_bg.png"),
					new ResourceLocation("darkmachinations",
							"textures/gui/machine/energy_bar_fg.png"),
					cellInventory,
					TileEntityPowerCell.FIELD_ENERGY_COUNT,
					TileEntityPowerCell.FIELD_ENERGY_CAPACITY,
					WBar.Direction.UP).withTooltip("%d/%d FU");
		}

		panel.add(energyBar, 64, 10, 36, 68);
	}
}
