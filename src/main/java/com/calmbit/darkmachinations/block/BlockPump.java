package com.calmbit.darkmachinations.block;

import com.calmbit.darkmachinations.tileentity.TileEntityPump;
import com.calmbit.darkmachinations.registry.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPump extends BlockMachineBase<TileEntityPump>{

    public BlockPump() {
        super(Material.IRON, "machine_pump", GuiRegistry.GUI_MACHINE_PUMP);
    }

    @Override
    public Class<TileEntityPump> getTileEntityClass() {
        return TileEntityPump.class;
    }

    @Nullable
    @Override
    public TileEntityPump createTileEntity(World world, IBlockState state) {
        return new TileEntityPump();
    }

    @Override
    public boolean getUseNeighborBrightness(IBlockState state) {
        return true;
    }
}
