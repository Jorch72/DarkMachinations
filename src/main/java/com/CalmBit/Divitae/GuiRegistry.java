package com.CalmBit.Divitae;

import com.CalmBit.Divitae.machine.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler {

    public static final int GUI_MACHINE_GRINDER = 0;
    public static final int GUI_MACHINE_COMPRESSOR = 1;
    public static final int GUI_MACHINE_GENERATOR = 2;

    @Nullable
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID)
        {
            case GUI_MACHINE_GRINDER:
                return new ContainerGrinder((TileEntityGrinder)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
            case GUI_MACHINE_COMPRESSOR:
                return new ContainerCompressor((TileEntityCompressor)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
            case GUI_MACHINE_GENERATOR:
                return new ContainerGenerator((TileEntityGenerator)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
        }
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID)
        {
            case GUI_MACHINE_GRINDER:
                return new GuiGrinder(getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_COMPRESSOR:
                return new GuiCompressor(getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_GENERATOR:
                return new GuiGenerator(getServerGuiElement(ID, player, world, x, y, z));
        }
        return null;
    }
}
