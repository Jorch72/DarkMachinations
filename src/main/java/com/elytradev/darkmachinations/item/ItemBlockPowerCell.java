package com.elytradev.darkmachinations.item;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.block.BlockPowerCell;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerCell extends ItemBlock {
	public ItemBlockPowerCell(BlockPowerCell block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return stack.getMetadata() >= 8 ? "tile.darkmachinations.creative_power_cell" : "tile.darkmachinations.power_dcll";
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
