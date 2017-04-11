package com.CalmBit.Divitae;

import com.CalmBit.Divitae.machine.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler {

    public static final int GUI_MACHINE_GRINDER = 0;
    public static final int GUI_MACHINE_COMPRESSOR = 1;
    public static final int GUI_MACHINE_GENERATOR = 2;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
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
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID)
        {
            case GUI_MACHINE_GRINDER:
                return new GuiGrinder(new ContainerGrinder((TileEntityGrinder)world.getTileEntity(new BlockPos(x,y,z)), player.inventory));
            case GUI_MACHINE_COMPRESSOR:
                return new GuiCompressor(new ContainerCompressor((TileEntityCompressor)world.getTileEntity(new BlockPos(x,y,z)), player.inventory));
            case GUI_MACHINE_GENERATOR:
                return new GuiGenerator(new ContainerGenerator((TileEntityGenerator) world.getTileEntity(new BlockPos(x,y,z)), player.inventory));
        }
        return null;
    }
}
