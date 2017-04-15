package com.CalmBit.DarkMachinations.generic;

import com.CalmBit.DarkMachinations.BlockRegistry;
import com.CalmBit.DarkMachinations.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class DarkMachinationsCreativeTab extends CreativeTabs {

    public DarkMachinationsCreativeTab(String name)
    {
        super(name);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(BlockRegistry.machine_compressor);
    }
}
