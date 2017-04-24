package com.calmbit.darkmachinations.network;


import net.minecraft.util.math.BlockPos;

public class EnergyNetworkNode {

    public enum NodeType {
        BLANK((byte)0),
        ROUTER((byte)1),
        SENDER((byte)2),
        RECEIVER((byte)3),
        SENDRECV((byte)4);

        public byte value;
        NodeType(byte value) {
            this.value = value;
        }

        public static NodeType getNodeFromByte(byte nodeType) {
            return NodeType.values()[nodeType];
        }
    }
    public BlockPos position;
    public NodeType nodeType;
    public EnergyNetwork network;

    public EnergyNetworkNode(EnergyNetwork network, BlockPos position, NodeType nodeType) {
        this.network = network;
        this.position = position;
        this.nodeType = nodeType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EnergyNetworkNode)
            return this.equals((EnergyNetworkNode)obj);
        return false;
    }

    public boolean equals(EnergyNetworkNode other) {
        return this.position.equals(other.position) && this.nodeType == other.nodeType;
    }
}
