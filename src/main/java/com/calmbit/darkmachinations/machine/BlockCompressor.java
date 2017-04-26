package com.calmbit.darkmachinations.machine;

import com.calmbit.darkmachinations.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
