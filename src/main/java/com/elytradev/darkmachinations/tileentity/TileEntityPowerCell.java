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

import com.elytradev.concrete.inventory.IContainerInventoryHolder;
import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.energy.EnergyCell;
import com.elytradev.darkmachinations.energy.EnergyCellCreative;
import com.elytradev.darkmachinations.energy.EnergyUser;
import com.elytradev.darkmachinations.gui.FakeInventoryView;
import com.elytradev.darkmachinations.probe.ProbeDataProviderGenerator;
import com.elytradev.darkmachinations.probe.ProbeDataProviderMachine;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityPowerCell extends TileEntityBase implements IContainerInventoryHolder {

	public EnergyUser energyStorage;
	public String customName;
	public Object probeDataProvider;
	public boolean isCreative;

	public static final int ENERGY_CAPACITY = 20000;
	public static final int ENERGY_TRANSFER_RATE = 40;

	public static final int FIELD_ENERGY_COUNT = 0;
	public static final int FIELD_ENERGY_CAPACITY = 1;
	private boolean isActive;
	private boolean wasActive;

	public TileEntityPowerCell() {
		this(false);
	}

	public TileEntityPowerCell(boolean creative) {
		isCreative = creative;
		energyStorage = creative ?  new EnergyCellCreative(ENERGY_TRANSFER_RATE) :
				new EnergyCell(ENERGY_CAPACITY, ENERGY_TRANSFER_RATE);
		energyStorage.listen(this::markDirty);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound update = new NBTTagCompound();
		this.writeToNBT(update);
		return update;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == null)
			return false;
		if (capability == DarkMachinations.PROBE_CAPABILITY)
			return true;
		if (capability == CapabilityEnergy.ENERGY)
			return (world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing);
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == null)
			return null;
		if (capability == CapabilityEnergy.ENERGY && (world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing))
			return (T) energyStorage;
		if (capability == DarkMachinations.PROBE_CAPABILITY) {
			if (probeDataProvider == null) {
				probeDataProvider = new ProbeDataProviderMachine();
			}
			return (T)probeDataProvider;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void writeItemData(NBTTagCompound compound) {
		if (!this.isCreative) {
			compound.setInteger("energy_stored", this.energyStorage.getEnergyStored());
		}
	}

	@Override
	public void readItemData(NBTTagCompound compound) {
		if (!this.isCreative && compound.hasKey("energy_stored")) {
			int energyStored = compound.getInteger("energy_stored");

			if (energyStored > ENERGY_CAPACITY)
				energyStored = ENERGY_CAPACITY;

			this.energyStorage.setEnergyStored(energyStored);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (!this.isCreative) {
			energyStorage.writeToNBT(compound);
			compound.setBoolean("isActive", this.isActive);
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (this.isCreative)
			return;
		super.readFromNBT(compound);
		energyStorage.readFromNBT(compound);
		this.isActive = compound.getBoolean("isActive");
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return "darkmachinations:power_cell";
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "darkmachinations.tileentity.power_cell";
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	@Override
	public boolean hasCustomName() {
		return (this.customName != null && !this.customName.isEmpty());
	}

	@Override
	public boolean getActive() {
		return this.isActive;
	}

	@Override
	public void setActive(boolean value) {
		this.isActive = value;
	}

	@Override
	public void setField(int id, int value) {
		switch(id)
		{
			case FIELD_ENERGY_COUNT: {
				this.energyStorage.setEnergyStored(value);
				break;
			}
			case FIELD_ENERGY_CAPACITY: {
				this.energyStorage.setEnergyCapacity(value);
				break;
			}
			default: {
				break;
			}
		}
	}

	@Override
	public int getField(int id) {
		switch(id)
		{
			case FIELD_ENERGY_COUNT: {
				return this.energyStorage.getEnergyStored();
			}
			case FIELD_ENERGY_CAPACITY: {
				return this.energyStorage.getMaxEnergyStored();
			}
			default: {
				return 0;
			}
		}
	}

	@Override
	public int getSlotCount() {
		return 0;
	}

	@Override
	public void update() {
		if (this.energyStorage.getEnergyStored() > 0) {
			ArrayList<IEnergyStorage> receivers = new ArrayList<>();
			for (EnumFacing facing : EnumFacing.VALUES) {
				IEnergyStorage storage = this.getPowerReceiver(facing);
				if (storage != null && storage.canReceive())
					receivers.add(storage);
			}

			if (receivers.size() != 0) {
				int energyRate = Math.min(ENERGY_TRANSFER_RATE / receivers.size(), this.energyStorage.getEnergyStored());
				int energyLeft = Math.min(ENERGY_TRANSFER_RATE, this.energyStorage.getEnergyStored());
				for (IEnergyStorage storage : receivers) {
					if (energyLeft <= 0)
						break;
					energyLeft -= energyStorage.extractEnergy(storage.receiveEnergy(energyRate, false), false);
				}
			}
		}

		if (this.probeDataProvider != null) {
			ProbeDataProviderGenerator probeData = (ProbeDataProviderGenerator)probeDataProvider;
			probeData.updateProbeEnergyData(this.energyStorage.getEnergyStored(), this.energyStorage.getMaxEnergyStored());
		}
	}

	private int checkPowerSide(int energyTransferRate, EnumFacing face) {
		IEnergyStorage energy = getPowerReceiver(face);
		if (energy.getEnergyStored() < energy.getMaxEnergyStored()) {
			int maximumTransfer = Math.min(energyTransferRate, energy.getMaxEnergyStored()-energy.getEnergyStored());
			return energy.receiveEnergy(this.energyStorage.extractEnergy(maximumTransfer, false), false);
		}
		return 0;
	}

	private IEnergyStorage getPowerReceiver(EnumFacing face) {
		BlockPos poweredBlock = this.getPos().add(face.getDirectionVec());
		if (world.getTileEntity(poweredBlock) != null && world.getTileEntity(poweredBlock).hasCapability(CapabilityEnergy.ENERGY, face))
			return world.getTileEntity(poweredBlock).getCapability(CapabilityEnergy.ENERGY, face);
		else
			return null;
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public IInventory getContainerInventory() {
		FakeInventoryView result = new FakeInventoryView(this.getName());

		if (!this.world.isRemote) {
			return result
					.withField(FIELD_ENERGY_COUNT, () -> this.energyStorage.getEnergyStored())
					.withField(FIELD_ENERGY_CAPACITY, () -> this.energyStorage.getMaxEnergyStored());
		}

		return result;
	}
}
