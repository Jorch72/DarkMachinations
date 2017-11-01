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

import com.elytradev.darkmachinations.registry.BlockRegistry;
import com.elytradev.darkmachinations.registry.FluidRegistry;
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

	public DarkMachinationsCreativeTab(String name)
	{
		super(name + ((LocalDateTime.now().getMonth() == Month.APRIL && LocalDateTime.now().getDayOfMonth() == 1) ? "_af" : ""));
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(BlockRegistry.machine_compressor);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
		super.displayAllRelevantItems(p_78018_1_);
		p_78018_1_.add(FluidUtil.getFilledBucket(new FluidStack(FluidRegistry.fluid_heavy_crude_oil, 1000)));
		p_78018_1_.add(FluidUtil.getFilledBucket(new FluidStack(FluidRegistry.fluid_fuel, 1000)));
	}
}
