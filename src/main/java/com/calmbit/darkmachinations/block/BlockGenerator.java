package com.calmbit.darkmachinations.block;

import com.calmbit.darkmachinations.tileentity.TileEntityGenerator;
import com.calmbit.darkmachinations.registry.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGenerator extends BlockMachineBase<TileEntityGenerator> {
    public BlockGenerator() {
        super(Material.IRON, "machine_generator", GuiRegistry.GUI_MACHINE_GENERATOR);
    }


    @Override
    public Class<TileEntityGenerator> getTileEntityClass() {
        return TileEntityGenerator.class;
    }

    @Nullable
    @Override
    public TileEntityGenerator createTileEntity(World world, IBlockState state) {
        return new TileEntityGenerator();
    }


}
