/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017:
 *     Ethan Brooks (CalmBit),
 *     and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
