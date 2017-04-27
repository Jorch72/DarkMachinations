package com.calmbit.darkmachinations.registry;


import com.calmbit.darkmachinations.DarkMachinations;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTableRegistry {

    public static ResourceLocation ENTITIES_GOLDEN_CREEPER;
    public static void init()
    {
        ENTITIES_GOLDEN_CREEPER = registerLootTable("entities/golden_creeper");
    }

    public static ResourceLocation registerLootTable(String resourceLocation)
    {
        return LootTableList.register(new ResourceLocation(DarkMachinations.DARKMACHINATIONS_MOD_ID, resourceLocation));
    }
}
