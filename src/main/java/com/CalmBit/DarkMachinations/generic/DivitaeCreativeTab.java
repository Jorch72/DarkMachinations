package com.CalmBit.DarkMachinations.generic;

import com.CalmBit.DarkMachinations.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class DivitaeCreativeTab extends CreativeTabs {

    public DivitaeCreativeTab(String name)
    {
        super(name);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemRegistry.ingot_copper);
    }
}
