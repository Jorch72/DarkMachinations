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

package com.elytradev.darkmachinations.network;


import com.elytradev.darkmachinations.registry.NetworkRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

public class EnergyNetwork {
	private ArrayList<BlockPos> members;
	private ArrayList<EnergyNetworkNode> nodes;
	private ArrayList<EnergyNetworkEdge> edges;
	private UUID identifier;

	public EnergyNetwork(World worldIn) {
		this.members = new ArrayList<>();
		this.nodes = new ArrayList<>();
		this.edges = new ArrayList<>();
		this.identifier = UUID.randomUUID();
		NetworkRegistry.registerNetwork(worldIn, this);
	}

	public void addNode(World worldIn, BlockPos node, EnergyNetworkNode.NodeType nodeType) {
		if(!worldIn.isRemote) {
			EnergyNetworkNode createdNode = new EnergyNetworkNode(this, node, nodeType);
			if (!nodes.contains(createdNode))
				this.nodes.add(createdNode);
			for (EnergyNetworkNode searchedNode : nodes) {
				if (searchedNode.equals(createdNode))
					continue;
				// TODO: Search and link
			}
		}
	}

	public ArrayList<BlockPos> getMembersList() { return members;}

	public ArrayList<EnergyNetworkNode> getNodesList() { return nodes; }

	public void addMember(BlockPos member) {
		if(!this.members.contains(member))
			this.members.add(member);
	}

	public void removeMember(BlockPos member) {
		if(this.members.contains(member))
			this.members.remove(member);
	}

	public void removeNode(EnergyNetworkNode node) {
		if(this.nodes.contains(node))
			this.nodes.remove(node);

		// TODO: Edges would be de-initialized here, too.
	}

	public boolean hasMember(BlockPos member) {
		return members.contains(member);
	}

	public boolean sameNetwork(EnergyNetwork network) {
		return network.identifier == this.identifier;
	}

	public void mergeNetworks(EnergyNetwork other) {
	}

	public int getSize() {
		return this.members.size() + this.nodes.size();
	}

	public UUID getIdentifier() {
		return identifier;
	}
}
