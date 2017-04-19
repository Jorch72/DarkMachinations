package com.CalmBit.DarkMachinations.machine;

import com.CalmBit.DarkMachinations.CompressorRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotCompressorSupply extends SlotItemHandler {
    public SlotCompressorSupply(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return !CompressorRecipes.INSTANCE.getRecipeResult(stack).isEmpty();
    }

    public ItemStack decrementCount()
    {
        if(this.getStack().getCount() < 0)
            return ItemStack.EMPTY;

        ItemStack returnStack = new ItemStack(this.getStack().getItem(), 1, this.getStack().getItemDamage());

        if(this.getStack().getCount() == 1)
            this.putStack(ItemStack.EMPTY);

        this.getStack().setCount(this.getStack().getCount()-1);

        return returnStack  ;
    }
}
