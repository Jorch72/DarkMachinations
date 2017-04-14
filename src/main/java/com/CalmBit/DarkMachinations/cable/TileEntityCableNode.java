package com.CalmBit.DarkMachinations.cable;


import com.CalmBit.DarkMachinations.DarkMachinations;
import com.CalmBit.DarkMachinations.network.EnergyNetwork;
import com.CalmBit.DarkMachinations.network.EnergyNetworkNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileEntityCableNode extends TileEntity {

    private EnergyNetwork network;
    private NetworkedEnergyUser energyStorage;
    private ProbeDataProviderCable cableProvider;

    public TileEntityCableNode(EnergyNetwork network) {
        this.network = network;
        this.energyStorage = new NetworkedEnergyUser(network);
        this.cableProvider = new ProbeDataProviderCable(this.getNetwork().getIdentifier());
    }

    @Override
    public void onLoad() {
        network.addNode(this.getPos(), EnergyNetworkNode.NodeType.ENDPOINT);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == null)
            return false;
        if(capability == CapabilityEnergy.ENERGY)
            return true;
        if(capability == DarkMachinations.PROBE_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return (T)energyStorage;
        }
        if(capability == DarkMachinations.PROBE_CAPABILITY) {
            return (T)cableProvider;
        }
        return super.getCapability(capability, facing);
    }



    public void mergeNetworks(EnergyNetwork other) {

    }

    public EnergyNetwork getNetwork() {
        return network;
    }

    public void setNetwork(EnergyNetwork network) {
        this.network = network;
        this.cableProvider.identifier = network.getIdentifier();
    }
}
