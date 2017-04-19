package com.calmbit.darkmachinations.world;

import com.calmbit.darkmachinations.generic.BlockBase;
import com.calmbit.darkmachinations.generic.IOreDict;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOre extends BlockBase implements IOreDict {

    protected String oreName;

    public BlockOre(String name, String oreName, float hardness, float resistance, int harvestLevel)
    {
        super(Material.ROCK, name);
        this.oreName = oreName;
        setHardness(hardness);
        setResistance(resistance);
        setHarvestLevel("pickaxe", harvestLevel);
    }

    public BlockOre(String name, String oreName)
    {
        this(name, oreName, 3f, 5f, 1);
    }

    @Override
    public BlockOre setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public void registerOreDict() {
        OreDictionary.registerOre(this.oreName, this);
    }
}
