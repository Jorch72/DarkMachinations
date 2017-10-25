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
