package com.CalmBit.Divitae.world;

import com.CalmBit.Divitae.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == 0)
        {
            generateOre(world, random, chunkX, chunkZ, BlockRegistry.ore_copper.getDefaultState(), 10, 32, 64, 5 + random.nextInt(5));
            generateOre(world, random, chunkX, chunkZ, BlockRegistry.ore_silver.getDefaultState(), 4, 10, 32, 3 + random.nextInt(4));
            generateOre(world, random, chunkX, chunkZ, BlockRegistry.ore_tin.getDefaultState(), 6, 12, 56, 6 + random.nextInt(4));
        }
    }

    private void generateOre(World world, Random rand, int chunkX, int chunkZ, IBlockState blockState, int chances, int yMin, int yMax, int veinSize)
    {
        int range = yMax-yMin;
        for(int i=0;i< chances;i++)
        {
            BlockPos pos = new BlockPos((chunkX*16) + rand.nextInt(16), yMin + rand.nextInt(range), (chunkZ*16) + rand.nextInt(16));
            WorldGenMinable oreGen = new WorldGenMinable(blockState, veinSize);
            oreGen.generate(world, rand, pos);
        }
    }

}
