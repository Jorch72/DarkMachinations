package com.elytradev.darkmachinations.energy;


public class EnergyProvider extends EnergyUser {
	public EnergyProvider(int capacity, int transferRate) {
		super(capacity, transferRate);
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
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
