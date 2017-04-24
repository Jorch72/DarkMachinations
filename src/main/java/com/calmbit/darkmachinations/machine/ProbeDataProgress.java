package com.calmbit.darkmachinations.machine;

import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IUnit;
import com.elytradev.probe.api.UnitDictionary;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;


public class ProbeDataProgress implements IProbeData {
    private double currProgress, maxProgress;
    private boolean active;
    public ProbeDataProgress(double currProgress, double maxProgress, boolean active) {
        this.currProgress = currProgress;
        this.maxProgress = maxProgress;
        this.active = active;
    }

    @Override
    public boolean hasLabel() {
        return true;
    }

    @Override
    public boolean hasBar() {
        return true;
    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Nonnull
    @Override
    public ITextComponent getLabel() {
        return new TextComponentString("Progress:");
    }

    @Override
    public double getBarMinimum() {
        return 0;
    }

    @Override
    public double getBarCurrent() {
        return active ? 100-currProgress : 0;
    }

    @Override
    public double getBarMaximum() {
        return maxProgress;
    }

    @Nonnull
    @Override
    public IUnit getBarUnit() {
        return UnitDictionary.PERCENT;
    }

    @Nonnull
    @Override
    public ImmutableList<ItemStack> getInventory() {
        return null;
    }
}
