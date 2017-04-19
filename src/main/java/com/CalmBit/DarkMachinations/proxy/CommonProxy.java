package com.calmbit.darkmachinations.proxy;

import com.calmbit.darkmachinations.DarkMachinations;
import com.calmbit.darkmachinations.GuiRegistry;
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
