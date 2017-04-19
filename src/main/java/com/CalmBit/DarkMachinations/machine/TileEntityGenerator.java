package com.calmbit.darkmachinations.machine;

import com.calmbit.darkmachinations.DarkMachinations;
import com.calmbit.darkmachinations.generic.EnergyProvider;
import com.calmbit.darkmachinations.generic.EnergyUser;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityGenerator extends TileEntityBase {

    public ItemStackHandler itemStackHandler;
    public EnergyUser energyStorage;
    public Object probeDataProvider;
    public String customName;

    public boolean isActive;
    public boolean wasActive;

    public static final int SLOT_COUNT = 1;
    public static final int ENERGY_CAPACITY = 10000;
    public static final int ENERGY_TRANSFER_RATE = 100;
    public static final int ENERGY_GENERATION_RATE = 125;

    public static final int FIELD_ENERGY_COUNT = 0;
    public static final int FIELD_ENERGY_CAPACITY = 1;
    public static final int FIELD_ITEM_PROCESSING_TIME = 2;
    public static final int FIELD_ITEM_PROCESSING_MAX = 3;

    public int itemProcessingTimer;
    public int itemProcessingMaximum;

    public TileEntityGenerator() {
        itemStackHandler = new ItemStackHandler(SLOT_COUNT);
        energyStorage = new EnergyProvider(ENERGY_CAPACITY, ENERGY_TRANSFER_RATE);
        energyStorage.listen(this::markDirty);
    }
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if(capability == DarkMachinations.PROBE_CAPABILITY) {
            return true;
        }
        if(capability == CapabilityEnergy.ENERGY) {
            return world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) itemStackHandler;
        }
        if(capability == CapabilityEnergy.ENERGY && world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing)
        {
            return (T) energyStorage;
        }
        if(capability == DarkMachinations.PROBE_CAPABILITY)
        {
            if(probeDataProvider == null) {
                probeDataProvider = new ProbeDataProviderGenerator();
                ((ProbeDataProviderGenerator)probeDataProvider).setItemStackHandler(itemStackHandler);
            }
            return (T)probeDataProvider;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", itemStackHandler.serializeNBT());
        energyStorage.writeToNBT(compound);
        compound.setBoolean("isActive", this.isActive);
        compound.setInteger("itemProcessingTimer", this.itemProcessingTimer);
        compound.setInteger("itemProcessingMaximum", this.itemProcessingMaximum);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        energyStorage.readFromNBT(compound);
        this.isActive = compound.getBoolean("isActive");
        this.itemProcessingTimer = compound.getInteger("itemProcessingTimer");
        this.itemProcessingMaximum = compound.getInteger("itemProcessingMaximum");
    }

    public int getField(int id)
    {
        switch(id)
        {
            case FIELD_ENERGY_COUNT:
                return this.energyStorage.getEnergyStored();
            case FIELD_ENERGY_CAPACITY:
                return this.energyStorage.getMaxEnergyStored();
            case FIELD_ITEM_PROCESSING_TIME:
                return this.itemProcessingTimer;
            case FIELD_ITEM_PROCESSING_MAX:
                return this.itemProcessingMaximum;
            default:
                return 0;
        }
    }

    @Override
    public int getSlotCount() {
        return SLOT_COUNT;
    }

    public void setField(int id, int value)
    {
        switch(id)
        {
            case FIELD_ENERGY_COUNT:
                this.energyStorage.setEnergyStored(value);
                break;
            case FIELD_ENERGY_CAPACITY:
                this.energyStorage.setEnergyCapacity(value);
            case FIELD_ITEM_PROCESSING_TIME:
                this.itemProcessingTimer = value;
                break;
            case FIELD_ITEM_PROCESSING_MAX:
                this.itemProcessingMaximum = value;
                break;
            default:
                break;
        }
    }
    @Override
    public void update() {
        ItemStack supplySlot = itemStackHandler.getStackInSlot(ContainerGenerator.GENERATOR_SUPPLY_SLOT);

        if (!this.world.isRemote) {
            if (this.itemProcessingTimer != 0) {
                this.itemProcessingTimer--;
                this.energyStorage.setEnergyStored(Math.min(this.energyStorage.getEnergyStored()+ENERGY_GENERATION_RATE, this.energyStorage.getMaxEnergyStored()));
            } else {
                if (supplySlot.isEmpty()) {
                    this.isActive = false;
                } else if(this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored())  {
                    this.itemProcessingTimer = this.itemProcessingMaximum = consumeFuel();
                    this.isActive  = true;
                }
            }

            if(this.energyStorage.getEnergyStored() > 0)
            {
                ArrayList<IEnergyStorage> receivers = new ArrayList<>();
                for(EnumFacing facing : EnumFacing.VALUES)
                {
                    IEnergyStorage storage = this.getPowerReceiver(facing);
                    if(storage != null && storage.canReceive())
                        receivers.add(storage);
                }

                if(receivers.size() != 0) {
                    int energyRate = Math.min(ENERGY_TRANSFER_RATE / receivers.size(), this.energyStorage.getEnergyStored());
                    int energyLeft = Math.min(ENERGY_TRANSFER_RATE, this.energyStorage.getEnergyStored());
                    for (IEnergyStorage storage : receivers) {
                        if(energyLeft <= 0)
                            break;
                        energyLeft -= energyStorage.extractEnergy(storage.receiveEnergy(energyRate, false), false);
                    }
                }
            }
        }

        if(this.probeDataProvider != null) {
            ProbeDataProviderGenerator probeData = (ProbeDataProviderGenerator)probeDataProvider;
            probeData.updateProbeEnergyData(this.energyStorage.getEnergyStored(), this.energyStorage.getMaxEnergyStored());
            probeData.updateProbeFuelData(this.itemProcessingTimer, this.itemProcessingMaximum, this.isActive);
        }

        if(wasActive != isActive) {
            wasActive = isActive;
            this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
            this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
            this.world.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
            this.world.scheduleBlockUpdate(this.pos, this.getBlockType(), 0, 0);
            this.markDirty();
        }
    }

    private int checkPowerSide(int energyTransferRate, EnumFacing face)
    {
        IEnergyStorage energy = getPowerReceiver(face);
        if(energy.getEnergyStored() < energy.getMaxEnergyStored()) {
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

    private int consumeFuel() {
        ItemStack supplySlot = itemStackHandler.getStackInSlot(ContainerGenerator.GENERATOR_SUPPLY_SLOT);
        int fuelValue = TileEntityFurnace.getItemBurnTime(supplySlot);
        supplySlot.setCount(supplySlot.getCount()-1);
        this.isActive = true;
        return fuelValue;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound update = new NBTTagCompound();
        this.writeToNBT(update);
        return new SPacketUpdateTileEntity(this.pos, 1, update);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerGenerator(this, playerInventory);
    }

    @Override
    public String getGuiID() {
        return "darkmachinations:generator";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "machine.generator";
    }

    public void setCustomName(String name)
    {
        this.customName = name;
    }

    @Override
    public boolean hasCustomName() {
        return !this.customName.isEmpty();
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
    public ItemStackHandler getItemStackHandler() {
        return this.itemStackHandler;
    }
}
