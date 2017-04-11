package com.CalmBit.Divitae.machine;

import com.CalmBit.Divitae.GrinderRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotGrinderSupply extends SlotItemHandler {
    public SlotGrinderSupply(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return !GrinderRecipes.INSTANCE.getRecipeResult(stack).isEmpty();
    }

    public ItemStack decrementCount()
    {
        if(this.getStack().getCount() < 0)
            return ItemStack.EMPTY;

        ItemStack returnStack = new ItemStack(this.getStack().getItem(), 1);

        if(this.getStack().getCount() == 1)
            this.putStack(ItemStack.EMPTY);

        this.getStack().setCount(this.getStack().getCount()-1);

        return returnStack  ;
    }
}
