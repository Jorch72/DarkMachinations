package com.calmbit.darkmachinations.probe;


import com.calmbit.darkmachinations.network.EnergyNetworkNode;
import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IProbeDataProvider;

import java.util.List;
import java.util.UUID;

public class ProbeDataProviderCable implements IProbeDataProvider {
    public UUID identifier;
    public EnergyNetworkNode.NodeType nodeType;

    public ProbeDataProviderCable(UUID identifier, EnergyNetworkNode.NodeType nodeType) {
        this.identifier = identifier;
        this.nodeType = nodeType;
    }

    @Override
    public void provideProbeData(List<IProbeData> data) {
        data.add(new ProbeDataUUID(identifier));
        data.add(new ProbeDataEndpointType(nodeType));
    }
}
