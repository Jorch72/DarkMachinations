package com.CalmBit.Divitae.cable;


import com.CalmBit.Divitae.network.EnergyNetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileEntityCable extends TileEntity {
    private EnergyNetwork network;
    private NetworkedEnergyUser energyStorage;
    public TileEntityCable() {
        this.energyStorage = new NetworkedEnergyUser(network);
    }

    public EnergyNetwork getNetwork() {
        return network;
    }

    public void setNetwork(EnergyNetwork network) {
        this.network = network;
    }

    public void mergeNetworks(EnergyNetwork network) {
        if(this.network == null) {
            this.network = network;
            this.network.addMember(this.getPos());
        }

        // TODO: Lots more work on merging networks together. Hoo boy.
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == null)
            return false;
        if(capability == CapabilityEnergy.ENERGY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return (T)energyStorage;
        }
        return super.getCapability(capability, facing);
    }
}
