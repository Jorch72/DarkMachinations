package com.calmbit.darkmachinations.network;


import com.calmbit.darkmachinations.registry.NetworkRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class NetworkEventHandler {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent worldTickEvent) {
        if(!worldTickEvent.world.isRemote)
            NetworkRegistry.networkTick(worldTickEvent.world);
    }
}
