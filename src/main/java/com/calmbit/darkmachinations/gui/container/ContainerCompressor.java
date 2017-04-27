package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityCompressor;
import net.minecraft.inventory.IInventory;

public class ContainerCompressor extends ContainerBase<TileEntityCompressor> {

    public static final int COMPRESSOR_SUPPLY_SLOT= 0;
    public static final int COMPRESSOR_PRODUCT_SLOT=1;

    public ContainerCompressor(TileEntityCompressor compressor, IInventory playerInventory)
    {
        super(compressor, playerInventory);
    }
}
