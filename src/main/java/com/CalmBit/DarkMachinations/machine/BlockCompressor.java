package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.GuiRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if(stack.hasTagCompound()) {
            NBTTagCompound itemData = stack.getTagCompound();
            if(itemData.hasKey("energy_stored")) {
                int energyStored = itemData.getInteger("energy_stored");

                if(energyStored > TileEntityCompressor.ENERGY_CAPACITY)
                    energyStored = TileEntityCompressor.ENERGY_CAPACITY;

                if(worldIn.getTileEntity(pos) instanceof  TileEntityCompressor) {
                    ((TileEntityCompressor)worldIn.getTileEntity(pos)).energyStorage.setEnergyStored(energyStored);
                }
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1);
        ArrayList<ItemStack> ret = new ArrayList<>();
        if(world.getTileEntity(pos) instanceof TileEntityCompressor) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("energy_stored", ((TileEntityCompressor)world.getTileEntity(pos)).energyStorage.getEnergyStored());
            stack.setTagCompound(compound);
        }
        ret.add(stack);

        return ret;
    }
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool)
    {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }
}
