package com.calmbit.darkmachinations.registry;

import com.calmbit.darkmachinations.DarkMachinations;
import com.calmbit.darkmachinations.generic.IOreDict;
import com.calmbit.darkmachinations.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

    public static ItemBase ingot_bronze;
    public static ItemBase ingot_copper;
    public static ItemBase ingot_silver;
    public static ItemBase ingot_steel;
    public static ItemBase ingot_tin;


    public static ItemBase dust_bronze;
    public static ItemBase dust_copper;
    public static ItemBase dust_gold;
    public static ItemBase dust_iron;
    public static ItemBase dust_silver;
    public static ItemBase dust_steel;
    public static ItemBase dust_tin;

    public static ItemBase plate_bronze;
    public static ItemBase plate_copper;
    public static ItemBase plate_gold;
    public static ItemBase plate_iron;
    public static ItemBase plate_silver;
    public static ItemBase plate_steel;
    public static ItemBase plate_tin;

    public static ItemBase machine_corner;

    public static void init()
    {
        machine_corner = registerItem(new ItemBase("machine_corner")).setCreativeTab(DarkMachinations.divitaeTab);
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
