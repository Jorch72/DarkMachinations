package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCompressor extends BlockMachineBase<TileEntityCompressor> {
    public BlockCompressor() {
        super(Material.IRON, "machine_compressor", GuiRegistry.GUI_MACHINE_COMPRESSOR);
    }


    @Override
    public Class<TileEntityCompressor> getTileEntityClass() {
        return TileEntityCompressor.class;
    }

    @Nullable
    @Override
    public TileEntityCompressor createTileEntity(World world, IBlockState state) {
        return new TileEntityCompressor();
    }
}