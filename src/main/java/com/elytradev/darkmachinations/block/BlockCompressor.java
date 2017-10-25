package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.tileentity.TileEntityCompressor;
import com.elytradev.darkmachinations.registry.GuiRegistry;
import com.elytradev.darkmachinations.tileentity.TileEntitySolidGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCompressor extends BlockMachineBase<TileEntityCompressor> {
	public BlockCompressor() {
		super(Material.IRON, "machine_compressor", GuiRegistry.GUI_MACHINE_COMPRESSOR);
	}


	@Override
	public Class<TileEntityCompressor> getTileEntityClass() {
		return TileEntityCompressor.class;
	}

	@Nullable
	@Override
	public TileEntityCompressor createTileEntity(World world, IBlockState state) {
		return new TileEntityCompressor();
	}

}
