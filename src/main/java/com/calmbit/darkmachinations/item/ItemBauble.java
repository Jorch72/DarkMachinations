package com.calmbit.darkmachinations.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemBauble extends ItemBase implements baubles.api.IBauble {

    public ItemBauble(String name) {
        super(name);
    }

    @Override
    @Optional.Method(modid = "baubles")
    public baubles.api.BaubleType getBaubleType(ItemStack arg0) {
        return baubles.api.BaubleType.RING;
    }

}