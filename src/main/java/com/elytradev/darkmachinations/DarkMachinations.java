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

package com.elytradev.darkmachinations;

import com.elytradev.darkmachinations.gui.creative.DarkMachinationsCreativeTab;
import com.elytradev.darkmachinations.proxy.CommonProxy;
import com.elytradev.darkmachinations.init.*;
import com.elytradev.darkmachinations.world.WorldGen;
import com.elytradev.probe.api.IProbeDataProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = DarkMachinations.MOD_ID,
		name = DarkMachinations.MOD_NAME,
		version = DarkMachinations.MOD_VERSION)
@Mod.EventBusSubscriber
public class DarkMachinations {

	public static final String MOD_ID = "darkmachinations";
	public static final String MOD_NAME = "Dark Machinations";
	public static final String MOD_VERSION = "1.12.1-0.1.0";

	public static final DarkMachinationsCreativeTab divitaeTab = new DarkMachinationsCreativeTab(MOD_ID);

	@Mod.Instance(MOD_ID)
	public static DarkMachinations instance;

	@SidedProxy(
			clientSide = "com.elytradev.darkmachinations.proxy.ClientProxy",
			serverSide = "com.elytradev.darkmachinations.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final Logger LOG = LogManager.getLogger(MOD_NAME);

	@CapabilityInject(IProbeDataProvider.class)
	public static Capability<IProbeDataProvider> PROBE_CAPABILITY = null;

	static {
		net.minecraftforge.fluids.FluidRegistry.enableUniversalBucket();
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(net.minecraftforge.fluids.FluidRegistry.WATER);
		net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(net.minecraftforge.fluids.FluidRegistry.LAVA);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(MOD_NAME + " is starting up!");
		DMFluids.init();
		proxy.init();
		GameRegistry.registerWorldGenerator(new WorldGen(), 3);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		DMBlocks.registerBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		DMItems.registerItems(event.getRegistry());
		DMBlocks.registerItemBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		DMBlocks.registerBlockModels();
		DMItems.registerItemModels();
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		DMSounds.registerSounds(event.getRegistry());
	}
}
