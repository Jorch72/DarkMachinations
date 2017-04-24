package com.calmbit.darkmachinations.machine;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCompressor extends ContainerBase<TileEntityCompressor> {

    public static final int COMPRESSOR_SUPPLY_SLOT= 0;
    public static final int COMPRESSOR_PRODUCT_SLOT=1;

    private int lastEnergyCount;
    private int lastTimer;
    private int lastTimerMax;


    public ContainerCompressor(TileEntityCompressor compressor, IInventory playerInventory)
    {
        super(compressor, playerInventory);
    }

    @Override
    void addCustomSlots() {
        this.addSlotToContainer(new SlotCompressorSupply(tileEntity.itemStackHandler, COMPRESSOR_SUPPLY_SLOT, 80, 32));
        this.addSlotToContainer(new SlotItemHandlerOutput(tileEntity.itemStackHandler, COMPRESSOR_PRODUCT_SLOT, 131, 33));
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileEntity.setField(id, data);
    }


    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendProgressBarUpdate(this, TileEntityCompressor.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
        listener.sendProgressBarUpdate(this, TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME, this.tileEntity.itemProcessingTimer);
        listener.sendProgressBarUpdate(this, TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX, this.tileEntity.itemProcessingMaximum);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener listener = (IContainerListener) this.listeners.get(i);
            if(this.lastEnergyCount != this.tileEntity.getField(TileEntityCompressor.FIELD_ENERGY_COUNT))
            {
                listener.sendProgressBarUpdate(this, TileEntityCompressor.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
            }
            if(this.lastTimer != this.tileEntity.getField(TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME))
            {
                listener.sendProgressBarUpdate(this, TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME, this.tileEntity.itemProcessingTimer);
            }
            if(this.lastTimerMax != this.tileEntity.getField(TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX))
            {
                listener.sendProgressBarUpdate(this, TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX, this.tileEntity.itemProcessingMaximum);
            }

        }
        this.lastEnergyCount = this.tileEntity.energyStorage.getEnergyStored();
        this.lastTimer = this.tileEntity.itemProcessingTimer;
        this.lastTimerMax = this.tileEntity.itemProcessingMaximum;
    }
}
