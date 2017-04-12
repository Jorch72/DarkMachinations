package com.CalmBit.Divitae.network;


public interface INetworkEdge {
    boolean equals(INetworkEdge other);
    boolean equalsProspective(INetworkNode first, INetworkNode last);
    void findShortestRoute();
}
