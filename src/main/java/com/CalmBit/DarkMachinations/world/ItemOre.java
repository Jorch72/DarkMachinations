package com.calmbit.darkmachinations.world;

import com.calmbit.darkmachinations.generic.IOreDict;
import com.calmbit.darkmachinations.generic.ItemBase;
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
