package com.CalmBit.DarkMachinations.network;


import net.minecraft.util.math.BlockPos;

public class EnergyNetworkNode {

    public enum NodeType {
        ROUTER,
        ENDPOINT,
        SENDER,
        RECIEVER,
        SENDRECV
    }
    public BlockPos position;
    public NodeType nodeType;
    public EnergyNetwork network;

    public EnergyNetworkNode(EnergyNetwork network, BlockPos position, NodeType nodeType) {
        this.network = network;
        this.position = position;
        this.nodeType = nodeType;
    }

    public boolean equals(EnergyNetworkNode other) {
        return this.position == other.position && this.nodeType == other.nodeType;
    }
}
