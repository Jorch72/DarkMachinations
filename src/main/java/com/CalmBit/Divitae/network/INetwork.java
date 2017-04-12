package com.CalmBit.Divitae.network;

import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public interface INetwork {

    boolean addEdge(INetworkNode first, INetworkNode last);

    void addMember(BlockPos member);

    INetworkEdge getEdge();

    UUID getUUID();
}
