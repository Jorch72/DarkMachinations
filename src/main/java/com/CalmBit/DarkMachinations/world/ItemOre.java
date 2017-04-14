package com.CalmBit.DarkMachinations.world;

import com.CalmBit.DarkMachinations.generic.IOreDict;
import com.CalmBit.DarkMachinations.generic.ItemBase;
import net.minecraftforge.oredict.OreDictionary;

public class ItemOre extends ItemBase implements IOreDict {

    protected String oreName;

    public ItemOre(String name, String oreName) {
        super(name);
        this.oreName = oreName;
    }

    @Override
    public void registerOreDict() {
        OreDictionary.registerOre(this.oreName, this);
    }
}
