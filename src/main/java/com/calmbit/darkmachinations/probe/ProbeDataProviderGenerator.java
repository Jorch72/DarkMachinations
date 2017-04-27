package com.calmbit.darkmachinations.probe;

import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IProbeDataProvider;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;


public class ProbeDataProviderGenerator implements IProbeDataProvider {

    private double currEnergy, maxEnergy;

    private double currTicks, maxTicks;
    private boolean active;

    private ItemStackHandler stackHandler;

    @Override
    public void provideProbeData(List<IProbeData> data) {
        data.add(new ProbeDataEnergy(currEnergy, maxEnergy));
        data.add(new ProbeDataFuel(currTicks, maxTicks, active));
        data.add(new ProbeDataItemStackHandler(stackHandler));
    }

    public void updateProbeEnergyData(double currEnergy, double maxEnergy) {
        this.currEnergy = currEnergy;
        this.maxEnergy = maxEnergy;
    }

    public void updateProbeFuelData(double currTicks, double maxTicks, boolean active)
    {
        this.currTicks = currTicks;
        this.maxTicks = maxTicks;
        this.active = active;
    }

    public void setItemStackHandler(ItemStackHandler stackHandler) {
        this.stackHandler = stackHandler;
    }

    public double getCurrentEnergy() {
        return this.currEnergy;
    }

    public void setCurrentEnergy(double curr) {
        this.currEnergy = curr;
    }

    public double getMaximumEnergy() {
        return this.maxEnergy;
    }

    public void setMaximumEnergy(double max) {
        this.maxEnergy = max;
    }

    public double getCurrentTicks() {
        return currTicks;
    }

    public void setCurrentticks(double curr) {
        this.currTicks = curr;
    }


    public double getMaximumTicks() {
        return maxTicks;
    }

    public void setMaximumTicks(double max) {
        this.maxTicks = max;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
