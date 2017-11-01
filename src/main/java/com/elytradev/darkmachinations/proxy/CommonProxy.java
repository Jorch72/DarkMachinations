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
