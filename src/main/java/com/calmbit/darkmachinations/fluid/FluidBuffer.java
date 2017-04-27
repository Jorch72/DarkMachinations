package com.calmbit.darkmachinations.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class FluidBuffer extends FluidTank{
    private ArrayList<Runnable> listeners = new ArrayList<>();

    public FluidBuffer(int capacity) {
        super(capacity);
    }

    /* Borrowed with great thanks and love to Falkreon and Thermionics */

    protected void markDirty() {
        for(Runnable r : listeners) {
            r.run();
        }
    }

    public void listen(@Nonnull Runnable r) {
        listeners.add(r);
    }

    /* End Borrow - Thanks Falkreon <3 */

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        markDirty();
        return super.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        markDirty();
        return super.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        markDirty();
        return super.drain(maxDrain, doDrain);
    }
}
