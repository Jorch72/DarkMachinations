package com.calmbit.darkmachinations.generic;

import com.calmbit.darkmachinations.BlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.time.LocalDateTime;
import java.time.Month;

public class DarkMachinationsCreativeTab extends CreativeTabs {

    public DarkMachinationsCreativeTab(String name)
    {
        super(name + ((LocalDateTime.now().getMonth() == Month.APRIL && LocalDateTime.now().getDayOfMonth() == 1) ? "_af" : ""));
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(BlockRegistry.machine_compressor);
    }
}
