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

package com.elytradev.darkmachinations.probe;

import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IProbeDataProvider;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;


public class ProbeDataProviderGenerator implements IProbeDataProvider {

	private double currEnergy, maxEnergy;

	private double currTicks, maxTicks;
	private boolean active;

	private ItemStackHandler stackHandler;

	@Override
	public void provideProbeData(List<IProbeData> data) {
		data.add(new ProbeDataEnergy(currEnergy, maxEnergy));
		data.add(new ProbeDataFuel(currTicks, maxTicks, active));
		data.add(new ProbeDataItemStackHandler(stackHandler));
	}

	public void updateProbeEnergyData(double currEnergy, double maxEnergy) {
		this.currEnergy = currEnergy;
		this.maxEnergy = maxEnergy;
	}

	public void updateProbeFuelData(double currTicks, double maxTicks, boolean active)
	{
		this.currTicks = currTicks;
		this.maxTicks = maxTicks;
		this.active = active;
	}

	public void setItemStackHandler(ItemStackHandler stackHandler) {
		this.stackHandler = stackHandler;
	}

	public double getCurrentEnergy() {
		return this.currEnergy;
	}

	public void setCurrentEnergy(double curr) {
		this.currEnergy = curr;
	}

	public double getMaximumEnergy() {
		return this.maxEnergy;
	}

	public void setMaximumEnergy(double max) {
		this.maxEnergy = max;
	}

	public double getCurrentTicks() {
		return currTicks;
	}

	public void setCurrentticks(double curr) {
		this.currTicks = curr;
	}


	public double getMaximumTicks() {
		return maxTicks;
	}

	public void setMaximumTicks(double max) {
		this.maxTicks = max;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
