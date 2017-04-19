package com.CalmBit.DarkMachinations.generic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class EnergyUser extends EnergyStorage {
    private ArrayList<Runnable> listeners = new ArrayList<>();

    public EnergyUser(int capacity, int transferRate) {
        super(capacity, transferRate);
    }

    public void setEnergyStored(int newStorage)
    {
        if(newStorage > this.capacity || newStorage < 0)
            return;

        else this.energy = newStorage;

        this.markDirty();
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

    public void setEnergyCapacity(int newCapacity)
    {
        this.capacity = newCapacity;
        markDirty();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(!simulate && this.canReceive()) markDirty();
        return super.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if(!simulate && this.canExtract()) markDirty();
        return super.extractEnergy(maxExtract, simulate);
    }


    public void readFromNBT(NBTTagCompound compound)
    {
        this.energy = compound.getInteger("power_level");
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("power_level", this.energy);
    }
}
