package com.calmbit.darkmachinations;

import com.calmbit.darkmachinations.gui.creative.DarkMachinationsCreativeTab;
import com.calmbit.darkmachinations.proxy.CommonProxy;
import com.calmbit.darkmachinations.registry.*;
import com.calmbit.darkmachinations.world.WorldGen;
import com.elytradev.probe.api.IProbeDataProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = DarkMachinations.DARKMACHINATIONS_MOD_ID, name = DarkMachinations.DARKMACHINATIONS_NAME, version = DarkMachinations.DARKMACHINATIONS_VERSION)
public class DarkMachinations {
    public static final String DARKMACHINATIONS_MOD_ID = "darkmachinations";
    public static final String DARKMACHINATIONS_NAME = "DarkMachinations";
    public static final String DARKMACHINATIONS_VERSION = "1.11.2-0.1.0.0";

    public static final DarkMachinationsCreativeTab divitaeTab = new DarkMachinationsCreativeTab(DARKMACHINATIONS_MOD_ID);

    @Mod.Instance(DARKMACHINATIONS_MOD_ID)
    public static DarkMachinations instance;

    @SidedProxy(clientSide = "com.calmbit.darkmachinations.proxy.ClientProxy", serverSide = "com.calmbit.darkmachinations.proxy.CommonProxy")
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
        SoundRegistry.init();
        LootTableRegistry.init();
        EntityRegistry.init();
        BlockRegistry.init();
        ItemRegistry.init();
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
}
