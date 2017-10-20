package com.elytradev.darkmachinations.item;

import com.elytradev.darkmachinations.generic.IOreDict;
import net.minecraftforge.oredict.OreDictionary;

public class ItemOre extends ItemBase implements IOreDict {

	private String oreName;

	public ItemOre(String name, String oreName) {
		super(name);
		this.oreName = oreName;
	}

	@Override
	public void registerOreDict() {
		OreDictionary.registerOre(this.oreName, this);
	}
}
