package com.calmbit.darkmachinations.machine;

import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBloomery extends TileEntityBase {
    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public int getSlotCount() {
        return 0;
    }

    @Override
    public boolean getActive() {
        return false;
    }

    @Override
    public void setActive(boolean value) {

    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public void writeItemData(NBTTagCompound compound) {

    }

    @Override
    public void readItemData(NBTTagCompound compound) {

    }

    @Override
    public IInventory getContainerInventory() {
        return new ValidatedInventoryView((ConcreteItemStorage) this.getItemStackHandler());
    }
}
