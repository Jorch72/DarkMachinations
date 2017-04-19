package com.calmbit.darkmachinations.machine;

import com.calmbit.darkmachinations.CrusherRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotCrusherSupply extends SlotItemHandler {
    public SlotCrusherSupply(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return !CrusherRecipes.INSTANCE.getRecipeResult(stack).isEmpty();
    }

    public ItemStack decrementCount()
    {
        if(this.getStack().getCount() < 0)
            return ItemStack.EMPTY;

        ItemStack returnStack = this.getStack().copy();
        returnStack.setCount(1);

        if(this.getStack().getCount() == 1)
            this.putStack(ItemStack.EMPTY);

        this.getStack().setCount(this.getStack().getCount()-1);

        return returnStack  ;
    }
}
