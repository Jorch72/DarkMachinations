package com.CalmBit.Divitae.network;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class Network {
    protected ArrayList<BlockPos> memberBlocks;
    protected List<NetworkEdge> edges;

    protected void addEdge(BlockPos first, BlockPos last) {
        this.edges.add(new NetworkEdge(this, first, last));
    }

    public void addMember(BlockPos member) {
        if(!memberBlocks.contains(member)) {
            this.memberBlocks.add(member);
        }
    }
}
