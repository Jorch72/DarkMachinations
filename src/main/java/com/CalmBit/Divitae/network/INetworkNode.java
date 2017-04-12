package com.CalmBit.Divitae.network;


/*
 * INetworkNode has to cope with the fact that a network might have more criteria
 *  influencing what counts as a "node", like in the case of EnergyNetworkNode,
 *  where we'll need both the position of the block AND the side its facing in order to determine
 *  both the endpoint and the uniqueness of the connection in the network.
 */
public interface INetworkNode {
    boolean equals(INetworkNode other);
    INetworkNode clone();
}
