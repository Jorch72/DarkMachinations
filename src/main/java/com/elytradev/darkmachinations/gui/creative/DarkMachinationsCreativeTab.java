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

package com.elytradev.darkmachinations.gui.creative;

import com.elytradev.darkmachinations.init.DMBlocks;
import com.elytradev.darkmachinations.init.DMFluids;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.time.LocalDateTime;
import java.time.Month;

public class DarkMachinationsCreativeTab extends CreativeTabs {

	public DarkMachinationsCreativeTab(String name) {
		super(name + ((LocalDateTime.now().getMonth() == Month.APRIL
				&& LocalDateTime.now().getDayOfMonth() == 1) ? "_af" : ""));
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(DMBlocks.machine_generator);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stacks) {
		super.displayAllRelevantItems(stacks);

		stacks.add(FluidUtil.getFilledBucket(new FluidStack(DMFluids.fluid_heavy_crude_oil, 1000)));
		stacks.add(FluidUtil.getFilledBucket(new FluidStack(DMFluids.fluid_fuel, 1000)));
		stacks.add(FluidUtil.getFilledBucket(new FluidStack(DMFluids.fluid_hydrogen_gas, 1000)));
	}
}
