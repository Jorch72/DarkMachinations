package com.CalmBit.Divitae.cable;

import com.CalmBit.Divitae.generic.BlockBase;
import com.sun.glass.ui.View;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockCable extends BlockBase {

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public static final List<AxisAlignedBB> boundingBoxes = new ArrayList<AxisAlignedBB>();

    public BlockCable(String name) {
        super(Material.CLOTH, name);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, false)
                .withProperty(EAST, false)
                .withProperty(SOUTH, false)
                .withProperty(WEST, false)
                .withProperty(UP, false)
                .withProperty(DOWN, false));
        if(boundingBoxes.isEmpty()) {
            for(int i =0;i < 64;i++) {
                double  minX = 0.3125,
                        minY = 0.3125,
                        minZ = 0.3125,
                        maxX = 0.6875,
                        maxY = 0.6875,
                        maxZ = 0.6875;

                if((i & 1) == 1) // North
                    minZ = 0;
                if((i & 2) == 2) // East
                    maxX = 1;
                if((i & 4) == 4) // South
                    maxZ = 1;
                if((i & 8) == 8) // West
                    minX = 0;
                if((i & 16) == 16) // Up
                    maxY = 1;
                if((i & 32) == 32) // Down
                    minY = 0;

                boundingBoxes.add(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if(!worldIn.isRemote) {
            TileEntityCable thisCable = (TileEntityCable)worldIn.getTileEntity(pos);

            // We'll iterate over every adjacent block to find other blocks
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos neighbour = pos.add(facing.getDirectionVec());

                if (worldIn.getTileEntity(neighbour) != null) {

                    TileEntity tile = worldIn.getTileEntity(neighbour);

                    if (tile instanceof TileEntityCable) {
                        TileEntityCable otherCable = (TileEntityCable) tile;
                        if (otherCable.getNetwork() != null) {
                            thisCable.mergeNetworks(otherCable.getNetwork());
                        }
                        System.out.println("Found another cable!");
                    }

                    else if (tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                        System.out.println("Found an energy producer!");
                    }
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return boundingBoxes.get(getBoundingBoxIndex(getActualState(state, source, pos)));
    }

    private int getBoundingBoxIndex(IBlockState state) {
        int index = 0;

        if(state.getValue(NORTH))
            index += 1;
        if(state.getValue(EAST))
            index += 2;
        if(state.getValue(SOUTH))
            index += 4;
        if(state.getValue(WEST))
            index += 8;
        if(state.getValue(UP))
            index += 16;
        if(state.getValue(DOWN))
            index += 32;

        return index;

    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public int getMetaFromState(IBlockState state) {
        return 0;
    }



    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(NORTH, canConnectTo(world, pos, EnumFacing.NORTH))
                    .withProperty(EAST, canConnectTo(world, pos, EnumFacing.EAST))
                    .withProperty(SOUTH, canConnectTo(world, pos, EnumFacing.SOUTH))
                    .withProperty(WEST, canConnectTo(world, pos, EnumFacing.WEST))
                    .withProperty(UP, canConnectTo(world, pos, EnumFacing.UP))
                    .withProperty(DOWN, canConnectTo(world, pos, EnumFacing.DOWN));
    }

    public boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        TileEntity entity = world.getTileEntity(pos.add(facing.getDirectionVec()));
        return (entity != null && entity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()));
    }

    @Override
    public BlockCable setCreativeTab(CreativeTabs tab) {
        return (BlockCable) super.setCreativeTab(tab);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntityCable createTileEntity(World world, IBlockState state) {
        return new TileEntityCable();
    }



}