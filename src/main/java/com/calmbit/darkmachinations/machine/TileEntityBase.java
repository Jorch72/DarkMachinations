package com.calmbit.darkmachinations.machine;

import com.calmbit.darkmachinations.generic.IUsableStrictness;
import com.elytradev.concrete.inventory.IContainerInventoryHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.IWorldNameable;

public abstract class TileEntityBase extends TileEntity implements IInteractionObject,
        IWorldNameable, IUsableStrictness, ITickable, ITEFieldable, ITEActivatable, ITEStackHandler, IContainerInventoryHolder {

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        if (this.world == null)
        {
            return true;
        }

        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }

        return player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64D;
    }


    public abstract void writeItemData(NBTTagCompound compound);
    public abstract void readItemData(NBTTagCompound compound);
}
