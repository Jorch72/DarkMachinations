package com.calmbit.darkmachinations.energy;


import com.calmbit.darkmachinations.network.EnergyNetwork;
import net.minecraftforge.energy.IEnergyStorage;

public class NetworkedEnergyUser implements IEnergyStorage {

    private EnergyNetwork network;

    public NetworkedEnergyUser(EnergyNetwork network) {
        this.network = network;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        // TODO: Extract from suppliers...
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        // TODO: Check every supplier in the network for energy?
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
