package com.calmbit.darkmachinations.machine;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGenerator extends ContainerBase<TileEntityGenerator> {

    public static final int GENERATOR_SUPPLY_SLOT= 0;

    private int lastEnergyCount;
    private int lastTimer;
    private int lastTimerMax;


    public ContainerGenerator(TileEntityGenerator generator, IInventory playerInventory)
    {
        super(generator, playerInventory);
    }

    @Override
    void addCustomSlots() {
        this.addSlotToContainer(new SlotFurnaceFuelItemStackHandler(tileEntity.itemStackHandler, GENERATOR_SUPPLY_SLOT, 86, 31));
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileEntity.setField(id, data);
    }


    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendProgressBarUpdate(this, TileEntityGenerator.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
        listener.sendProgressBarUpdate(this, TileEntityGenerator.FIELD_ITEM_PROCESSING_TIME, this.tileEntity.itemProcessingTimer);
        listener.sendProgressBarUpdate(this, TileEntityGenerator.FIELD_ITEM_PROCESSING_MAX, this.tileEntity.itemProcessingMaximum);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener listener = (IContainerListener) this.listeners.get(i);
            if(this.lastEnergyCount != this.tileEntity.getField(TileEntityGenerator.FIELD_ENERGY_COUNT))
            {
                listener.sendProgressBarUpdate(this, TileEntityGenerator.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
            }
            if(this.lastTimer != this.tileEntity.getField(TileEntityGenerator.FIELD_ITEM_PROCESSING_TIME))
            {
                listener.sendProgressBarUpdate(this, TileEntityGenerator.FIELD_ITEM_PROCESSING_TIME, this.tileEntity.itemProcessingTimer);
            }
            if(this.lastTimerMax != this.tileEntity.getField(TileEntityGenerator.FIELD_ITEM_PROCESSING_MAX))
            {
                listener.sendProgressBarUpdate(this, TileEntityGenerator.FIELD_ITEM_PROCESSING_MAX, this.tileEntity.itemProcessingMaximum);
            }

        }
        this.lastEnergyCount = this.tileEntity.energyStorage.getEnergyStored();
        this.lastTimer = this.tileEntity.itemProcessingTimer;
        this.lastTimerMax = this.tileEntity.itemProcessingMaximum;
    }
}
