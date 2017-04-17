package com.CalmBit.DarkMachinations.cable;


import com.CalmBit.DarkMachinations.DarkMachinations;
import com.CalmBit.DarkMachinations.network.EnergyNetwork;
import com.CalmBit.DarkMachinations.network.EnergyNetworkNode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileEntityCableNode extends TileEntity {

    private EnergyNetwork network;
    private NetworkedEnergyUser energyStorage;
    private ProbeDataProviderCable cableProvider;
    private EnergyNetworkNode.NodeType nodeType;
    private BlockCableEndpoint.BlockCableType cableType = BlockCableEndpoint.BlockCableType.COPPER_INSULATED;

    public void addNodeToNetwork(EnergyNetworkNode.NodeType nodeType) {
        this.nodeType = nodeType;
        this.cableProvider.nodeType = nodeType;
        network.addNode(world, this.getPos(), nodeType);
    }

    public void assignCableType(BlockCableEndpoint.BlockCableType type) {
        this.cableType = type;
        this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
        this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
        this.world.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
        this.world.scheduleBlockUpdate(this.pos, this.getBlockType(), 0, 0);
        this.markDirty();
    }

    public BlockCableEndpoint.BlockCableType getCableType() {
        return this.cableType;
    }

    public EnergyNetworkNode.NodeType getNodeType() {
        return this.nodeType;
    }

    @Override
    public void onLoad() {
        this.nodeType = this.nodeType == null ? EnergyNetworkNode.NodeType.BLANK : this.nodeType;
        this.network = new EnergyNetwork(world);
        this.energyStorage = new NetworkedEnergyUser(network);
        this.cableProvider = new ProbeDataProviderCable(this.getNetwork().getIdentifier(), nodeType);
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

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.nodeType = EnergyNetworkNode.NodeType.getNodeFromByte(compound.getByte("nodeType"));
        this.assignCableType(BlockCableEndpoint.BlockCableType.valueOf(compound.getString("cableType").toUpperCase()));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setByte("nodeType", this.nodeType.value);
        compound.setString("cableType", this.getCableType().getName());
        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound update = new NBTTagCompound();
        this.writeToNBT(update);
        return new SPacketUpdateTileEntity(this.pos, 1, update);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
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
