package com.elytradev.darkmachinations.registry;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.generic.IOreDict;
import com.elytradev.darkmachinations.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemRegistry {

	public static final ItemBase machine_corner = new ItemBase("machine_corner").setCreativeTab(DarkMachinations.divitaeTab);


	public static void registerItems(IForgeRegistry<Item> registry) {
		registerItem(registry, machine_corner);
	}

	public static void registerItemModels() {
		registerItemModel(machine_corner);
	}

	private static void registerItem(IForgeRegistry<Item> registry, ItemBase item)
	{
		registry.register(item);

		if(item instanceof IOreDict)
		{
			((IOreDict)item).registerOreDict();
		}
	}

	private static void registerItemModel(ItemBase item) {
		item.registerItemModel();
	}
}
