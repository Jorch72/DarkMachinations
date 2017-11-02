/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017:
 *     Ethan Brooks (CalmBit),
 *     and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.item.ItemBlockPowerCell;
import com.elytradev.darkmachinations.init.GuiHandler;
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
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
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
		ItemStack stack = new ItemStack(this, 1, state.getValue(CREATIVE) ? 0b1000 : 0b0000);
		if (world.getTileEntity(pos) instanceof TileEntityBase) {
			NBTTagCompound compound = new NBTTagCompound();
			((TileEntityBase)world.getTileEntity(pos)).writeItemData(compound);
			stack.setTagCompound(compound);
		}
		drops.add(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		if ((stack.getItemDamage() & 0b1000) != 0)
			return;

		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null) {
			if (GuiScreen.isShiftKeyDown()) {
				if (compound.hasKey("energy_stored"))
					tooltip.add(TextFormatting.RED.toString() + TextFormatting.ITALIC.toString() + "Energy: " + TextFormatting.RESET.toString() + compound.getInteger("energy_stored") + "/" + TileEntitySolidGenerator.ENERGY_CAPACITY + " FU");
			} else {
				tooltip.add(TextFormatting.ITALIC.toString() + "Press SHIFT for more...");
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound itemData = stack.getTagCompound();
			if (worldIn.getTileEntity(pos) instanceof  TileEntityBase) {
				((TileEntityBase)worldIn.getTileEntity(pos)).readItemData(itemData);
			}
		}
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity != null) {
				player.openGui(DarkMachinations.instance, GuiHandler.GUI_POWER_CELL, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
}
