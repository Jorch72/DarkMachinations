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

package com.elytradev.darkmachinations.tileentity;


import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.block.BlockCableEndpoint;
import com.elytradev.darkmachinations.energy.NetworkedEnergyUser;
import com.elytradev.darkmachinations.network.EnergyNetwork;
import com.elytradev.darkmachinations.network.EnergyNetworkNode;
import com.elytradev.darkmachinations.probe.ProbeDataProviderCable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityCableNode extends TileEntity {

	private EnergyNetwork network;
	private NetworkedEnergyUser energyStorage;
	private ProbeDataProviderCable cableProvider;
	private EnergyNetworkNode.NodeType nodeType;
	private BlockCableEndpoint.BlockCableType cableType = BlockCableEndpoint.BlockCableType.COPPER_INSULATED;

	public void addNodeToNetwork(EnergyNetworkNode.NodeType nodeType) {
		this.nodeType = nodeType;
		this.cableProvider.nodeType = nodeType;
		network.addNode(world, this.getPos(), nodeType);
	}

	public void assignCableType(BlockCableEndpoint.BlockCableType type) {
		this.cableType = type;
		this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
		this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
		this.world.scheduleBlockUpdate(this.pos, this.getBlockType(), 0, 0);
		this.markDirty();
	}

	public BlockCableEndpoint.BlockCableType getCableType() {
		return this.cableType;
	}

	public EnergyNetworkNode.NodeType getNodeType() {
		return this.nodeType;
	}

	@Override
	public void onLoad() {
		this.nodeType = this.nodeType == null ? EnergyNetworkNode.NodeType.BLANK : this.nodeType;
		this.network = new EnergyNetwork(world);
		this.energyStorage = new NetworkedEnergyUser(network);
		this.cableProvider = new ProbeDataProviderCable(this.getNetwork().getIdentifier(), nodeType);
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ||
				capability == DarkMachinations.PROBE_CAPABILITY ||
				super.hasCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.nodeType = EnergyNetworkNode.NodeType.getNodeFromByte(compound.getByte("nodeType"));
		this.assignCableType(BlockCableEndpoint.BlockCableType.valueOf(compound.getString("cableType").toUpperCase()));
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("nodeType", this.nodeType.value);
		compound.setString("cableType", this.getCableType().getName());
		return super.writeToNBT(compound);
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound update = new NBTTagCompound();
		this.writeToNBT(update);
		return new SPacketUpdateTileEntity(this.pos, 1, update);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY)
			return (T)energyStorage;
		if (capability == DarkMachinations.PROBE_CAPABILITY)
			return (T)cableProvider;
		return super.getCapability(capability, facing);
	}



	/*public void mergeNetworks(EnergyNetwork other) {

	}*/

	public EnergyNetwork getNetwork() {
		return network;
	}

	public void setNetwork(EnergyNetwork network) {
		this.network = network;
		this.cableProvider.identifier = network.getIdentifier();
	}
}
