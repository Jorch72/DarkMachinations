package com.CalmBit.Divitae.machine;

import com.CalmBit.Divitae.generic.IUsableStrictness;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.IWorldNameable;

public abstract class TileEntityBase extends TileEntity implements IInteractionObject,
        IWorldNameable, IUsableStrictness, ITickable, ITEFieldable, ITEActivatable, ITEStackHandler {

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

}
