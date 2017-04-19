package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.CrusherRecipes;
import com.CalmBit.DarkMachinations.DarkMachinations;
import com.CalmBit.DarkMachinations.generic.EnergyReciever;
import com.CalmBit.DarkMachinations.generic.EnergyUser;
import net.minecraft.block.BlockHorizontal;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityCrusher extends TileEntityBase {

    public ItemStackHandler itemStackHandler;
    public EnergyUser energyStorage;
    public String customName;

    public ItemStack inCrusher;
    public boolean isActive;
    public boolean wasActive;

    public static final int SLOT_COUNT = 2;
    public static final int ENERGY_CAPACITY = 10000;
    public static final int ENERGY_TRANSFER_RATE = 100;
    public static final int ENERGY_USAGE_RATE = 25;

    public static final int FIELD_ENERGY_COUNT = 0;
    public static final int FIELD_ENERGY_CAPACITY = 1;
    public static final int FIELD_ITEM_PROCESSING_TIME = 2;
    public static final int FIELD_ITEM_PROCESSING_MAX = 3;

    public int itemProcessingTimer;
    public int itemProcessingMaximum = 100;

    public Object probeDataProvider;


    public TileEntityCrusher() {
        itemStackHandler = new ItemStackHandler(SLOT_COUNT);
        energyStorage = new EnergyReciever(ENERGY_CAPACITY, ENERGY_TRANSFER_RATE);
        energyStorage.listen(this::markDirty);
        inCrusher = ItemStack.EMPTY;
    }
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if(capability == CapabilityEnergy.ENERGY)
        {
            return (world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing) && facing != EnumFacing.UP;
        }
        if(capability == DarkMachinations.PROBE_CAPABILITY) {
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
        if(capability == CapabilityEnergy.ENERGY && (world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing) && facing != EnumFacing.UP)
        {
            return (T) energyStorage;
        }
        if(capability == DarkMachinations.PROBE_CAPABILITY) {
            if(capability == DarkMachinations.PROBE_CAPABILITY)
            {
                if(probeDataProvider == null) {
                    probeDataProvider = new ProbeDataProviderMachine();
                    ((ProbeDataProviderMachine)probeDataProvider).setStackHandler(this.itemStackHandler);
                }
                return (T)probeDataProvider;
            }
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

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerCrusher(this, playerInventory);
    }

    @Override
    public String getGuiID() {
        return "darkmachinations:crusher";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "machine.crusher";
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
    public void update() {

        ItemStack supplySlot = itemStackHandler.getStackInSlot(ContainerCrusher.CRUSHER_SUPPLY_SLOT);

        if(!this.world.isRemote) {
            if (this.isActive) {
                if (this.itemProcessingTimer == 0) {
                    this.grindItem();
                } else if (this.energyStorage.getEnergyStored() >= ENERGY_USAGE_RATE) {
                    this.itemProcessingTimer--;
                    this.energyStorage.setEnergyStored(this.energyStorage.getEnergyStored()-ENERGY_USAGE_RATE);
                }
            }

            if (!isActive && this.energyStorage.getEnergyStored() >= ENERGY_USAGE_RATE) {
                if (!supplySlot.isEmpty()) {
                    ItemStack product = CrusherRecipes.INSTANCE.getRecipeResult(supplySlot);
                    if (!product.isEmpty()) {
                        inCrusher = new ItemStack(supplySlot.getItem(), 1, supplySlot.getItemDamage());
                        this.itemStackHandler.setStackInSlot(ContainerCrusher.CRUSHER_SUPPLY_SLOT, new ItemStack(supplySlot.getItem(), supplySlot.getCount() - 1, supplySlot.getItemDamage()));
                        this.itemProcessingTimer = this.itemProcessingMaximum;
                        this.isActive = true;
                    }
                }
            }
        }

        if(this.probeDataProvider != null) {
            ProbeDataProviderMachine probeData = (ProbeDataProviderMachine)probeDataProvider;
            probeData.updateProbeEnergyData(this.energyStorage.getEnergyStored(), this.energyStorage.getMaxEnergyStored());
            probeData.updateProbeProgressData(this.itemProcessingTimer, this.itemProcessingMaximum, this.isActive);
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


    public void grindItem()
    {
        ItemStack product = CrusherRecipes.INSTANCE.getRecipeResult(inCrusher);
        ItemStack productSlot = this.itemStackHandler.getStackInSlot(ContainerCrusher.CRUSHER_PRODUCT_SLOT);

        if (productSlot.isEmpty())
            this.itemStackHandler.setStackInSlot(ContainerCrusher.CRUSHER_PRODUCT_SLOT, new ItemStack(product.getItem(), product.getCount(), product.getItemDamage()));
        else if (productSlot.getItem() == product.getItem() && productSlot.getItemDamage() == product.getItemDamage() && product.getCount() + productSlot.getCount() <= 64)
            this.itemStackHandler.setStackInSlot(ContainerCrusher.CRUSHER_PRODUCT_SLOT, new ItemStack(product.getItem(), product.getCount()+productSlot.getCount(), product.getItemDamage()));
        else
            return;

        this.inCrusher = ItemStack.EMPTY;
        this.isActive = false;
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
    public boolean getActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean value) {
        this.isActive = value;
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }
}