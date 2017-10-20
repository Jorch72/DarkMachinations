package com.elytradev.darkmachinations;

import com.elytradev.darkmachinations.gui.creative.DarkMachinationsCreativeTab;
import com.elytradev.darkmachinations.proxy.CommonProxy;
import com.elytradev.darkmachinations.registry.*;
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
		modid = DarkMachinations.DARKMACHINATIONS_MOD_ID,
		name = DarkMachinations.DARKMACHINATIONS_NAME,
		version = DarkMachinations.DARKMACHINATIONS_VERSION)
@Mod.EventBusSubscriber
public class DarkMachinations {

	public static final String DARKMACHINATIONS_MOD_ID = "darkmachinations";
		public static final String DARKMACHINATIONS_NAME = "Dark Machinations";
	public static final String DARKMACHINATIONS_VERSION = "1.12.1-0.1.0";

	public static final DarkMachinationsCreativeTab divitaeTab = new DarkMachinationsCreativeTab(DARKMACHINATIONS_MOD_ID);

	@Mod.Instance(DARKMACHINATIONS_MOD_ID)
	public static DarkMachinations instance;

	@SidedProxy(
			clientSide = "com.elytradev.darkmachinations.proxy.ClientProxy",
			serverSide = "com.elytradev.darkmachinations.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final Logger LOG = LogManager.getLogger(DARKMACHINATIONS_NAME);

	@CapabilityInject(IProbeDataProvider.class)
	public static Capability<IProbeDataProvider> PROBE_CAPABILITY = null;

	static {
		net.minecraftforge.fluids.FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println(DARKMACHINATIONS_NAME + " is starting up!");
		FluidRegistry.init();
		proxy.init();
		RecipeRegistry.init();
		GameRegistry.registerWorldGenerator(new WorldGen(), 3);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		BlockRegistry.registerBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ItemRegistry.registerItems(event.getRegistry());
		BlockRegistry.registerItemBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		BlockRegistry.registerBlockModels();
		ItemRegistry.registerItemModels();
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		SoundRegistry.registerSounds(event.getRegistry());
	}
}
