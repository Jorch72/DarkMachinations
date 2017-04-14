package com.CalmBit.DarkMachinations.generic;

import com.CalmBit.DarkMachinations.DarkMachinations;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IDivitaeBlock {
    protected String name;
    public BlockBase(Material materialIn, String name)
    {
        super(materialIn);

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void registerItemModel(ItemBlock itemBlock)
    {
        DarkMachinations.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }
}
