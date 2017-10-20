package com.elytradev.darkmachinations.registry;

import com.elytradev.darkmachinations.DarkMachinations;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class SoundRegistry {

	private static final SoundEvent goldenCreeperHiss = createSoundEvent("entity.golden_creeper.primed");
	private static final SoundEvent goldenCreeperHurt = createSoundEvent("entity.golden_creeper.hurt");
	private static final SoundEvent goldenCreeperKilled = createSoundEvent("entity.golden_creeper.death");

	private static SoundEvent createSoundEvent(String resourceLocation) {
		ResourceLocation sound = new ResourceLocation(DarkMachinations.DARKMACHINATIONS_MOD_ID, resourceLocation);
		return new SoundEvent(sound).setRegistryName(sound);
	}

	public static void registerSounds(IForgeRegistry<SoundEvent> registry) {
		registry.registerAll(
				goldenCreeperHiss,
				goldenCreeperHurt,
				goldenCreeperKilled
		);
	}
}
