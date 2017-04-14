package com.CalmBit.DarkMachinations.network;


import com.CalmBit.DarkMachinations.NetworkRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

public class EnergyNetwork {
    private ArrayList<BlockPos> members;
    private ArrayList<EnergyNetworkNode> nodes;
    private ArrayList<EnergyNetworkEdge> edges;
    private UUID identifier;

    public EnergyNetwork(World worldIn) {
        this.members = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.identifier = UUID.randomUUID();
        NetworkRegistry.registerNetwork(worldIn, this);
    }

    public void addNode(BlockPos node, EnergyNetworkNode.NodeType nodeType) {
        EnergyNetworkNode createdNode = new EnergyNetworkNode(this, node, nodeType);
        if(!nodes.contains(createdNode))
            this.nodes.add(createdNode);
        for(EnergyNetworkNode searchedNode : nodes) {
            if(searchedNode.equals(createdNode))
                continue;
            // TODO: Search and link
        }
    }

    public ArrayList<BlockPos> getMembersList() { return members;}

    public ArrayList<EnergyNetworkNode> getNodesList() { return nodes; }

    public void addMember(BlockPos member) {
        this.members.add(member);
    }

    public boolean hasMember(BlockPos member) {
        return members.contains(member);
    }

    public boolean sameNetwork(EnergyNetwork network) {
        return network.identifier == this.identifier;
    }

    public void mergeNetworks(EnergyNetwork other) {
    }

    public UUID getIdentifier() {
        return identifier;
    }
}
