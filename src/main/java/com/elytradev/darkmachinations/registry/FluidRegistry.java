package com.elytradev.darkmachinations.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidRegistry {
	public static final Fluid fluid_heavy_crude_oil = new Fluid("heavy_crude_oil",
			new ResourceLocation("darkmachinations:blocks/fluid_heavy_crude_oil_still"),
			new ResourceLocation("darkmachinations:blocks/fluid_heavy_crude_oil_flowing"))
			.setLuminosity(0).setDensity(960).setViscosity(9000).setTemperature(37);


	public static void init()
	{
		net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid_heavy_crude_oil);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid_heavy_crude_oil);

	}
}
