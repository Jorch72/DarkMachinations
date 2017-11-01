package com.elytradev.darkmachinations.energy;

public class EnergyCellCreative extends EnergyCell {
	public EnergyCellCreative(int transferRate) {
		super(-1, transferRate);
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
	public int getEnergyStored() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int actualTransfer = Math.min(maxExtract, this.maxExtract);
		if(actualTransfer != 0 && !simulate) this.markDirty();
		return actualTransfer;
	}
}
