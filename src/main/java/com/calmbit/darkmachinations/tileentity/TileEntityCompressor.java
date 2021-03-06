package com.calmbit.darkmachinations.tileentity;

import com.calmbit.darkmachinations.gui.container.ContainerCompressor;
import com.calmbit.darkmachinations.registry.recipes.CompressorRecipes;
import com.calmbit.darkmachinations.DarkMachinations;
import com.calmbit.darkmachinations.energy.EnergyReciever;
import com.calmbit.darkmachinations.energy.EnergyUser;
import com.calmbit.darkmachinations.probe.ProbeDataProviderMachine;
import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import com.elytradev.concrete.inventory.Validators;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
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

public class TileEntityCompressor extends TileEntityBase {

    public ConcreteItemStorage itemStackHandler;
    public EnergyUser energyStorage;
    public Object probeDataProvider;
    public String customName;

    public ItemStack inCompressor;
    public boolean isActive;
    public boolean wasActive;

    public static final int SLOT_COUNT = 2;
    public static final int ENERGY_CAPACITY = 1000;
    public static final int ENERGY_TRANSFER_RATE = 20;
    public static final int ENERGY_USAGE_RATE = 10;

    public static final int FIELD_ENERGY_COUNT = 0;
    public static final int FIELD_ENERGY_CAPACITY = 1;
    public static final int FIELD_ITEM_PROCESSING_TIME = 2;
    public static final int FIELD_ITEM_PROCESSING_MAX = 3;

    public int itemProcessingTimer;
    public int itemProcessingMaximum = 100;

    public TileEntityCompressor()
    {
        itemStackHandler = new ConcreteItemStorage(SLOT_COUNT).withValidators((stack)->!CompressorRecipes.INSTANCE.getRecipeResult(stack).isEmpty(), Validators.NOTHING);
        energyStorage = new EnergyReciever(ENERGY_CAPACITY, ENERGY_TRANSFER_RATE);
        energyStorage.listen(this::markDirty);
        inCompressor = ItemStack.EMPTY;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound update = new NBTTagCompound();
        this.writeToNBT(update);
        return update;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == null)
            return false;

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == DarkMachinations.PROBE_CAPABILITY) {
            return true;
        }
        if(capability == CapabilityEnergy.ENERGY)
        {
            return (world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing);
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == null)
            return null;

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) itemStackHandler;
        }
        if(capability == CapabilityEnergy.ENERGY && (world.getBlockState(pos).getValue(BlockHorizontal.FACING) != facing))
        {
            return (T) energyStorage;
        }
        if(capability == DarkMachinations.PROBE_CAPABILITY)
        {
            if(probeDataProvider == null) {
                probeDataProvider = new ProbeDataProviderMachine();
                ((ProbeDataProviderMachine)probeDataProvider).setStackHandler(this.itemStackHandler);
            }
            return (T)probeDataProvider;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", itemStackHandler.serializeNBT());
        compound.setTag("inCompressor", inCompressor.serializeNBT());
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
        inCompressor = new ItemStack(compound.getCompoundTag("inCompressor"));
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
        ItemStack supplySlot = itemStackHandler.getStackInSlot(StandardMachineSlots.INPUT);

        if(!this.world.isRemote) {
            if (this.isActive) {
                if (this.itemProcessingTimer == 0) {
                    this.compressItem();
                } else if (this.energyStorage.getEnergyStored() >= ENERGY_USAGE_RATE) {
                    this.itemProcessingTimer--;
                    this.energyStorage.setEnergyStored(this.energyStorage.getEnergyStored()-ENERGY_USAGE_RATE);
                }
            }

            if (!isActive && this.energyStorage.getEnergyStored() >= ENERGY_USAGE_RATE) {
                if (!supplySlot.isEmpty()) {
                    ItemStack product = CompressorRecipes.INSTANCE.getRecipeResult(supplySlot);
                    if (!product.isEmpty()) {
                        inCompressor = supplySlot.copy();
                        inCompressor.setCount(1);
                        ItemStack supplyDecrement = supplySlot.copy();
                        supplyDecrement.setCount(supplySlot.getCount()-1);
                        this.itemStackHandler.setStackInSlot(StandardMachineSlots.INPUT, supplyDecrement);
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

    public void compressItem()
    {
        ItemStack product = CompressorRecipes.INSTANCE.getRecipeResult(inCompressor);
        ItemStack productSlot = this.itemStackHandler.getStackInSlot(StandardMachineSlots.OUTPUT);

        if (productSlot.isEmpty())
            this.itemStackHandler.setStackInSlot(StandardMachineSlots.OUTPUT, product.copy());
        else if (productSlot.getItem() == product.getItem() && productSlot.getItemDamage() == product.getItemDamage() &&  product.getCount() + productSlot.getCount() <= 64) {
            ItemStack adjustedQtyProduct = product.copy();
            adjustedQtyProduct.setCount(product.getCount() + productSlot.getCount());
            this.itemStackHandler.setStackInSlot(StandardMachineSlots.OUTPUT, adjustedQtyProduct);
        }
        else
            return;

        this.inCompressor = ItemStack.EMPTY;
        this.isActive = false;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isActive(TileEntityCompressor inventory) {
        return inventory.getField(FIELD_ITEM_PROCESSING_TIME) > 0;
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
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerCompressor(this, playerInventory, this.getContainerInventory());
    }

    @Override
    public String getGuiID() {
        return "darkmachinations:compressor";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "tileentity.compressor";
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

    @Override
    public void writeItemData(NBTTagCompound compound) {
        compound.setInteger("energy_stored", this.energyStorage.getEnergyStored());
    }

    @Override
    public void readItemData(NBTTagCompound compound) {
        if(compound.hasKey("energy_stored")) {
            int energyStored = compound.getInteger("energy_stored");

            if(energyStored > ENERGY_CAPACITY)
                energyStored = ENERGY_CAPACITY;

            this.energyStorage.setEnergyStored(energyStored);
        }
    }

    @Override
    public IInventory getContainerInventory() {
        ValidatedInventoryView result = new ValidatedInventoryView(itemStackHandler);

        if(!this.world.isRemote)
            return result
                    .withField(FIELD_ENERGY_COUNT, ()->this.energyStorage.getEnergyStored())
                    .withField(FIELD_ENERGY_CAPACITY, ()->this.energyStorage.getMaxEnergyStored())
                    .withField(FIELD_ITEM_PROCESSING_TIME, ()->this.itemProcessingTimer)
                    .withField(FIELD_ITEM_PROCESSING_MAX, ()->this.itemProcessingMaximum);

        return result;
    }
}
