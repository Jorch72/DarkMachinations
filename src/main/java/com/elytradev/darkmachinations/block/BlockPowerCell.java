package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.generic.IDarmaBlock;
import com.elytradev.darkmachinations.item.ItemBlockPowerCell;
import com.elytradev.darkmachinations.registry.GuiRegistry;
import com.elytradev.darkmachinations.tileentity.TileEntityBase;
import com.elytradev.darkmachinations.tileentity.TileEntityPowerCell;
import com.elytradev.darkmachinations.tileentity.TileEntitySolidGenerator;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockPowerCell extends BlockBase implements IDarmaBlock {

	public static final PropertyBool CREATIVE = PropertyBool.create("creative");

	public BlockPowerCell() {
		super(Material.IRON, "power_cell");
		setDefaultState(this.blockState.getBaseState()
				.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH)
				.withProperty(CREATIVE, false));
		setCreativeTab(DarkMachinations.divitaeTab);
		setHarvestLevel("pickaxe", 1);
		setHardness(5.0f);
		setResistance(10.0f);
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand)
				.withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 8));
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean creative = ((meta & 0b1000) == 0b1000);

		EnumFacing enumfacing = EnumFacing.getFront(meta & 0b0111);
		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState()
				.withProperty(BlockHorizontal.FACING, enumfacing)
				.withProperty(CREATIVE, creative);
	}

	@Nonnull
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockHorizontal.FACING, CREATIVE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(BlockHorizontal.FACING).getIndex()) | (state.getValue(CREATIVE) ? 0b1000 : 0b0000);
	}

	@Override
	public ItemBlock getItemBlock() {
		return (ItemBlock)(new ItemBlockPowerCell(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerItemModel(ItemBlock itemBlock) {
		DarkMachinations.proxy.registerItemRenderer(itemBlock, 0, "power_cell");
		DarkMachinations.proxy.registerItemRenderer(itemBlock, 8, "power_cell", "inventory_creative");
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityPowerCell(state.getValue(CREATIVE));
	}


	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(this, 1, state.getValue(CREATIVE) ? 0b1000 : 0b0000));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null) {
			if (GuiScreen.isShiftKeyDown()) {
				if (compound.hasKey("energy_stored"))
					tooltip.add(TextFormatting.RED.toString() + TextFormatting.ITALIC.toString() + "Energy: " + TextFormatting.RESET.toString() + compound.getInteger("energy_stored") + "/" + TileEntitySolidGenerator.ENERGY_CAPACITY + " FU");
			} else {
				tooltip.add(TextFormatting.ITALIC.toString() + "Press SHIFT for more information...");
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound itemData = stack.getTagCompound();
			if(worldIn.getTileEntity(pos) instanceof TileEntityBase) {
				((TileEntityBase)worldIn.getTileEntity(pos)).readItemData(itemData);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote) {
			TileEntity tileentity = world.getTileEntity(pos);
			if(tileentity != null) {
				player.openGui(DarkMachinations.instance, GuiRegistry.GUI_POWER_CELL, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
}
