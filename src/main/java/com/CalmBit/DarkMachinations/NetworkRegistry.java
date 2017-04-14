package com.CalmBit.DarkMachinations;

import com.CalmBit.DarkMachinations.cable.BlockCable;
import com.CalmBit.DarkMachinations.cable.TileEntityCableNode;
import com.CalmBit.DarkMachinations.network.EnergyNetwork;
import com.CalmBit.DarkMachinations.network.EnergyNetworkNode;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.UUID;

public class NetworkRegistry {

    public static ArrayList<EnergyNetwork> networkList = new ArrayList<>();

    public static void registerNetwork(World worldIn, EnergyNetwork network) {
        if(!worldIn.isRemote)
            networkList.add(network);
    }

    public static void removeNetwork(World worldIn, EnergyNetwork network) {
        if(!worldIn.isRemote)
            networkList.remove(network);
    }

    public static EnergyNetwork getNetwork(UUID identifier) {
        for(EnergyNetwork network : networkList) {
            if(network.getIdentifier() == identifier)
                return network;
        }
        return null;
    }

    public static UUID getNetworkIdentityForBlock(BlockPos pos) {
        for(EnergyNetwork network : networkList) {
            if(network.hasMember(pos)) {
                return network.getIdentifier();
            }
        }
        return null;
    }

    public static void mergeNetworks(World worldIn, EnergyNetwork one, EnergyNetwork two)
    {
        if(!worldIn.isRemote) {
            if (one.getIdentifier() == two.getIdentifier()) {
                FMLLog.getLogger().error("For some reason, this network (" + one.getIdentifier() + ") just tried to merge to itself...");
                return;
            }
            try {
                FMLLog.getLogger().info("Network merge incoming! (" + one.getIdentifier() + "," + two.getIdentifier() + ")");
                for (BlockPos member : two.getMembersList()) {
                    one.addMember(member);
                }

                for (EnergyNetworkNode node : two.getNodesList()) {
                    one.addNode(node.position, node.nodeType);
                    switch (node.nodeType) {
                        case ENDPOINT:
                            TileEntityCableNode cable = (TileEntityCableNode) worldIn.getTileEntity(node.position);
                            if (cable == null)
                                FMLLog.getLogger().error("What the fuck? Why is an ENDPOINT node not carrying a TE? Position: " + node.position);
                            else
                                cable.setNetwork(one);
                            break;
                    }
                }
                NetworkRegistry.removeNetwork(worldIn, two);
                FMLLog.getLogger().info("Network merge complete.");
            } catch (ConcurrentModificationException e) {
                FMLLog.getLogger().fatal("ConcurrentModificationException thrown merging networks " + one.getIdentifier() + ", " + two.getIdentifier());
            }
        }
    }

    public static void networkRediscovery(World worldIn, EnergyNetwork network, BlockPos pos) {
        if(network.hasMember(pos))
            return;
        if(!(worldIn.getBlockState(pos).getBlock() instanceof BlockCable))
            return;

        LinkedList<BlockPos> toBeChecked = new LinkedList<>();
        toBeChecked.add(pos);

        network.addMember(pos);

        while(!toBeChecked.isEmpty()) {
            BlockPos checking = toBeChecked.removeFirst();
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos extra = checking.add(facing.getDirectionVec());
                if(network.hasMember(extra))
                    continue;
                if(worldIn.getBlockState(extra).getBlock() instanceof BlockCable)
                {
                    if(worldIn.getTileEntity(extra) != null)
                        mergeNetworks(worldIn, network, ((TileEntityCableNode) worldIn.getTileEntity(extra)).getNetwork());
                    else {
                        network.addMember(extra);
                        toBeChecked.add(extra);
                    }
                }
            }
        }
    }
}
