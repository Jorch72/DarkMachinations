package com.elytradev.darkmachinations.registry;

import com.elytradev.darkmachinations.DarkMachinations;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidRegistry {
	public static final Fluid fluid_heavy_crude_oil = new Fluid(DarkMachinations.DARKMACHINATIONS_MOD_ID+".heavy_crude_oil",
			new ResourceLocation("darkmachinations:blocks/fluid_heavy_crude_oil_still"),
			new ResourceLocation("darkmachinations:blocks/fluid_heavy_crude_oil_flowing"))
			.setLuminosity(0).setDensity(960).setViscosity(9000).setTemperature(300);

	public static final Fluid fluid_fuel = new Fluid(DarkMachinations.DARKMACHINATIONS_MOD_ID+".fuel",
			new ResourceLocation("darkmachinations:blocks/fluid_fuel_still"),
			new ResourceLocation("darkmachinations:blocks/fluid_fuel_flowing"))
			.setLuminosity(0).setDensity(720).setViscosity(750).setTemperature(300);

	public static final Fluid fluid_hydrogen_gas = new Fluid(DarkMachinations.DARKMACHINATIONS_MOD_ID+".hydrogen_gas",
			new ResourceLocation("darkmachinations:blocks/fluid_hydrogen_gas_still"),
			new ResourceLocation("darkmachinations:blocks/fluid_hydrogen_gas_flowing"))
			.setLuminosity(0).setDensity(0).setViscosity(10).setTemperature(300).setGaseous(true);


	public static void init()
	{
		net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid_heavy_crude_oil);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid_heavy_crude_oil);

		net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid_fuel);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid_fuel);

		net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid_hydrogen_gas);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid_hydrogen_gas);
	}
}
