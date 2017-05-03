package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityBase;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public abstract class ContainerBase<TE extends TileEntityBase> extends ConcreteContainer {

    public TE tileEntity;

    public ContainerBase(TE tileEntity, IInventory playerInventory, IInventory containerInventory) {
        super(playerInventory, containerInventory);
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.isUseableByPlayer(playerIn);
    }

}
