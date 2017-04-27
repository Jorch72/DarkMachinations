package com.calmbit.darkmachinations.probe;

import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.IUnit;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by Ethan on 2017-04-17.
 */
public class ProbeDataItemStackHandler implements IProbeData {

    private ItemStackHandler stackHandler;

    public ProbeDataItemStackHandler(ItemStackHandler stackHandler) {
        this.stackHandler = stackHandler;
    }

    @Override
    public boolean hasLabel() {
        return false;
    }

    @Override
    public boolean hasBar() {
        return false;
    }

    @Override
    public boolean hasInventory() {
        return true;
    }

    @Nonnull
    @Override
    public ITextComponent getLabel() {
        return new TextComponentString("Inventory:");
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
        ImmutableList.Builder<ItemStack> itemStacks = ImmutableList.builder();
        for(int i =0; i < stackHandler.getSlots();i++) {
            itemStacks.add(stackHandler.getStackInSlot(i));
        }
        return itemStacks.build();
    }
}
