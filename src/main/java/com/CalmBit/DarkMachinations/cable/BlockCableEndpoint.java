package com.CalmBit.DarkMachinations.cable;


import com.CalmBit.DarkMachinations.network.EnergyNetwork;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCableEndpoint extends BlockCable {

    public BlockCableEndpoint(String name) {
        super(name);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntityCableNode createTileEntity(World world, IBlockState state) {
        return new TileEntityCableNode(new EnergyNetwork(world));
    }



}
