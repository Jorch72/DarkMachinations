package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.generic.EnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityGenerator extends TileEntityBase {

    public ItemStackHandler itemStackHandler;
    public EnergyUser energyStorage;
    public String customName;

    public boolean isActive;
    public boolean wasActive;

    public static final int SLOT_COUNT = 1;
    public static final int ENERGY_CAPACITY = 100000;
    public static final int ENERGY_TRANSFER_RATE = 100;
    public static final int ENERGY_GENERATION_RATE = 125;

    public static final int FIELD_ENERGY_COUNT = 0;
    public static final int FIELD_ENERGY_CAPACITY = 1;
    public static final int FIELD_ITEM_PROCESSING_TIME = 2;
    public static final int FIELD_ITEM_PROCESSING_MAX = 3;

    public int itemProcessingTimer;
    public int itemProcessingMaximum = 100;

    public TileEntityGenerator()
    {
        super();
        itemStackHandler = new ItemStackHandler(SLOT_COUNT);
        energyStorage = new EnergyUser(ENERGY_CAPACITY, ENERGY_TRANSFER_RATE);
        this.energyStorage.setEnergyStored(ENERGY_CAPACITY);
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) itemStackHandler;
        }
        if(capability == CapabilityEnergy.ENERGY)
        {
            return (T) energyStorage;
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
                this.energyStorage.receiveEnergy(ENERGY_GENERATION_RATE, false);
            } else {
                if (supplySlot.isEmpty()) {
                    this.isActive = false;
                } else if(this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored())  {
                    consumeFuel();
                    this.itemProcessingTimer = this.itemProcessingMaximum;
                    this.isActive  = true;
                }
            }

            if(this.energyStorage.getEnergyStored() > ENERGY_TRANSFER_RATE)
            {
                checkPowerSide(0, 1, 0, EnumFacing.DOWN);
                checkPowerSide(0, -1, 0, EnumFacing.UP);
                checkPowerSide(1, 0, 0, EnumFacing.WEST);
                checkPowerSide(-1, 0, 0, EnumFacing.EAST);
                checkPowerSide(0, 0, 1, EnumFacing.NORTH);
                checkPowerSide(0, 0, -1, EnumFacing.SOUTH);
            }
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

    private void checkPowerSide(int xAdd, int yAdd, int zAdd, EnumFacing face)
    {
        if (world.getTileEntity(this.getPos().add(xAdd, yAdd, zAdd)) != null && world.getTileEntity(this.getPos().add(xAdd, yAdd, zAdd)).hasCapability(CapabilityEnergy.ENERGY, face)) {
            IEnergyStorage energy = world.getTileEntity(this.getPos().add(xAdd, yAdd, zAdd)).getCapability(CapabilityEnergy.ENERGY, face);
            if(energy.getEnergyStored() < energy.getMaxEnergyStored()) {
                int maximumTransfer = Math.min(ENERGY_TRANSFER_RATE, energy.getMaxEnergyStored()-energy.getEnergyStored());
                world.getTileEntity(this.getPos().add(xAdd, yAdd, zAdd)).getCapability(CapabilityEnergy.ENERGY, face).receiveEnergy(this.energyStorage.extractEnergy(maximumTransfer, false), false);
            }
        }
    }

    private void consumeFuel() {
        ItemStack supplySlot = itemStackHandler.getStackInSlot(ContainerGenerator.GENERATOR_SUPPLY_SLOT);
        this.itemStackHandler.setStackInSlot(ContainerGenerator.GENERATOR_SUPPLY_SLOT, new ItemStack(supplySlot.getItem(), supplySlot.getCount() - 1));
        this.isActive = true;
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
        return "divitae:generator";
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
