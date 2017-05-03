package com.calmbit.darkmachinations.registry;

import com.calmbit.darkmachinations.DarkMachinations;
import com.calmbit.darkmachinations.generic.IOreDict;
import com.calmbit.darkmachinations.item.ItemBase;
import com.calmbit.darkmachinations.item.ItemTestRing;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

    public static ItemBase test_ring;

    public static ItemBase machine_corner;

    public static void init()
    {
        machine_corner = registerItem(new ItemBase("machine_corner")).setCreativeTab(DarkMachinations.divitaeTab);
        test_ring = registerItem(new ItemTestRing()).setCreativeTab(DarkMachinations.divitaeTab);
    }

    private static <T extends Item> T registerItem(T item)
    {
        GameRegistry.register(item);

        if(item instanceof ItemBase)
        {
            ((ItemBase)item).registerItemModel();
        }
        if(item instanceof IOreDict)
        {
            ((IOreDict)item).registerOreDict();
        }
        return item;
    }
}
