package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.GuiRegistry;
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

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if(stack.hasTagCompound()) {
            NBTTagCompound itemData = stack.getTagCompound();
            if(itemData.hasKey("energy_stored")) {
                int energyStored = itemData.getInteger("energy_stored");

                if(energyStored > TileEntityGenerator.ENERGY_CAPACITY)
                    energyStored = TileEntityGenerator.ENERGY_CAPACITY;

                if(worldIn.getTileEntity(pos) instanceof  TileEntityGenerator) {
                    ((TileEntityGenerator)worldIn.getTileEntity(pos)).energyStorage.setEnergyStored(energyStored);
                }
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1);
        ArrayList<ItemStack> ret = new ArrayList<>();
        if(world.getTileEntity(pos) instanceof TileEntityGenerator) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("energy_stored", ((TileEntityGenerator)world.getTileEntity(pos)).energyStorage.getEnergyStored());
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
