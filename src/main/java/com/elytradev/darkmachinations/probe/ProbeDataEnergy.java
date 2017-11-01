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
import com.elytradev.probe.api.IUnit;
import com.elytradev.probe.api.UnitDictionary;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public class ProbeDataEnergy implements IProbeData {

	double minEnergy, currEnergy, maxEnergy;

	public ProbeDataEnergy(double curr, double max)
	{
		minEnergy = 0;
		currEnergy = curr;
		maxEnergy = max;
	}

	@Override
	public boolean hasLabel() {
		return true;
	}

	@Override
	public boolean hasBar() {
		return true;
	}

	@Override
	public boolean hasInventory() {
		return false;
	}

	@Nonnull
	@Override
	public ITextComponent getLabel() {
		return new TextComponentString("Energy: ");
	}

	@Override
	public double getBarMinimum() {
		return minEnergy;
	}

	@Override
	public double getBarCurrent() {
		return currEnergy;
	}

	@Override
	public double getBarMaximum() {
		return maxEnergy;
	}

	@Nonnull
	@Override
	public IUnit getBarUnit() {
		return UnitDictionary.FORGE_ENERGY;
	}

	@Nonnull
	@Override
	public ImmutableList<ItemStack> getInventory() {
		return null;
	}
}
