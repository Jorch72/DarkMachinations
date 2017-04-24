package com.calmbit.darkmachinations.machine;


import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IUnit;
import com.elytradev.probe.api.UnitDictionary;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public class ProbeDataEnergy implements IProbeData {

    double minEnergy, currEnergy, maxEnergy;

    public ProbeDataEnergy(double curr, double max)
    {
        minEnergy = 0;
        currEnergy = curr;
        maxEnergy = max;
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
        return new TextComponentString("Energy: ");
    }

    @Override
    public double getBarMinimum() {
        return minEnergy;
    }

    @Override
    public double getBarCurrent() {
        return currEnergy;
    }

    @Override
    public double getBarMaximum() {
        return maxEnergy;
    }

    @Nonnull
    @Override
    public IUnit getBarUnit() {
        return UnitDictionary.FORGE_ENERGY;
    }

    @Nonnull
    @Override
    public ImmutableList<ItemStack> getInventory() {
        return null;
    }
}
