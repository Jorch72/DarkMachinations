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

package com.elytradev.darkmachinations.init;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.block.BlockCable;
import com.elytradev.darkmachinations.tileentity.TileEntityCableNode;
import com.elytradev.darkmachinations.network.EnergyNetwork;
import com.elytradev.darkmachinations.network.EnergyNetworkNode;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class NetworkHandler {

	public static HashMap<UUID, EnergyNetwork> networkList = new HashMap<>();
	private static ArrayList<UUID> toRemove = new ArrayList<>();


	public static void registerNetwork(World worldIn, EnergyNetwork network) {
		if (!worldIn.isRemote) {
			networkList.put(network.getIdentifier(), network);
		}
	}

	public static void removeNetwork(World worldIn, UUID identifier) {
		networkList.remove(identifier);
	}

	public static EnergyNetwork getNetwork(UUID identifier) {
		if (networkList.containsKey(identifier)) {
			return networkList.get(identifier);
		}

		return null;
	}

	public static void networkTick(World worldIn) {
		if (!worldIn.isRemote) {
			for (EnergyNetwork network : networkList.values()) {
				if (network.getNodesList().size() == 0) {
					if (network.getMembersList().size() != 0) {
						for (BlockPos member : network.getMembersList()) {
							NetworkHandler.networkRediscovery(worldIn, network, member);
						}
					}
					toRemove.add(network.getIdentifier());
				}
			}

			for (UUID ident : toRemove) {
				NetworkHandler.removeNetwork(worldIn, ident);
			}

			toRemove.clear();
		}
	}

	public static UUID getNetworkIdentityForBlock(BlockPos pos) {
		for (EnergyNetwork network : networkList.values()) {
			if (network.hasMember(pos)) {
				return network.getIdentifier();
			}
		}
		return null;
	}

	public static EnergyNetwork mergeNetworks(World worldIn, EnergyNetwork one, EnergyNetwork two) {
		// TODO: Refactor this.
		if (!worldIn.isRemote) {
			boolean biggerNetwork = one.getSize() > two.getSize();
			EnergyNetwork biggest = biggerNetwork ? one : two;
			EnergyNetwork smallest = biggerNetwork ? two : one;
			if (biggest.getIdentifier() == smallest.getIdentifier()) {
				DarkMachinations.LOG.error("For some reason, this network (" + biggest.getIdentifier()
						+ ") just tried to merge to itself...");
				return biggest;
			}
			try {
				DarkMachinations.LOG.info("Network merge incoming! (" + biggest.getIdentifier() + ","
						+ smallest.getIdentifier() + ")");
				for (BlockPos member : smallest.getMembersList()) {
					biggest.addMember(member);
				}

				for (EnergyNetworkNode node : smallest.getNodesList()) {
					biggest.addNode(worldIn, node.position, node.nodeType);
					switch (node.nodeType) {
						case ROUTER: {
							break;
						}
						default: {
							TileEntityCableNode cable = (TileEntityCableNode) worldIn.getTileEntity(node.position);
							if (cable == null) {
								DarkMachinations.LOG.error(
										"What the fuck? Why is an ENDPOINT node not carrying a TE? Position: "
												+ node.position);
							} else {
								cable.setNetwork(biggest);
							}
							break;
						}
					}
				}

				smallest.getNodesList().clear();
				smallest.getMembersList().clear();

				DarkMachinations.LOG.info("Network merge complete.");
			} catch (ConcurrentModificationException e) {
				DarkMachinations.LOG.fatal("ConcurrentModificationException thrown merging networks "
						+ biggest.getIdentifier() + ", " + smallest.getIdentifier());
			}
			return biggest;
		}
		return null;
	}

	public static void networkRediscovery(World worldIn, EnergyNetwork network, BlockPos pos) {
		// TODO: Refactor this.

		EnergyNetwork mergingNetwork = network;

		if (!worldIn.isRemote) {
			if (mergingNetwork != null && mergingNetwork.hasMember(pos))
				return;
			if (!(worldIn.getBlockState(pos).getBlock() instanceof BlockCable))
				return;

			LinkedList<BlockPos> toBeChecked = new LinkedList<>();
			ArrayList<BlockPos> alreadyChecked = new ArrayList<>();
			if (mergingNetwork != null) {
				mergingNetwork.addMember(pos);
			}

			toBeChecked.add(pos);

			while (!toBeChecked.isEmpty()) {
				BlockPos checking = toBeChecked.removeFirst();
				alreadyChecked.add(checking);
				for (EnumFacing facing : EnumFacing.VALUES) {
					BlockPos extra = checking.add(facing.getDirectionVec());
					if (mergingNetwork != null && mergingNetwork.hasMember(extra))
						continue;
					if (worldIn.getBlockState(extra).getBlock() instanceof BlockCable) {
						if (worldIn.getTileEntity(extra) != null) {
							if (mergingNetwork == null) {
								mergingNetwork = ((TileEntityCableNode) worldIn.getTileEntity(extra)).getNetwork();
							} else {
								mergingNetwork = mergeNetworks(worldIn, mergingNetwork,
										((TileEntityCableNode) worldIn.getTileEntity(extra)).getNetwork());
							}
						} else {
							if (mergingNetwork != null) {
								mergingNetwork.addMember(extra);
							}
							if (!alreadyChecked.contains(extra)) {
								toBeChecked.add(extra);
							}
						}
					}
				}
			}
			if (mergingNetwork == null) {
				NetworkHandler.networkRediscovery(worldIn, new EnergyNetwork(worldIn), pos);
			}
		}
	}
}