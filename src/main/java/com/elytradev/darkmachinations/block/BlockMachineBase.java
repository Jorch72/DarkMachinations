package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.generic.IDarmaBlock;
import com.elytradev.darkmachinations.tileentity.TileEntityBase;
import com.elytradev.darkmachinations.tileentity.TileEntityGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BlockMachineBase<TE extends TileEntityBase> extends Block implements IDarmaBlock {
	protected String name;
	protected int guiID;
	public enum EnumMachineState implements IStringSerializable
	{
		OFF("off"),
		ON("on");

		private final String name;

		EnumMachineState(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return this.name;
		}

		@Nonnull
		public String getName()
		{
			return this.name;
		}
	}

	private static final PropertyEnum<BlockCrusher.EnumMachineState> STATE = PropertyEnum.create("state", BlockMachineBase.EnumMachineState.class);

	protected BlockMachineBase(Material materialIn, String name, int guiID) {
		super(materialIn);
		this.name = name;
		this.guiID = guiID;

		setUnlocalizedName("darkmachinations."+name);
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

	@Nonnull
	@Override
	public BlockMachineBase setCreativeTab(@Nonnull CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}

	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Nonnull
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Nonnull
	@Override
	public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
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

	@Nonnull
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockHorizontal.FACING, STATE);
	}

	@Nonnull
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}


	@Override
	public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
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

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(BlockHorizontal.FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getIndex();
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
	public abstract TE createTileEntity(@Nonnull World world, @Nonnull IBlockState state);

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		NBTTagCompound compound = stack.getTagCompound();
		if(compound != null) {
			if (GuiScreen.isShiftKeyDown()) {
				if (compound.hasKey("energy_stored"))
					tooltip.add(TextFormatting.RED.toString() + TextFormatting.ITALIC.toString() + "Energy: " + TextFormatting.RESET.toString() + compound.getInteger("energy_stored") + "/" + TileEntityGenerator.ENERGY_CAPACITY + " FU");
				if(compound.hasKey("fluid_stored")) {
					NBTTagCompound fluid = compound.getCompoundTag("fluid_stored");
					if(fluid.hasKey("FluidName"))
						tooltip.add(TextFormatting.BLUE.toString() + TextFormatting.ITALIC.toString() + "Fluid: " + TextFormatting.RESET.toString() + I18n.format("fluid." + fluid.getString("FluidName")) + ", " + fluid.getInteger("Amount") + "mb");
				}
			} else {
				tooltip.add(TextFormatting.ITALIC.toString() + "Press SHIFT for more information...");
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound itemData = stack.getTagCompound();
			if(worldIn.getTileEntity(pos) instanceof  TileEntityBase) {
				((TileEntityBase)worldIn.getTileEntity(pos)).readItemData(itemData);
			}
		}
	}

	@Nonnull
	@Override
	public List<ItemStack> getDrops(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune)
	{
		ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1);
		ArrayList<ItemStack> ret = new ArrayList<>();
		if(world.getTileEntity(pos) instanceof TileEntityBase) {
			NBTTagCompound compound = new NBTTagCompound();
			((TileEntityBase)world.getTileEntity(pos)).writeItemData(compound);
			stack.setTagCompound(compound);
		}
		ret.add(stack);

		return ret;
	}
	@Override
	public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
		//If it will harvest, delay deletion of the block until after getDrops
		return willHarvest || super.removedByPlayer(state, world, pos, player, false);
	}

	@Override
	public void harvestBlock(@Nonnull World world, EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nullable TileEntity te, ItemStack tool)
	{
		super.harvestBlock(world, player, pos, state, te, tool);
		world.setBlockToAir(pos);
	}

	@Override
	public ItemBlock getItemBlock() {
		return (ItemBlock)new ItemBlock(this).setRegistryName(this.getRegistryName());
	}
}
