package com.CalmBit.Divitae;

import com.CalmBit.Divitae.monster.EntityGoldenCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Ethan on 2017-03-26.
 */
public class EntityRegistry {

    public static int ID = 0;

    public static void init()
    {
        registerEntity(new ResourceLocation(Divitae.DIVITAE_MOD_ID, "golden_creeper"), EntityGoldenCreeper.class, 64, 3, 16777011, 5065984);
    }

    public static void registerEntity(ResourceLocation entityName, Class<? extends Entity> entityClass, int trackingRange, int updateFrequency, int eggPrimary, int eggSecondary)
    {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(entityName, entityClass, entityName.toString(), ++ID, Divitae.instance, trackingRange, updateFrequency, true, eggPrimary, eggSecondary);
    }
}
