package com.CalmBit.DarkMachinations.cable;

import com.CalmBit.DarkMachinations.network.EnergyNetworkNode;
import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IUnit;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;;

import javax.annotation.Nonnull;

public class ProbeDataEndpointType implements IProbeData {

    private EnergyNetworkNode.NodeType nodeType;

    public ProbeDataEndpointType(EnergyNetworkNode.NodeType nodeType) {
        this.nodeType = nodeType;
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
        if(nodeType != null)
            return new TextComponentString(nodeType.toString());
        else
            return new TextComponentString("No Endpoint Type");
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
