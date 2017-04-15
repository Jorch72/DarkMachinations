package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.DarkMachinations;
import com.CalmBit.DarkMachinations.generic.IDivitaeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockMachineBase<TE extends TileEntityBase> extends Block implements IDivitaeBlock {
    protected String name;
    protected int guiID;
    public enum EnumMachineState implements IStringSerializable
    {
        OFF("off"),
        ON("on");

        private final String name;

        private EnumMachineState(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public static final PropertyEnum<BlockCrusher.EnumMachineState> STATE = PropertyEnum.create("state", BlockMachineBase.EnumMachineState.class);

    protected BlockMachineBase(Material materialIn, String name, int guiID) {
        super(materialIn);
        this.name = name;
        this.guiID = guiID;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(DarkMachinations.divitaeTab);
        setHarvestLevel("pickaxe", 1);
        setHardness(5.0f);
        setResistance(10.0f);
        setDefaultState(this.blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(STATE, EnumMachineState.OFF));

    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote) {
            TileEntity tileentity = world.getTileEntity(pos);
            if(tileentity != null) {
                player.openGui(DarkMachinations.instance, guiID, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    public void registerItemModel(ItemBlock itemBlock)
    {
        DarkMachinations.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    @Override
    public BlockMachineBase setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        BlockMachineBase.EnumMachineState machineState = EnumMachineState.OFF;
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if(tileentity != null)
        {
            TE crusher = (TE)tileentity;
            if(crusher.getActive())
                machineState = EnumMachineState.ON;
        }
        return state.withProperty(STATE, machineState);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = world.getBlockState(pos).getActualState(world, pos);
        int lightLevel = 0;
        if(state.getBlock() instanceof BlockMachineBase) {
            if (state.getValue(STATE) == BlockMachineBase.EnumMachineState.ON)
                lightLevel = 7;
            return lightLevel;
        }
        else
            return super.getLightValue(state, world, pos);

    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockHorizontal.FACING, STATE);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TE tileEntity = (TE)worldIn.getTileEntity(pos);

        if (tileEntity != null)
        {
            for(int i =0;i < tileEntity.getItemStackHandler().getSlots();i++)
            {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileEntity.getItemStackHandler().getStackInSlot(i));
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(BlockHorizontal.FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(BlockHorizontal.FACING)).getIndex();
    }

    public abstract Class<TE> getTileEntityClass();

    public TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE)world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);
}
