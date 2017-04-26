package com.calmbit.darkmachinations.machine;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPump extends ContainerBase<TileEntityPump> {

    public static final int PUMP_BLOCK_SLOT= 0;

    private int lastEnergyCount;
    private int lastTimer;
    private int lastTimerMax;
    private int lastFluidLevel;

    public ContainerPump(TileEntityPump pump, IInventory playerInventory)
    {
        super(pump, playerInventory);
    }

    @Override
    void addCustomSlots() {
        this.addSlotToContainer(new SlotFurnaceFuelItemStackHandler(tileEntity.itemStackHandler, PUMP_BLOCK_SLOT, 86, 31));
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileEntity.setField(id, data);
    }


    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
        listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_PUMP_TIMER, this.tileEntity.pumpTimer);
        listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_PUMP_TIMER_MAX, this.tileEntity.pumpTimerMaximum);
        listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_FLUID_LEVEL, this.tileEntity.fluidTank.getFluidAmount());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener listener = (IContainerListener) this.listeners.get(i);
            if(this.lastEnergyCount != this.tileEntity.getField(TileEntityPump.FIELD_ENERGY_COUNT)) {
                listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_ENERGY_COUNT, this.tileEntity.energyStorage.getEnergyStored());
            }
            if(this.lastTimer != this.tileEntity.getField(TileEntityPump.FIELD_PUMP_TIMER)) {
                listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_PUMP_TIMER, this.tileEntity.pumpTimer);
            }
            if(this.lastTimerMax != this.tileEntity.getField(TileEntityPump.FIELD_PUMP_TIMER_MAX)) {
                listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_PUMP_TIMER_MAX, this.tileEntity.pumpTimerMaximum);
            }
            if(this.lastFluidLevel != this.tileEntity.getField(TileEntityPump.FIELD_FLUID_LEVEL)) {
                listener.sendProgressBarUpdate(this, TileEntityPump.FIELD_FLUID_LEVEL, this.tileEntity.fluidTank.getFluidAmount());
            }

        }
        this.lastEnergyCount = this.tileEntity.energyStorage.getEnergyStored();
        this.lastTimer = this.tileEntity.pumpTimer;
        this.lastTimerMax = this.tileEntity.pumpTimerMaximum;
        this.lastFluidLevel = this.tileEntity.fluidTank.getFluidAmount();
    }
}
