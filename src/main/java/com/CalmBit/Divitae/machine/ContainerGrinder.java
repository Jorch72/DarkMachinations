package com.CalmBit.Divitae.machine;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGrinder extends ContainerBase<TileEntityGrinder> {

    public static final int GRINDER_SUPPLY_SLOT= 0;
    public static final int GRINDER_PRODUCT_SLOT=1;

    private int lastEnergyCount;
    private int lastTimer;
    private int lastTimerMax;

    public ContainerGrinder(TileEntityGrinder grinder, IInventory playerInventory)
    {
        super(grinder, playerInventory);

        // First Slot - 80,11
        // Product Slot - 80, 60
        // Progess - 82,29 size 12x15
    }

    @Override
    void addCustomSlots() {
        this.addSlotToContainer(new SlotGrinderSupply(tileEntity.itemStackHandler, GRINDER_SUPPLY_SLOT, 80, 11));
        this.addSlotToContainer(new SlotItemHandlerOutput(tileEntity.itemStackHandler, GRINDER_PRODUCT_SLOT, 80, 60));
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileEntity.setField(id, data);
    }


    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendProgressBarUpdate(this, TileEntityGrinder.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
        listener.sendProgressBarUpdate(this, TileEntityGrinder.FIELD_ITEM_PROCESSING_TIME, this.tileEntity.itemProcessingTimer);
        listener.sendProgressBarUpdate(this, TileEntityGrinder.FIELD_ITEM_PROCESSING_MAX, this.tileEntity.itemProcessingMaximum);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener listener = (IContainerListener) this.listeners.get(i);
            if(this.lastEnergyCount != this.tileEntity.getField(TileEntityGrinder.FIELD_ENERGY_COUNT))
            {
                listener.sendProgressBarUpdate(this, TileEntityGrinder.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
            }
            if(this.lastTimer != this.tileEntity.getField(TileEntityGrinder.FIELD_ITEM_PROCESSING_TIME))
            {
                listener.sendProgressBarUpdate(this, TileEntityGrinder.FIELD_ITEM_PROCESSING_TIME, this.tileEntity.itemProcessingTimer);
            }
            if(this.lastTimerMax != this.tileEntity.getField(TileEntityGrinder.FIELD_ITEM_PROCESSING_MAX))
            {
                listener.sendProgressBarUpdate(this, TileEntityGrinder.FIELD_ITEM_PROCESSING_MAX, this.tileEntity.itemProcessingMaximum);
            }

        }
        this.lastEnergyCount = this.tileEntity.energyStorage.getEnergyStored();
        this.lastTimer = this.tileEntity.itemProcessingTimer;
        this.lastTimerMax = this.tileEntity.itemProcessingMaximum;
    }

}
