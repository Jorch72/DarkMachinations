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

package com.elytradev.darkmachinations.gui;

import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.Map;
import java.util.function.Supplier;

public class FakeInventoryView  implements IInventory {

	private int[] fields = new int[0];
	private final Map<Integer, Supplier<Integer>> fieldDelegates = Maps.newHashMap();

	public FakeInventoryView() {
		super();
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	/* All borrowed from Concrete, specifically ValidatedInventoryView */

	@Override
	public int getField(int id) {
		Supplier<Integer> delegate = fieldDelegates.get(id);
		if (delegate != null) return delegate.get();
		if (fields.length > id) return fields[id];
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (fields.length <= id) {
			int[] newFields = new int[id + 1];
			if (fields.length > 0) System.arraycopy(fields, 0, newFields, 0, fields.length);
			fields = newFields;
		}
		fields[id] = value;
	}

	@Override
	public int getFieldCount() {
		//TODO: This is prone to problems; assumes that fieldDelegates are contiguous
		return Math.max(fields.length, fieldDelegates.size());
	}

	/* End borrowing */

	@Override
	public void clear() {

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
	public ITextComponent getDisplayName() {
		return null;
	}

	public FakeInventoryView withField(int index, Supplier<Integer> delegate) {
		fieldDelegates.put(index, delegate);
		return this;
	}


}
