package com.calmbit.darkmachinations.machine;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCompressor extends ContainerBase<TileEntityCompressor> {

    public static final int COMPRESSOR_SUPPLY_SLOT= 0;
    public static final int COMPRESSOR_PRODUCT_SLOT=1;

    public ContainerCompressor(TileEntityCompressor compressor, IInventory playerInventory)
    {
        super(compressor, playerInventory);
    }
}
