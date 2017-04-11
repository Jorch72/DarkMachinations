package com.CalmBit.Divitae.generic;

import com.CalmBit.Divitae.ItemRegistry;
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
