package com.calmbit.darkmachinations.probe;

import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IUnit;
import com.elytradev.probe.api.UnitDictionary;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;

public class ProbeDataFluid implements IProbeData {
    private FluidTank fluidTank;

    public ProbeDataFluid(FluidTank fluidTank) {
        this.fluidTank = fluidTank;
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
        return new TextComponentString("Fluid:");
    }

    @Override
    public double getBarMinimum() {
        return 0;
    }

    @Override
    public double getBarCurrent() {
        if(fluidTank != null)
            return fluidTank.getFluidAmount();
        return 0;
    }

    @Override
    public double getBarMaximum() {
        if(fluidTank != null)
            return fluidTank.getCapacity();
        return 0;
    }

    @Nonnull
    @Override
    public IUnit getBarUnit() {
        return UnitDictionary.BUCKETS_ANY;
    }

    @Nonnull
    @Override
    public ImmutableList<ItemStack> getInventory() {
        return null;
    }
}
