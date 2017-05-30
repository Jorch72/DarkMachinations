package com.calmbit.darkmachinations.tileentity;

import com.calmbit.darkmachinations.DarkMachinations;
import com.calmbit.darkmachinations.gui.container.ContainerGenerator;
import com.calmbit.darkmachinations.gui.container.ContainerPump;
import com.calmbit.darkmachinations.probe.ProbeDataProviderPump;
import com.calmbit.darkmachinations.registry.FluidRegistry;
import com.calmbit.darkmachinations.energy.EnergyUser;
import com.calmbit.darkmachinations.fluid.FluidBuffer;
import com.calmbit.darkmachinations.probe.ProbeDataProviderGenerator;
import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import com.elytradev.concrete.inventory.Validators;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityPump extends TileEntityBase {

    public ConcreteItemStorage itemStackHandler;
    public FluidBuffer fluidTank;
    public EnergyUser energyStorage;
    public Object probeDataProvider;
    public String customName;

    public boolean isActive;
    public boolean wasActive;


    public static final int SLOT_COUNT = 1;
    public static final int ENERGY_CAPACITY = 10000;
    public static final int ENERGY_TRANSFER_RATE = 100;

    public static final int FLUID_CAPACITY = 10000;
    public static final int FIELD_ENERGY_COUNT = 0;
    public static final int FIELD_ENERGY_CAPACITY = 1;
    public static final int FIELD_PUMP_TIMER = 2;
    public static final int FIELD_PUMP_TIMER_MAX = 3;
    public static final int FIELD_FLUID_LEVEL = 4;
    public static final int FIELD_FLUID_CAPACITY = 5;

    public int pumpTimer;
    public int pumpTimerMaximum;

    public TileEntityPump() {
        itemStackHandler = new ConcreteItemStorage(SLOT_COUNT).withValidators((stack) -> stack.getItem() instanceof ItemBlock);
        fluidTank = new FluidBuffer(FLUID_CAPACITY);
        //fluidTank.setFluid(new FluidStack(FluidRegistry.fluid_heavy_crude_oil, 1000));
        fluidTank.listen(this::markDirty);
        energyStorage = new EnergyUser(ENERGY_CAPACITY, ENERGY_TRANSFER_RATE);
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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == DarkMachinations.PROBE_CAPABILITY
                || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
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
                probeDataProvider = new ProbeDataProviderPump();
                ((ProbeDataProviderPump)probeDataProvider).setItemStackHandler(itemStackHandler);
            }
            return (T)probeDataProvider;
        }
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T)fluidTank;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", itemStackHandler.serializeNBT());
        fluidTank.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        compound.setBoolean("isActive", this.isActive);
        compound.setInteger("pumpTimer", this.pumpTimer);
        compound.setInteger("pumpTimerMax", this.pumpTimerMaximum);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        fluidTank.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
        this.isActive = compound.getBoolean("isActive");
        this.pumpTimer = compound.getInteger("pumpTimer");
        this.pumpTimerMaximum = compound.getInteger("pumpTimerMax");
    }

    public int getField(int id)
    {
        switch(id)
        {
            case FIELD_ENERGY_COUNT:
                return this.energyStorage.getEnergyStored();
            case FIELD_ENERGY_CAPACITY:
                return this.energyStorage.getMaxEnergyStored();
            case FIELD_PUMP_TIMER:
                return this.pumpTimer;
            case FIELD_PUMP_TIMER_MAX:
                return this.pumpTimerMaximum;
            case FIELD_FLUID_LEVEL:
                return this.fluidTank.getFluidAmount();
            case FIELD_FLUID_CAPACITY:
                return this.fluidTank.getCapacity();
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
            case FIELD_PUMP_TIMER:
                this.pumpTimer = value;
                break;
            case FIELD_PUMP_TIMER_MAX:
                this.pumpTimerMaximum = value;
                break;
            case FIELD_FLUID_LEVEL:
                if(this.fluidTank.getFluid() != null)
                    this.fluidTank.getFluid().amount = value;
                break;
            case FIELD_FLUID_CAPACITY:
                this.fluidTank.setCapacity(value);
            default:
                break;
        }
    }
    @Override
    public void update() {
        ItemStack supplySlot = itemStackHandler.getStackInSlot(StandardMachineSlots.INPUT);

        if(this.probeDataProvider != null) {
            ProbeDataProviderPump probeData = (ProbeDataProviderPump) probeDataProvider;
            probeData.updateProbeEnergyData(this.energyStorage.getEnergyStored(), this.energyStorage.getMaxEnergyStored());
            probeData.updateProbeFuelData(this.pumpTimer, this.pumpTimerMaximum, this.getActive());
            probeData.updateProbeFluidTank(this.fluidTank);
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

    private void pumpFluid() {

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
        return new ContainerPump(this, playerInventory, this.getContainerInventory());
    }

    @Override
    public String getGuiID() {
        return "darkmachinations:pump";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "tileentity.pump";
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
        NBTTagCompound fluid = new NBTTagCompound();
        fluidTank.writeToNBT(fluid);
        compound.setTag("fluid_stored", fluid);
    }

    @Override
    public void readItemData(NBTTagCompound compound) {
        if(compound.hasKey("energy_stored")) {
            int energyStored = compound.getInteger("energy_stored");

            if(energyStored > ENERGY_CAPACITY)
                energyStored = ENERGY_CAPACITY;

            this.energyStorage.setEnergyStored(energyStored);
        }

        if(compound.hasKey("fluid_stored")) {
            this.fluidTank.readFromNBT(compound.getCompoundTag("fluid_stored"));
        }
    }

    @Override
    public IInventory getContainerInventory() {
        ValidatedInventoryView result = new ValidatedInventoryView(itemStackHandler);

        if(!this.world.isRemote)
            return result
                    .withField(FIELD_ENERGY_COUNT, ()->this.energyStorage.getEnergyStored())
                    .withField(FIELD_ENERGY_CAPACITY, ()->this.energyStorage.getMaxEnergyStored())
                    .withField(FIELD_PUMP_TIMER, ()->this.FIELD_PUMP_TIMER)
                    .withField(FIELD_PUMP_TIMER_MAX, ()->this.FIELD_PUMP_TIMER_MAX);

        return result;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
