package com.calmbit.darkmachinations.world;

import com.calmbit.darkmachinations.block.BlockFluidHeavyCrudeOil;
import com.calmbit.darkmachinations.registry.BlockRegistry;
import com.calmbit.darkmachinations.registry.FluidRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateOil(world, random, chunkX, chunkZ, 1);
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

    public void generateOil(World world, Random rand, int chunkX, int chunkZ, int chances) {
        WorldGenLakes lakeGen = new WorldGenLakes(FluidRegistry.heavy_crude_oil);
        BlockPos pos = new BlockPos((chunkX*16) + rand.nextInt(16), 0, (chunkZ*16) + rand.nextInt(16));
        pos = pos.add(0, world.getHeight(pos.getX(), pos.getZ()), 0);
        lakeGen.generate(world, rand, pos);
    }

}
