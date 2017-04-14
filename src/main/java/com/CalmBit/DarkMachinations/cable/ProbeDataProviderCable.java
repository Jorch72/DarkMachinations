package com.CalmBit.DarkMachinations.cable;


import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IProbeDataProvider;

import java.util.List;
import java.util.UUID;

public class ProbeDataProviderCable implements IProbeDataProvider {
    public UUID identifier;

    public ProbeDataProviderCable(UUID identifier) {
        this.identifier = identifier;
    }

    @Override
    public void provideProbeData(List<IProbeData> data) {
        data.add(new ProbeDataUUID(identifier));
    }
}
