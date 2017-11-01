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

package com.elytradev.darkmachinations.tileentity;

import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.IContainerInventoryHolder;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import com.elytradev.darkmachinations.generic.ITEStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBloomery extends TileEntityBase implements ITEStackHandler, IContainerInventoryHolder {
	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public int getSlotCount() {
		return 0;
	}

	@Override
	public boolean getActive() {
		return false;
	}

	@Override
	public void setActive(boolean value) {

	}

	@Override
	public ItemStackHandler getItemStackHandler() {
		return null;
	}

	@Override
	public void update() {

	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public void writeItemData(NBTTagCompound compound) {

	}

	@Override
	public void readItemData(NBTTagCompound compound) {

	}

	@Override
	public IInventory getContainerInventory() {
		return new ValidatedInventoryView((ConcreteItemStorage) this.getItemStackHandler());
	}
}
