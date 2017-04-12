package com.CalmBit.Divitae.cable;


import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCableNode extends BlockCable {

    public BlockCableNode(String name) {
        super(name);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntityCableNode createTileEntity(World world, IBlockState state) {
        return new TileEntityCableNode();
    }

}
