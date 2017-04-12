package com.CalmBit.Divitae.network;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnergyNetwork implements INetwork {

    public List<Tuple<BlockPos, EnumFacing>> supplierList;
    public List<Tuple<BlockPos, EnumFacing>> receiverList;

    ArrayList<BlockPos> memberBlocks;
    List<EnergyNetworkEdge> edges;

    private int maximumEnergy;

    private UUID identifier;

    public EnergyNetwork() {
        identifier = UUID.randomUUID();
    }

    @Override
    public boolean addEdge(INetworkNode first, INetworkNode last) {
        if(first instanceof EnergyNetworkNode && last instanceof EnergyNetworkNode) {
            for (EnergyNetworkEdge edge : edges) {
                if (edge.equalsProspective(first, last))
                    return false;
            }
            edges.add(new EnergyNetworkEdge(this, (EnergyNetworkNode) first, (EnergyNetworkNode) last));
            return true;
        }
        return false;
    }

    public void addMember(BlockPos member) {
        if(!memberBlocks.contains(member)) {
            this.memberBlocks.add(member);
        }
    }

    @Override
    public INetworkEdge getEdge() {
        return null;
    }

    @Override
    public UUID getUUID() {
        return identifier;
    }

    @Override
    public String toString() {
        return "EnergyNetwork:"+identifier.toString();
    }

    public void addSupplier(World worldIn, BlockPos supplierPos, EnumFacing side) {
        /*
        Tuple<BlockPos, EnumFacing> supplier = new Tuple<>(supplierPos, side);
        if(!supplierList.contains(supplier)) {
            this.supplierList.add(new Tuple<>(supplierPos, side));
            TileEntity supplierEntity = worldIn.getTileEntity(supplierPos);

            this.maximumEnergy += supplierEntity.getCapability(CapabilityEnergy.ENERGY, side).getMaxEnergyStored();

            for(BlockPos reciever : receiverList) {
                if(reciever != supplierPos)
                    this.addEdge(reciever, supplierPos);
            }
        }
        */
        // TODO: Implement adding suppliers - current thinking is broken.
    }

    public void removeSupplier(World worldIn, BlockPos supplierPos, EnumFacing side) {
        /*
        Tuple<BlockPos, EnumFacing> supplier = new Tuple<>(supplierPos, side);
        if(supplierList.contains(supplier)) {
            this.supplierList.remove(new Tuple<>(supplierPos, side));
            TileEntity supplierEntity = worldIn.getTileEntity(supplierPos);
            this.maximumEnergy -= supplierEntity.getCapability(CapabilityEnergy.ENERGY, side).getMaxEnergyStored();

            for(EnergyNetworkEdge edge : edges) {

            }
        }
        */
        // TODO: Implement removing suppliers - current thinking is broken.
    }

    public int getMaximumEnergy() {
        return maximumEnergy;
    }

    public void setMaximumEnergy(int newMaximum) {
        maximumEnergy = newMaximum;
    }

    public void addReciever(BlockPos reciever) {
        /*if(!receiverList.contains(reciever)) {
            this.receiverList.add(reciever);

            for(BlockPos supplier : supplierList) {
                if(reciever != supplier)
                    this.addEdge(reciever, supplier);
            }

        }*/
    }
}
