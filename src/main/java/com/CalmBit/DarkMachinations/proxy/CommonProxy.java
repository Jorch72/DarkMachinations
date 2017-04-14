package com.CalmBit.DarkMachinations.proxy;

import com.CalmBit.DarkMachinations.DarkMachinations;
import com.CalmBit.DarkMachinations.GuiRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void init()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(DarkMachinations.instance, new GuiRegistry());
    }

    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
}
