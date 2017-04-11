package com.CalmBit.Divitae;

import com.CalmBit.Divitae.generic.DivitaeCreativeTab;
import com.CalmBit.Divitae.proxy.CommonProxy;
import com.CalmBit.Divitae.world.WorldGen;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Divitae.DIVITAE_MOD_ID, name = Divitae.DIVITAE_NAME, version = Divitae.DIVITAE_VERSION, useMetadata = true)
public class Divitae {
    public static final String DIVITAE_MOD_ID = "divitae";
    public static final String DIVITAE_NAME = "Divitae";
    public static final String DIVITAE_VERSION = "1.11.2-0.0.0.1";

    public static final DivitaeCreativeTab divitaeTab = new DivitaeCreativeTab(DIVITAE_MOD_ID);

    @Mod.Instance(DIVITAE_MOD_ID)
    public static Divitae instance;

    @SidedProxy(clientSide = "com.CalmBit.Divitae.proxy.ClientProxy", serverSide = "com.CalmBit.Divitae.proxy.CommonProxy")
    public static CommonProxy proxy;

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
