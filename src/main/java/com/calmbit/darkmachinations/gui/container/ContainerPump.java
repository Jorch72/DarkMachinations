package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityPump;
import net.minecraft.inventory.IInventory;

public class ContainerPump extends ContainerBase<TileEntityPump> {

    public static final int PUMP_BLOCK_SLOT= 0;

    public ContainerPump(TileEntityPump pump, IInventory playerInventory)
    {
        super(pump, playerInventory);
    }

}
