package com.calmbit.darkmachinations.registry;

import com.calmbit.darkmachinations.gui.GuiCompressor;
import com.calmbit.darkmachinations.gui.GuiCrusher;
import com.calmbit.darkmachinations.gui.GuiGenerator;
import com.calmbit.darkmachinations.gui.GuiPump;
import com.calmbit.darkmachinations.gui.container.ContainerCompressor;
import com.calmbit.darkmachinations.gui.container.ContainerCrusher;
import com.calmbit.darkmachinations.gui.container.ContainerGenerator;
import com.calmbit.darkmachinations.gui.container.ContainerPump;
import com.calmbit.darkmachinations.tileentity.*;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler {

    public static final int GUI_MACHINE_CRUSHER = 0;
    public static final int GUI_MACHINE_COMPRESSOR = 1;
    public static final int GUI_MACHINE_GENERATOR = 2;
    public static final int GUI_MACHINE_BLOOMERY = 3;
    public static final int GUI_MACHINE_PUMP = 4;

    @Nullable
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID)
        {
            case GUI_MACHINE_CRUSHER:
                return new ContainerCrusher((TileEntityCrusher)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
            case GUI_MACHINE_COMPRESSOR:
                return new ContainerCompressor((TileEntityCompressor)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
            case GUI_MACHINE_GENERATOR:
                return new ContainerGenerator((TileEntityGenerator)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
            case GUI_MACHINE_PUMP:
                return new ContainerPump((TileEntityPump)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
        }
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID)
        {
            case GUI_MACHINE_CRUSHER:
                return new GuiCrusher(getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_COMPRESSOR:
                return new GuiCompressor(getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_GENERATOR:
                return new GuiGenerator((ConcreteContainer) getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_PUMP:
                return new GuiPump(getServerGuiElement(ID, player, world, x, y, z));
        }
        return null;
    }
}
