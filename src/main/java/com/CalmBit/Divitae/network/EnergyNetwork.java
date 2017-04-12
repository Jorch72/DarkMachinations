package com.CalmBit.Divitae.network;


import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.apache.commons.lang3.math.IEEE754rUtils;

import java.util.List;

public class EnergyNetwork extends Network {

    public List<Tuple<BlockPos, EnumFacing>> supplierList;
    public List<Tuple<BlockPos, EnumFacing>> receiverList;

    private int maximumEnergy;

    public void addSupplier(World worldIn, BlockPos supplierPos, EnumFacing side) {
        Tuple<BlockPos, EnumFacing> supplier = new Tuple<>(supplierPos, side);
        if(!supplierList.contains(supplier)) {
            this.supplierList.add(new Tuple<>(supplierPos, side));
            TileEntity supplierEntity = worldIn.getTileEntity(supplierPos);

            this.maximumEnergy += supplierEntity.getCapability(CapabilityEnergy.ENERGY, side).getMaxEnergyStored();

            /*for(BlockPos reciever : receiverList) {
                if(reciever != supplierPos)
                    this.addEdge(reciever, supplierPos);
            }*/
        }
    }

    public void removeSupplier(World worldIn, BlockPos supplierPos, EnumFacing side) {
        Tuple<BlockPos, EnumFacing> supplier = new Tuple<>(supplierPos, side);
        if(supplierList.contains(supplier)) {
            this.supplierList.remove(new Tuple<>(supplierPos, side));
            TileEntity supplierEntity = worldIn.getTileEntity(supplierPos);
            this.maximumEnergy -= supplierEntity.getCapability(CapabilityEnergy.ENERGY, side).getMaxEnergyStored();

            for(NetworkEdge edge : edges) {
                    /*
                     *  TODO: Make an intermediate class, NetworkNode, and Energy-specific versions of both
                     *  NetworkNode and NetworkEdge, EnergyNetworkNode & EnergyNetworkEdge.
                     *
                     *  NetworkNode has to cope with the fact that a network might have more criteria
                     *  influencing what counts as a "node", like in the case of EnergyNetworkNode,
                     *  where we'll need both the position of the block AND the side its facing in order to determine
                     *  both the endpoint and the uniqueness of the connection in the network.
                     */
            }
        }
    }

    public int getMaximumEnergy() {
        return this.maximumEnergy;
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
