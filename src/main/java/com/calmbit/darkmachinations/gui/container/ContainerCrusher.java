package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityCrusher;
import net.minecraft.inventory.IInventory;

public class ContainerCrusher extends ContainerBase<TileEntityCrusher> {

    public static final int CRUSHER_SUPPLY_SLOT= 0;
    public static final int CRUSHER_PRODUCT_SLOT=1;

    public ContainerCrusher(TileEntityCrusher crusher, IInventory playerInventory, IInventory crusherInventory)
    {
        super(crusher, playerInventory, crusherInventory);

        // First Slot - 80,11
        // Product Slot - 80, 60
        // Progess - 82,29 size 12x15
    }

}
