package com.calmbit.darkmachinations.fluid;

import com.elytradev.concrete.inventory.ConcreteFluidTank;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class FluidBuffer extends ConcreteFluidTank{
    private ArrayList<Runnable> listeners = new ArrayList<>();

    public FluidBuffer(int capacity) {
        super(capacity);
    }
}
