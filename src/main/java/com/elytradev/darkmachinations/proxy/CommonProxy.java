package com.elytradev.darkmachinations.proxy;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.registry.GuiRegistry;
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

	public void registerItemRenderer(Item item, int meta, String id, String variant)
	{

	}
}
