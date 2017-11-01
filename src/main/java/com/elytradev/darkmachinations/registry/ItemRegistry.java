/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017:
 *     Ethan Brooks (CalmBit),
 *     and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
