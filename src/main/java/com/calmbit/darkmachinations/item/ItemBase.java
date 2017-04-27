package com.calmbit.darkmachinations.item;

import com.calmbit.darkmachinations.DarkMachinations;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name)
    {
        this.name = name;
        setUnlocalizedName("darkmachinations."+name);
        setRegistryName(name);
    }

    public void registerItemModel()
    {
        DarkMachinations.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemBase setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }
}
