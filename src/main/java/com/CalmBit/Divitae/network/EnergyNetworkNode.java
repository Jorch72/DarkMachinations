package com.CalmBit.Divitae.network;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EnergyNetworkNode implements INetworkNode {

    private BlockPos position;
    private EnumFacing face;

    public EnergyNetworkNode(BlockPos position, EnumFacing face) {
        this.position = position;
        this.face = face;
    }

    public boolean equals(INetworkNode other) {
        if(other instanceof EnergyNetworkNode) {
            EnergyNetworkNode otherNode = (EnergyNetworkNode)(other);
            return otherNode.position == this.position && otherNode.face == this.face;
        }
        return false;
    }

    public BlockPos getPosition() {
        return position;
    }

    public EnumFacing getFace() {
        return face;
    }
}
