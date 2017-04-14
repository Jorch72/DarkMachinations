package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGrinder extends BlockMachineBase<TileEntityGrinder> {

    public BlockGrinder() {
        super(Material.IRON, "machine_grinder", GuiRegistry.GUI_MACHINE_GRINDER);
    }

    @Override
    public Class<TileEntityGrinder> getTileEntityClass()
    {
        return TileEntityGrinder.class;
    }

    @Nullable
    @Override
    public TileEntityGrinder createTileEntity(World world, IBlockState state)
    {
        return new TileEntityGrinder();
    }
}
