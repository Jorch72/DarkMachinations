package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.tileentity.TileEntityCrusher;
import com.elytradev.darkmachinations.registry.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCrusher extends BlockMachineBase<TileEntityCrusher> {

	public BlockCrusher() {
		super(Material.IRON, "machine_crusher", GuiRegistry.GUI_MACHINE_CRUSHER);
	}

	@Override
	public Class<TileEntityCrusher> getTileEntityClass()
	{
		return TileEntityCrusher.class;
	}

	@Nullable
	@Override
	public TileEntityCrusher createTileEntity(World world, IBlockState state)
	{
		return new TileEntityCrusher();
	}

}
