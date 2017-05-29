package com.calmbit.darkmachinations.registry;

import com.calmbit.darkmachinations.DarkMachinations;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityRegistry {

    public static int ID = 0;

    public static void init()
    {

    }

    public static void registerEntity(ResourceLocation entityName, Class<? extends Entity> entityClass, int trackingRange, int updateFrequency, int eggPrimary, int eggSecondary)
    {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(entityName, entityClass, entityName.toString(), ++ID, DarkMachinations.instance, trackingRange, updateFrequency, true, eggPrimary, eggSecondary);
    }
}
