package com.calmbit.darkmachinations.registry;

import com.calmbit.darkmachinations.monster.EntityGoldenCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Ethan on 2017-03-26.
 */
public class EntityRegistry {

    public static int ID = 0;

    public static void init()
    {
        registerEntity(new ResourceLocation(DarkMachinations.DARKMACHINATIONS_MOD_ID, "golden_creeper"), EntityGoldenCreeper.class, 64, 3, 16777011, 5065984);
    }

    public static void registerEntity(ResourceLocation entityName, Class<? extends Entity> entityClass, int trackingRange, int updateFrequency, int eggPrimary, int eggSecondary)
    {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(entityName, entityClass, entityName.toString(), ++ID, DarkMachinations.instance, trackingRange, updateFrequency, true, eggPrimary, eggSecondary);
    }
}
