package com.CalmBit.Divitae.network;


import net.minecraft.util.math.BlockPos;

public interface INetworkEdge {
    boolean equals(INetworkEdge other);
    boolean equalsProspective(INetworkNode first, INetworkNode last);
    void findShortestRoute();
    boolean containsBlock(BlockPos block);
}
