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

import com.elytradev.darkmachinations.tileentity.TileEntityCompressor;
import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.concrete.inventory.gui.widget.WLabel;
import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerCompressor extends ContainerBase<TileEntityCompressor> {

	public ContainerCompressor(TileEntityCompressor compressor, IInventory playerInventory, IInventory compressorInventory) {
		super(compressor, playerInventory, compressorInventory);

		WPlainPanel panel = new WPlainPanel();
		this.setRootPanel(panel);

		panel.add(WItemSlot.of(compressorInventory, StandardMachineSlots.INPUT), 4*18, (int)(1.75f*18));
		panel.add(WItemSlot.of(compressorInventory, StandardMachineSlots.OUTPUT), 7*18, (int)(1.75f*18));
		panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
		panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/energy_bar_bg.png"),
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/energy_bar_fg.png"),
				compressorInventory,
				TileEntityCompressor.FIELD_ENERGY_COUNT,
				TileEntityCompressor.FIELD_ENERGY_CAPACITY,
				WBar.Direction.UP).withTooltip("%d/%d FU"), 0, 10, 18, 68);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/compressor_arrow_down_bg.png"),
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/compressor_arrow_down_fg.png"),
				compressorInventory,
				TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME,
				TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX,
				WBar.Direction.DOWN), (int)(3.6*18), 8, 32, 22);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/compressor_arrow_up_bg.png"),
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/compressor_arrow_up_fg.png"),
				compressorInventory,
				TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME,
				TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX,
				WBar.Direction.UP), (int)(3.6*18), 48, 32, 22);
		panel.add(new WBar(
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/arrow_right_bg.png"),
				new ResourceLocation("darkmachinations",
						"textures/gui/machine/arrow_right_fg.png"),
				compressorInventory,
				TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME,
				TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX,
				WBar.Direction.RIGHT), (int)(5.5*18), (int)(3.75*8), 24, 17);
		panel.add(new WLabel("Compressor"), 0, 0);
	}
}
