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

package com.elytradev.darkmachinations.init;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.block.*;
import com.elytradev.darkmachinations.tileentity.TileEntityCableNode;
import com.elytradev.darkmachinations.block.IDarmaBlock;
import com.elytradev.darkmachinations.generic.IOreDict;
import com.elytradev.darkmachinations.tileentity.TileEntityPowerCell;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class DMBlocks {

	public static final BlockCrusher machine_crusher = new BlockCrusher();
	public static final BlockCompressor machine_compressor = new BlockCompressor();
	public static final BlockBase machine_redstone_core = new BlockBase(Material.IRON,
			"machine_redstone_core").setCreativeTab(DarkMachinations.divitaeTab);
	public static final BlockSolidGenerator machine_generator = new BlockSolidGenerator();
	public static final BlockPump machine_pump = new BlockPump();
	public static final BlockElectricFurnace machine_electric_furnace = new BlockElectricFurnace();
	public static final BlockPowerCell power_cell = new BlockPowerCell();

	public static final BlockCable cable_copper_insulated =
			new BlockCableCopperInsulated("cable_copper_insulated").setCreativeTab(DarkMachinations.divitaeTab);
	public static final BlockCable cable_gold_insulated =
			new BlockCableGoldInsulated("cable_gold_insulated").setCreativeTab(DarkMachinations.divitaeTab);
	public static final BlockCable cable_tin_insulated =
			new BlockCableTinInsulated("cable_tin_insulated").setCreativeTab(DarkMachinations.divitaeTab);
	public static final BlockCable cable_copper_uninsulated =
			new BlockCableCopperUninsulated("cable_copper_uninsulated").setCreativeTab(DarkMachinations.divitaeTab);
	public static final BlockCable cable_gold_uninsulated =
			new BlockCableGoldUninsulated("cable_gold_uninsulated").setCreativeTab(DarkMachinations.divitaeTab);
	public static final BlockCable cable_tin_uninsulated =
			new BlockCableTinUninsulated("cable_tin_uninsulated").setCreativeTab(DarkMachinations.divitaeTab);

	public static final BlockCableEndpoint cable_regular_node = new BlockCableEndpoint("cable_regular_node");

	public static final BlockFluidHeavyCrudeOil heavy_crude_oil =
			new BlockFluidHeavyCrudeOil(DMFluids.fluid_heavy_crude_oil, MaterialLiquid.WATER);
	public static final BlockFluidFuel fuel = new BlockFluidFuel(DMFluids.fluid_fuel, MaterialLiquid.WATER);
	public static final BlockFluidHydrogenGas hydrogen_gas =
			new BlockFluidHydrogenGas(DMFluids.fluid_hydrogen_gas, MaterialLiquid.WATER);

	private static void registerBlock(IForgeRegistry<Block> registry, IDarmaBlock block) {
		registry.register(((Block)block));

		if (block instanceof IOreDict) {
			((IOreDict)block).registerOreDict();
		}
		if (block instanceof BlockMachineBase) {
			GameRegistry.registerTileEntity(((BlockMachineBase<?>)block).getTileEntityClass(),
					((Block)block).getRegistryName().toString());
		}
		if (block instanceof BlockPowerCell) {
			GameRegistry.registerTileEntity(TileEntityPowerCell.class, ((Block)block).getRegistryName().toString());
		}
		if (block instanceof BlockCableEndpoint) {
			GameRegistry.registerTileEntity(TileEntityCableNode.class, ((Block)block).getRegistryName().toString());
		}
	}

	public static void registerItemBlock(IForgeRegistry<Item> registry, ItemBlock itemBlock) {
		registry.register(itemBlock);

		if (itemBlock instanceof IOreDict) {
			((IOreDict)itemBlock).registerOreDict();
		}
	}

	public static void registerBlockModel(IDarmaBlock block) {
		block.registerItemModel((ItemBlock)Item.getItemFromBlock((Block)block));
	}

	public static void registerBlocks(IForgeRegistry<Block> registry) {
		registerBlock(registry, machine_crusher);
		registerBlock(registry, machine_compressor);
		registerBlock(registry, machine_redstone_core);
		registerBlock(registry, machine_generator);
		registerBlock(registry, machine_pump);
		registerBlock(registry, machine_electric_furnace);
		registerBlock(registry, power_cell);
		registerBlock(registry, cable_copper_insulated);
		registerBlock(registry, cable_gold_insulated);
		registerBlock(registry, cable_tin_insulated);
		registerBlock(registry, cable_copper_uninsulated);
		registerBlock(registry, cable_gold_uninsulated);
		registerBlock(registry, cable_tin_uninsulated);
		registerBlock(registry, cable_regular_node);
		registerBlock(registry, heavy_crude_oil);
		registerBlock(registry, fuel);
		registerBlock(registry, hydrogen_gas);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		registerItemBlock(registry, machine_crusher.getItemBlock());
		registerItemBlock(registry, machine_compressor.getItemBlock());
		registerItemBlock(registry, machine_redstone_core.getItemBlock());
		registerItemBlock(registry, machine_generator.getItemBlock());
		registerItemBlock(registry, machine_pump.getItemBlock());
		registerItemBlock(registry, machine_electric_furnace.getItemBlock());
		registerItemBlock(registry, power_cell.getItemBlock());
		registerItemBlock(registry, cable_copper_insulated.getItemBlock());
		registerItemBlock(registry, cable_gold_insulated.getItemBlock());
		registerItemBlock(registry, cable_tin_insulated.getItemBlock());
		registerItemBlock(registry, cable_copper_uninsulated.getItemBlock());
		registerItemBlock(registry, cable_gold_uninsulated.getItemBlock());
		registerItemBlock(registry, cable_tin_uninsulated.getItemBlock());
		registerItemBlock(registry, cable_regular_node.getItemBlock());
		registerItemBlock(registry, heavy_crude_oil.getItemBlock());
		registerItemBlock(registry, fuel.getItemBlock());
		registerItemBlock(registry, hydrogen_gas.getItemBlock());
	}

	public static void registerBlockModels() {
		registerBlockModel(machine_crusher);
		registerBlockModel(machine_compressor);
		registerBlockModel(machine_redstone_core);
		registerBlockModel(machine_generator);
		registerBlockModel(machine_pump);
		registerBlockModel(machine_electric_furnace);
		registerBlockModel(power_cell);
		registerBlockModel(cable_copper_insulated);
		registerBlockModel(cable_gold_insulated);
		registerBlockModel(cable_tin_insulated);
		registerBlockModel(cable_copper_uninsulated);
		registerBlockModel(cable_gold_uninsulated);
		registerBlockModel(cable_tin_uninsulated);
		registerBlockModel(cable_regular_node);
		registerBlockModel(heavy_crude_oil);
		registerBlockModel(fuel);
		registerBlockModel(hydrogen_gas);
	}
}