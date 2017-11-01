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
import com.elytradev.darkmachinations.client.RenderPowerCell;
import com.elytradev.darkmachinations.client.RenderPump;
import com.elytradev.darkmachinations.registry.BlockRegistry;
import com.elytradev.darkmachinations.tileentity.TileEntityPowerCell;
import com.elytradev.darkmachinations.tileentity.TileEntityPump;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("MethodCallSideOnly")
public class ClientProxy extends CommonProxy {

	public void init()
	{
		super.init();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPump.class, new RenderPump());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPowerCell.class, new RenderPowerCell());
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id)
	{
		this.registerItemRenderer(item, meta, id, "inventory");
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id, String variant)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(DarkMachinations.DARKMACHINATIONS_MOD_ID + ":" + id, variant));
	}

}
