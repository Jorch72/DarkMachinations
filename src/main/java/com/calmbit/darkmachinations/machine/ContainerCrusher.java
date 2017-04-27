package com.calmbit.darkmachinations.machine;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCrusher extends ContainerBase<TileEntityCrusher> {

    public static final int CRUSHER_SUPPLY_SLOT= 0;
    public static final int CRUSHER_PRODUCT_SLOT=1;

    public ContainerCrusher(TileEntityCrusher crusher, IInventory playerInventory)
    {
        super(crusher, playerInventory);

        // First Slot - 80,11
        // Product Slot - 80, 60
        // Progess - 82,29 size 12x15
    }

}
