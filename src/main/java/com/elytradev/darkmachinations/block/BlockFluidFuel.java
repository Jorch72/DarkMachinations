package com.elytradev.darkmachinations.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidFuel extends BlockFluidBaseClassic {
	public BlockFluidFuel(Fluid fluid, Material material) {
		super(fluid, material, "fuel");
	}
}
