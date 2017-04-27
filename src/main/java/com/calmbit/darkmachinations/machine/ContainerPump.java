package com.calmbit.darkmachinations.machine;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPump extends ContainerBase<TileEntityPump> {

    public static final int PUMP_BLOCK_SLOT= 0;

    public ContainerPump(TileEntityPump pump, IInventory playerInventory)
    {
        super(pump, playerInventory);
    }

}
