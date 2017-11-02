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

package com.elytradev.darkmachinations.world;

import com.elytradev.darkmachinations.init.DMBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
						 IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		generateOil(world, random, chunkX, chunkZ, 1);
	}

	private void generateOre(World world, Random rand, int chunkX, int chunkZ,
							 IBlockState blockState, int chances, int yMin, int yMax, int veinSize) {
		int range = yMax-yMin;
		for (int i=0;i< chances;i++) {
			BlockPos pos = new BlockPos((chunkX*16) + rand.nextInt(16),
					yMin + rand.nextInt(range), (chunkZ*16) + rand.nextInt(16));
			WorldGenMinable oreGen = new WorldGenMinable(blockState, veinSize);
			oreGen.generate(world, rand, pos);
		}
	}

	public void generateOil(World world, Random rand, int chunkX, int chunkZ, int chances) {
		WorldGenLakes lakeGen = new WorldGenLakes(DMBlocks.heavy_crude_oil);
		BlockPos pos = new BlockPos((chunkX*16) + rand.nextInt(16), 256, (chunkZ*16) + rand.nextInt(16));
		Biome biome = world.getBiome(pos);

		if (biome instanceof BiomeDesert) {
			if (rand.nextInt(10) == 0) {
				lakeGen.generate(world, rand, pos);
			}
		} else if (biome.getTempCategory() == Biome.TempCategory.MEDIUM) {
			if (rand.nextInt(30) == 0) {
				lakeGen.generate(world, rand, pos);
			}
		} else {
			if (rand.nextInt(100) == 0) {
				lakeGen.generate(world, rand, pos);
			}
		}
	}

}
