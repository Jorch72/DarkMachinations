package com.CalmBit.DarkMachinations;

import com.CalmBit.DarkMachinations.generic.DivitaeCreativeTab;
import com.CalmBit.DarkMachinations.proxy.CommonProxy;
import com.CalmBit.DarkMachinations.world.WorldGen;
import com.elytradev.probe.api.IProbeDataProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = DarkMachinations.DIVITAE_MOD_ID, name = DarkMachinations.DIVITAE_NAME, version = DarkMachinations.DIVITAE_VERSION, useMetadata = true)
public class DarkMachinations {
    public static final String DIVITAE_MOD_ID = "divitae";
    public static final String DIVITAE_NAME = "DarkMachinations";
    public static final String DIVITAE_VERSION = "1.11.2-0.0.0.1";

    public static final DivitaeCreativeTab divitaeTab = new DivitaeCreativeTab(DIVITAE_MOD_ID);

    @Mod.Instance(DIVITAE_MOD_ID)
    public static DarkMachinations instance;

    @SidedProxy(clientSide = "com.CalmBit.DarkMachinations.proxy.ClientProxy", serverSide = "com.CalmBit.DarkMachinations.proxy.CommonProxy")
    public static CommonProxy proxy;

    @CapabilityInject(IProbeDataProvider.class)
    public static Capability<IProbeDataProvider> PROBE_CAPABILITY = null;

    static {
        net.minecraftforge.fluids.FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println(DIVITAE_NAME + " is starting up!");
        SoundRegistry.init();
        LootTableRegistry.init();
        EntityRegistry.init();
        ItemRegistry.init();
        BlockRegistry.init();
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