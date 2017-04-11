package com.CalmBit.Divitae.proxy;

import com.CalmBit.Divitae.Divitae;
import com.CalmBit.Divitae.GuiRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void init()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Divitae.instance, new GuiRegistry());
    }

    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
}
