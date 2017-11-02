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


import net.minecraft.util.math.BlockPos;

public class EnergyNetworkNode {

	public enum NodeType {
		BLANK((byte)0),
		ROUTER((byte)1),
		SENDER((byte)2),
		RECEIVER((byte)3),
		SENDRECV((byte)4);

		public byte value;
		NodeType(byte value) {
			this.value = value;
		}

		public static NodeType getNodeFromByte(byte nodeType) {
			return NodeType.values()[nodeType];
		}
	}

	public BlockPos position;
	public NodeType nodeType;
	public EnergyNetwork network;

	public EnergyNetworkNode(EnergyNetwork network, BlockPos position, NodeType nodeType) {
		this.network = network;
		this.position = position;
		this.nodeType = nodeType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EnergyNetworkNode)
			return this.equals((EnergyNetworkNode)obj);
		return false;
	}

	public boolean equals(EnergyNetworkNode other) {
		return this.position.equals(other.position) && this.nodeType == other.nodeType;
	}
}
