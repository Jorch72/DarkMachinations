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

import com.elytradev.darkmachinations.init.DMBlocks;
import com.elytradev.darkmachinations.init.NetworkHandler;
import com.elytradev.darkmachinations.tileentity.TileEntityCableNode;
import com.elytradev.darkmachinations.network.EnergyNetworkNode;
import com.elytradev.probe.api.IProbeDataProvider;
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
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockCable extends BlockBase {
	static final PropertyBool NORTH = PropertyBool.create("north");
	static final PropertyBool EAST = PropertyBool.create("east");
	static final PropertyBool SOUTH = PropertyBool.create("south");
	static final PropertyBool WEST = PropertyBool.create("west");
	static final PropertyBool UP = PropertyBool.create("up");
	static final PropertyBool DOWN = PropertyBool.create("down");

	static final PropertyBool EDGE_MEMBER = PropertyBool.create("edge_member");

	private static final List<AxisAlignedBB> boundingBoxes = new ArrayList<AxisAlignedBB>();

	public IProbeDataProvider provider;

	public BlockCable(String name) {
		super(Material.CLOTH, name);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(EDGE_MEMBER, false)
				.withProperty(NORTH, false)
				.withProperty(EAST, false)
				.withProperty(SOUTH, false)
				.withProperty(WEST, false)
				.withProperty(UP, false)
				.withProperty(DOWN, false));

		if (boundingBoxes.isEmpty()) {
			for (int i =0;i < 64;i++) {
				double  minX = 0.3125,
						minY = 0.3125,
						minZ = 0.3125,
						maxX = 0.6875,
						maxY = 0.6875,
						maxZ = 0.6875;

				if ((i & 0b000001) != 0) { // North
					minZ = 0;
				}

				if ((i & 0b000010) != 0) { // East
					maxX = 1;
				}

				if ((i & 0b000100) != 0) { // South
					maxZ = 1;
				}

				if ((i & 0b001000) != 0) { // West
					minX = 0;
				}

				if ((i & 0b010000) != 0) { // Up
					maxY = 1;
				}

				if ((i & 0b100000) != 0) { // Down
					minY = 0;
				}

				boundingBoxes.add(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		reappraiseCable(worldIn, pos);
	}

	private void reappraiseCable(World worldIn, BlockPos pos) {
		if (!worldIn.isRemote) {
			for (EnumFacing facing : EnumFacing.VALUES) {
				BlockPos neighbour = pos.add(facing.getDirectionVec());
				if (worldIn.getTileEntity(neighbour) instanceof TileEntityCableNode) {
					NetworkHandler.networkRediscovery(worldIn, ((TileEntityCableNode) worldIn.getTileEntity(neighbour)).getNetwork(), pos);
				} else if (worldIn.getBlockState(neighbour).getBlock() instanceof BlockCable) {
					NetworkHandler.networkRediscovery(worldIn, null, pos);
				} else if (worldIn.getTileEntity(neighbour) != null && worldIn.getTileEntity(neighbour).hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
					IEnergyStorage storage = worldIn.getTileEntity(neighbour).getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());

					EnergyNetworkNode.NodeType nodeType;
					if (storage.canReceive()) {
						if (storage.canExtract()) {
							nodeType = EnergyNetworkNode.NodeType.SENDRECV;
						} else {
							nodeType = EnergyNetworkNode.NodeType.RECEIVER;
						}
					} else {
						nodeType = EnergyNetworkNode.NodeType.SENDER;
					}
					worldIn.setBlockState(pos, DMBlocks.cable_regular_node.getDefaultState(), 3);
					TileEntityCableNode node = ((TileEntityCableNode) worldIn.getTileEntity(pos));
					node.addNodeToNetwork(nodeType);
					NetworkHandler.networkRediscovery(worldIn, ((TileEntityCableNode) worldIn.getTileEntity(pos)).getNetwork(), pos);
				}
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			UUID network = NetworkHandler.getNetworkIdentityForBlock(pos);
			if (network != null) {
				NetworkHandler.getNetwork(network).removeMember(pos);
			}
		}
		super.breakBlock(worldIn, pos, state);
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return boundingBoxes.get(getBoundingBoxIndex(getActualState(state, source, pos)));
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {

	}

	private int getBoundingBoxIndex(IBlockState state) {
		int index = 0b000000;

		if (state.getValue(NORTH)) {
			index |= 0b000001;
		}

		if (state.getValue(EAST)) {
			index |= 0b000010;
		}

		if ( state.getValue(SOUTH)) {
			index |= 0b000100;
		}

		if (state.getValue(WEST)) {
			index |= 0b001000;
		}

		if (state.getValue(UP)) {
			index |= 0b010000;
		}

		if (state.getValue(DOWN)) {
			index |= 0b100000;
		}

		return index;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, EDGE_MEMBER, NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	public int getMetaFromState(IBlockState state) {
		boolean isEdgeMember = state.getValue(EDGE_MEMBER);
		return (isEdgeMember ? 0 : 1);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(EDGE_MEMBER, (meta & 1) == 1);
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
		if (entity != null)
			return entity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
		else if (world.getBlockState(pos.add(facing.getDirectionVec())).getBlock() instanceof BlockCable)
			return true;

		return false;
	}

	@Override
	public BlockCable setCreativeTab(CreativeTabs tab) {
		return (BlockCable) super.setCreativeTab(tab);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}



}
