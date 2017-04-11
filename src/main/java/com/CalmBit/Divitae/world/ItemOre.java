package com.CalmBit.Divitae.world;

import com.CalmBit.Divitae.generic.IOreDict;
import com.CalmBit.Divitae.generic.ItemBase;
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
