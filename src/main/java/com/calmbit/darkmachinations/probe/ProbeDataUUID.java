package com.calmbit.darkmachinations.probe;


import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IUnit;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ProbeDataUUID implements IProbeData{

    UUID identifier;
    public ProbeDataUUID(UUID identifier) {
        this.identifier = identifier;
    }
    @Override
    public boolean hasLabel() {
        return true;
    }

    @Override
    public boolean hasBar() {
        return false;
    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Nonnull
    @Override
    public ITextComponent getLabel() {
        if(identifier == null) {
            return new TextComponentString("No network found.");
        }
        return new TextComponentString(identifier.toString());
    }

    @Override
    public double getBarMinimum() {
        return 0;
    }

    @Override
    public double getBarCurrent() {
        return 0;
    }

    @Override
    public double getBarMaximum() {
        return 0;
    }

    @Nonnull
    @Override
    public IUnit getBarUnit() {
        return null;
    }

    @Nonnull
    @Override
    public ImmutableList<ItemStack> getInventory() {
        return null;
    }
}
