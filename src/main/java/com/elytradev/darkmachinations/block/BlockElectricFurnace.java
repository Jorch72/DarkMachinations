package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.registry.GuiRegistry;
import com.elytradev.darkmachinations.tileentity.TileEntityCompressor;
import com.elytradev.darkmachinations.tileentity.TileEntityElectricFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockElectricFurnace extends BlockMachineBase<TileEntityElectricFurnace> {
	public BlockElectricFurnace() {
		super(Material.IRON, "machine_electric_furnace", GuiRegistry.GUI_MACHINE_ELECTRIC_FURNACE);
	}


	@Override
	public Class<TileEntityElectricFurnace> getTileEntityClass() {
		return TileEntityElectricFurnace.class;
	}

	@Nullable
	@Override
	public TileEntityElectricFurnace createTileEntity(World world, IBlockState state) {
		return new TileEntityElectricFurnace();
	}

}
