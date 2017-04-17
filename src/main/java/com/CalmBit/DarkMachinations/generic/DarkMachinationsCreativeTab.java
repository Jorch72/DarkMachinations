package com.CalmBit.DarkMachinations.generic;

import com.CalmBit.DarkMachinations.BlockRegistry;
import com.CalmBit.DarkMachinations.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDateTime;

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
