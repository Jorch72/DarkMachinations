package com.elytradev.darkmachinations.generic;

import net.minecraft.item.ItemBlock;

public interface IDarmaBlock {
	void registerItemModel(ItemBlock itemBlock);
	ItemBlock getItemBlock();
}
