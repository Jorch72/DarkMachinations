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

package com.elytradev.darkmachinations.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class EnergyUser extends EnergyStorage {
	private ArrayList<Runnable> listeners = new ArrayList<>();

	public EnergyUser(int capacity, int transferRate) {
		super(capacity, transferRate);
	}

	public void setEnergyStored(int newStorage)
	{
		if (newStorage > this.capacity || newStorage < 0)
			return;

		else this.energy = newStorage;

		this.markDirty();
	}

	/* Borrowed with great thanks and love to Falkreon and Thermionics */

	void markDirty() {
		for (Runnable r : listeners) {
			r.run();
		}
	}

	public void listen(@Nonnull Runnable r) {
		listeners.add(r);
	}

	/* End Borrow - Thanks Falkreon <3 */

	public void setEnergyCapacity(int newCapacity)
	{
		this.capacity = newCapacity;
		markDirty();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!simulate && this.canReceive()) {
			markDirty();
		}
		return super.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!simulate && this.canExtract()) markDirty();
		return super.extractEnergy(maxExtract, simulate);
	}


	public void readFromNBT(NBTTagCompound compound) {
		this.energy = compound.getInteger("power_level");
	}

	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger("power_level", this.energy);
	}
}
