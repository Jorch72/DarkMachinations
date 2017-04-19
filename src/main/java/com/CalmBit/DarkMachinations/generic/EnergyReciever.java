package com.CalmBit.DarkMachinations.generic;


public class EnergyReciever extends EnergyUser {
    public EnergyReciever(int capacity, int transferRate) {
        super(capacity, transferRate);
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int actualTransfer = Math.min(maxReceive, this.maxReceive);
        int energyToTransfer = Math.min(actualTransfer, this.getMaxEnergyStored()-this.getEnergyStored());
        if(!simulate)
            this.setEnergyStored(this.getEnergyStored()+energyToTransfer);
        if(energyToTransfer != 0 && !simulate) this.markDirty();
        return energyToTransfer;
    }
}
