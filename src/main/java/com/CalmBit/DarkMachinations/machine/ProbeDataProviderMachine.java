package com.CalmBit.DarkMachinations.machine;

import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IProbeDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;


public class ProbeDataProviderMachine implements IProbeDataProvider {

    private double currEnergy, maxEnergy;

    private double currProgress, maxProgress;
    private boolean active;
    private ItemStackHandler stackHandler;

    @Override
    public void provideProbeData(List<IProbeData> data) {
        data.add(new ProbeDataEnergy(currEnergy, maxEnergy));
        data.add(new ProbeDataProgress(currProgress, maxProgress, active));
        data.add(new ProbeDataItemStackHandler(stackHandler));
    }

    public void updateProbeEnergyData(double currEnergy, double maxEnergy) {
        this.currEnergy = currEnergy;
        this.maxEnergy = maxEnergy;
    }

    public void updateProbeProgressData(double currProgress, double maxProgress, boolean active)
    {
        this.currProgress = currProgress;
        this.maxProgress = maxProgress;
        this.active = active;
    }

    public void setStackHandler(ItemStackHandler stackHandler) {
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

    public double getCurrentProgress() {
        return currProgress;
    }

    public void setCurentrProgress(double curr) {
        this.currProgress = curr;
    }


    public double getMaximumProgress() {
        return maxProgress;
    }

    public void setMaximumProgress(double max) {
        this.maxProgress = max;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
