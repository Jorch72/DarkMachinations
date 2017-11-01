package com.elytradev.darkmachinations.registry;

import com.elytradev.darkmachinations.gui.*;
import com.elytradev.darkmachinations.gui.container.*;
import com.elytradev.darkmachinations.tileentity.*;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler {

	public static final int GUI_MACHINE_CRUSHER = 0;
	public static final int GUI_MACHINE_COMPRESSOR = 1;
	public static final int GUI_MACHINE_SOLID_GENERATOR = 2;
	public static final int GUI_MACHINE_BLOOMERY = 3;
	public static final int GUI_MACHINE_PUMP = 4;
	public static final int GUI_MACHINE_ELECTRIC_FURNACE = 5;
	public static final int GUI_POWER_CELL = 6;

	@Nullable
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		ConcreteContainer container = null;
		switch(ID)
		{
			case GUI_MACHINE_CRUSHER: {
				container = new ContainerCrusher((TileEntityCrusher) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, ((TileEntityCrusher) world.getTileEntity(new BlockPos(x, y, z))).getContainerInventory());
				break;
			}
			case GUI_MACHINE_COMPRESSOR: {
				container = new ContainerCompressor((TileEntityCompressor) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, ((TileEntityCompressor) world.getTileEntity(new BlockPos(x, y, z))).getContainerInventory());
				break;
			}
			case GUI_MACHINE_SOLID_GENERATOR: {
				container = new ContainerSolidGenerator((TileEntitySolidGenerator) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, ((TileEntitySolidGenerator) world.getTileEntity(new BlockPos(x, y, z))).getContainerInventory());
				break;
			}
			case GUI_MACHINE_PUMP: {
				container = new ContainerPump((TileEntityPump) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, ((TileEntityPump) world.getTileEntity(new BlockPos(x, y, z))).getContainerInventory());
				break;
			}
			case GUI_MACHINE_ELECTRIC_FURNACE: {
				container = new ContainerElectricFurnace((TileEntityElectricFurnace) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, ((TileEntityElectricFurnace) world.getTileEntity(new BlockPos(x, y, z))).getContainerInventory());
				break;
			}
			case GUI_POWER_CELL: {
				container = new ContainerPowerCell((TileEntityPowerCell) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, ((TileEntityPowerCell) world.getTileEntity(new BlockPos(x, y, z))).getContainerInventory());
				break;
			}
		}
		if(container != null)
			container.validate();

		return container;
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID)
		{
			case GUI_MACHINE_CRUSHER:
				return new GuiBase((ConcreteContainer)getServerGuiElement(ID, player, world, x, y, z));
			case GUI_MACHINE_COMPRESSOR:
				return new GuiBase((ConcreteContainer)getServerGuiElement(ID, player, world, x, y, z));
			case GUI_MACHINE_SOLID_GENERATOR:
				return new GuiBase((ConcreteContainer)getServerGuiElement(ID, player, world, x, y, z));
			case GUI_MACHINE_PUMP:
				return new GuiBase((ConcreteContainer)getServerGuiElement(ID, player, world, x, y, z));
			case GUI_MACHINE_ELECTRIC_FURNACE:
				return new GuiBase((ConcreteContainer)getServerGuiElement(ID, player, world, x, y, z));
			case GUI_POWER_CELL:
				return new GuiBase((ConcreteContainer)getServerGuiElement(ID, player, world, x, y, z));
		}
		return null;
	}
}
