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

package com.elytradev.darkmachinations.init;

import com.elytradev.darkmachinations.DarkMachinations;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class DMFluids {
	public static final Fluid fluid_heavy_crude_oil = new Fluid(DarkMachinations.MOD_ID +".heavy_crude_oil",
			new ResourceLocation("darkmachinations:blocks/fluid_heavy_crude_oil_still"),
			new ResourceLocation("darkmachinations:blocks/fluid_heavy_crude_oil_flowing"))
			.setLuminosity(0).setDensity(960).setViscosity(9000).setTemperature(300);

	public static final Fluid fluid_fuel = new Fluid(DarkMachinations.MOD_ID +".fuel",
			new ResourceLocation("darkmachinations:blocks/fluid_fuel_still"),
			new ResourceLocation("darkmachinations:blocks/fluid_fuel_flowing"))
			.setLuminosity(0).setDensity(720).setViscosity(750).setTemperature(300);

	public static final Fluid fluid_hydrogen_gas = new Fluid(DarkMachinations.MOD_ID +".hydrogen_gas",
			new ResourceLocation("darkmachinations:blocks/fluid_hydrogen_gas_still"),
			new ResourceLocation("darkmachinations:blocks/fluid_hydrogen_gas_flowing"))
			.setLuminosity(0).setDensity(0).setViscosity(10).setTemperature(300).setGaseous(true);


	public static void init() {
		net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid_heavy_crude_oil);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid_heavy_crude_oil);

		net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid_fuel);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid_fuel);

		net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid_hydrogen_gas);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid_hydrogen_gas);
	}
}
