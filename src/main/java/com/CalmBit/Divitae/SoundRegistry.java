package com.CalmBit.Divitae;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SoundRegistry {

    public static SoundEvent goldenCreeperHiss;
    public static SoundEvent goldenCreeperHurt;
    public static SoundEvent goldenCreeperKilled;

    public static void init()
    {
        goldenCreeperHiss = registerSound("entity.golden_creeper.primed");
        goldenCreeperHurt = registerSound("entity.golden_creeper.hurt");
        goldenCreeperKilled = registerSound("entity.golden_creeper.death");
    }

    public static SoundEvent registerSound(String resourceLocation)
    {
        ResourceLocation sound = new ResourceLocation(Divitae.DIVITAE_MOD_ID, resourceLocation);
        return GameRegistry.register(new SoundEvent(sound).setRegistryName(sound));
    }
}
