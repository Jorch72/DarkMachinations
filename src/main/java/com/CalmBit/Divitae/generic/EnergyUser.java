package com.CalmBit.Divitae.generic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyUser extends EnergyStorage {
    public EnergyUser(int capacity, int transferRate) {
        super(capacity, transferRate);
    }

    public void setEnergyStored(int newStorage)
    {
        if(newStorage > this.capacity || newStorage < 0)
            return;

        else this.energy = newStorage;
    }

    public void setEnergyCapacity(int newCapacity)
    {
        this.capacity = newCapacity;
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
