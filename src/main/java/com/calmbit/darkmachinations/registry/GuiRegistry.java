package com.calmbit.darkmachinations.registry;

import com.calmbit.darkmachinations.gui.GuiCompressor;
import com.calmbit.darkmachinations.gui.GuiCrusher;
import com.calmbit.darkmachinations.gui.GuiGenerator;
import com.calmbit.darkmachinations.gui.GuiPump;
import com.calmbit.darkmachinations.gui.container.ContainerCompressor;
import com.calmbit.darkmachinations.gui.container.ContainerCrusher;
import com.calmbit.darkmachinations.gui.container.ContainerGenerator;
import com.calmbit.darkmachinations.gui.container.ContainerPump;
import com.calmbit.darkmachinations.tileentity.TileEntityCompressor;
import com.calmbit.darkmachinations.tileentity.TileEntityCrusher;
import com.calmbit.darkmachinations.tileentity.TileEntityGenerator;
import com.calmbit.darkmachinations.tileentity.TileEntityPump;
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
        ConcreteContainer container = null;
        switch(ID)
        {
            case GUI_MACHINE_CRUSHER:
                container =  new ContainerCrusher((TileEntityCrusher)world.getTileEntity(new BlockPos(x,y,z)), player.inventory,((TileEntityCrusher) world.getTileEntity(new BlockPos(x,y,z))).getContainerInventory());
                break;
            case GUI_MACHINE_COMPRESSOR:
                container = new ContainerCompressor((TileEntityCompressor)world.getTileEntity(new BlockPos(x,y,z)), player.inventory,((TileEntityCompressor) world.getTileEntity(new BlockPos(x,y,z))).getContainerInventory());
                break;
            case GUI_MACHINE_GENERATOR:
                container = new ContainerGenerator((TileEntityGenerator)world.getTileEntity(new BlockPos(x,y,z)), player.inventory,((TileEntityGenerator) world.getTileEntity(new BlockPos(x,y,z))).getContainerInventory());
                break;
            case GUI_MACHINE_PUMP:
                container = new ContainerPump((TileEntityPump)world.getTileEntity(new BlockPos(x,y,z)), player.inventory, ((TileEntityPump) world.getTileEntity(new BlockPos(x,y,z))).getContainerInventory());
                break;
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
                return new GuiCrusher(getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_COMPRESSOR:
                return new GuiCompressor((ConcreteContainer)getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_GENERATOR:
                return new GuiGenerator((ConcreteContainer) getServerGuiElement(ID, player, world, x, y, z));
            case GUI_MACHINE_PUMP:
                return new GuiPump((ConcreteContainer) getServerGuiElement(ID, player, world, x, y, z));
        }
        return null;
    }
}
