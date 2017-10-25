package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.tileentity.TileEntitySolidGenerator;
import com.elytradev.darkmachinations.registry.GuiRegistry;
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

public class BlockSolidGenerator extends BlockMachineBase<TileEntitySolidGenerator> {
	public BlockSolidGenerator() {
		super(Material.IRON, "machine_solid_generator", GuiRegistry.GUI_MACHINE_SOLID_GENERATOR);
	}


	@Override
	public Class<TileEntitySolidGenerator> getTileEntityClass() {
		return TileEntitySolidGenerator.class;
	}

	@Nullable
	@Override
	public TileEntitySolidGenerator createTileEntity(World world, IBlockState state) {
		return new TileEntitySolidGenerator();
	}
}
