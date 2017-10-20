package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.tileentity.TileEntityBloomery;
import com.elytradev.darkmachinations.registry.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBloomery extends BlockMachineBase<TileEntityBloomery> {

	public BlockBloomery() {
		super(Material.ROCK, "machine_bloomery", GuiRegistry.GUI_MACHINE_BLOOMERY);
	}

	@Override
	public Class<TileEntityBloomery> getTileEntityClass() {
		return TileEntityBloomery.class;
	}

	@Nullable
	@Override
	public TileEntityBloomery createTileEntity(World world, IBlockState state) {
		return new TileEntityBloomery();
	}

}
