package com.CalmBit.DarkMachinations;

import com.CalmBit.DarkMachinations.generic.IOreDict;
import com.CalmBit.DarkMachinations.generic.ItemBase;
import com.CalmBit.DarkMachinations.world.ItemOre;
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
        ingot_bronze  = registerItem(new ItemOre("ingot_bronze", "ingotBronze").setCreativeTab(DarkMachinations.divitaeTab));
        ingot_copper = registerItem(new ItemOre("ingot_copper", "ingotCopper").setCreativeTab(DarkMachinations.divitaeTab));
        ingot_silver = registerItem(new ItemOre("ingot_silver", "ingotSilver").setCreativeTab(DarkMachinations.divitaeTab));
        ingot_steel = registerItem(new ItemOre("ingot_steel", "ingotSteel").setCreativeTab(DarkMachinations.divitaeTab));
        ingot_tin  = registerItem(new ItemOre("ingot_tin", "ingotTin").setCreativeTab(DarkMachinations.divitaeTab));


        dust_bronze  = registerItem(new ItemOre("dust_bronze", "dustBronze").setCreativeTab(DarkMachinations.divitaeTab));
        dust_copper  = registerItem(new ItemOre("dust_copper", "dustCopper").setCreativeTab(DarkMachinations.divitaeTab));
        dust_gold  = registerItem(new ItemOre("dust_gold", "dustGold").setCreativeTab(DarkMachinations.divitaeTab));
        dust_iron  = registerItem(new ItemOre("dust_iron", "dustIron").setCreativeTab(DarkMachinations.divitaeTab));
        dust_silver  = registerItem(new ItemOre("dust_silver", "dustSilver").setCreativeTab(DarkMachinations.divitaeTab));
        dust_steel = registerItem(new ItemOre("dust_steel", "dustSteel").setCreativeTab(DarkMachinations.divitaeTab));
        dust_tin  = registerItem(new ItemOre("dust_tin", "dustTin").setCreativeTab(DarkMachinations.divitaeTab));

        plate_bronze  = registerItem(new ItemOre("plate_bronze", "plateBronze").setCreativeTab(DarkMachinations.divitaeTab));
        plate_copper  = registerItem(new ItemOre("plate_copper", "plateCopper").setCreativeTab(DarkMachinations.divitaeTab));
        plate_gold  = registerItem(new ItemOre("plate_gold", "plateGold").setCreativeTab(DarkMachinations.divitaeTab));
        plate_iron  = registerItem(new ItemOre("plate_iron", "plateIron").setCreativeTab(DarkMachinations.divitaeTab));
        plate_silver  = registerItem(new ItemOre("plate_silver", "plateSilver").setCreativeTab(DarkMachinations.divitaeTab));
        plate_steel = registerItem(new ItemOre("plate_steel", "plateSteel").setCreativeTab(DarkMachinations.divitaeTab));
        plate_tin  = registerItem(new ItemOre("plate_tin", "plateTin").setCreativeTab(DarkMachinations.divitaeTab));

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
