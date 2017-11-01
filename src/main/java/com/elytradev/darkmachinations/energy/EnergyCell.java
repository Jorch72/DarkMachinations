package com.elytradev.darkmachinations.energy;

public class EnergyCell extends EnergyUser {
	public EnergyCell(int capacity, int transferRate) {
		super(capacity, transferRate);
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	@Override
	public boolean canExtract() {
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

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int actualTransfer = Math.min(maxExtract, this.maxExtract);
		int energyToTransfer = Math.min(actualTransfer, this.getEnergyStored());
		if(!simulate)
			this.setEnergyStored(this.getEnergyStored()-energyToTransfer);
		if(energyToTransfer != 0 && !simulate) this.markDirty();
		return energyToTransfer;
	}
}
