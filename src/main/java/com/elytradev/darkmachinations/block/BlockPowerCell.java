package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.generic.IDarmaBlock;
import com.elytradev.darkmachinations.item.ItemBlockPowerCell;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class BlockPowerCell extends BlockBase implements IDarmaBlock {

	public static final PropertyBool CREATIVE = PropertyBool.create("creative");

	public BlockPowerCell() {
		super(Material.IRON, "power_cell");
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH)
				.withProperty(CREATIVE, false));
		this.setCreativeTab(DarkMachinations.divitaeTab);
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
}
