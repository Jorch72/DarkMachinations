package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.generic.IDarmaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IDarmaBlock {
	protected String name;
	public BlockBase(Material materialIn, String name)
	{
		super(materialIn);

		this.name = name;

		setUnlocalizedName("darkmachinations."+name);
		setRegistryName(name);
	}

	@Override
	public ItemBlock getItemBlock() {
		return (ItemBlock)new ItemBlock(this).setRegistryName(this.getRegistryName());
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
