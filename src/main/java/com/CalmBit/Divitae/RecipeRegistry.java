package com.CalmBit.Divitae;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeRegistry {

    public static void init()
    {
        // Crafting
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.dust_bronze, 4), new Object[] {"dustCopper", "dustCopper", "dustCopper", "dustTin"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.machine_corner, new Object[] {"PP", "P*", 'P', "plateSteel"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.machine_corner, new Object[] {"P*", "PP", 'P', "plateSteel"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockRegistry.machine_redstone_core, new Object[] {"CPC", "PRP", "CPC", 'C', ItemRegistry.machine_corner, 'P', "plateSteel", 'R', "blockRedstone"}));

        // Smelting
        GameRegistry.addSmelting(BlockRegistry.ore_copper, new ItemStack(ItemRegistry.ingot_copper, 1), 0.7f);
        GameRegistry.addSmelting(BlockRegistry.ore_silver, new ItemStack(ItemRegistry.ingot_silver, 1), 1.0f);
        GameRegistry.addSmelting(BlockRegistry.ore_tin, new ItemStack(ItemRegistry.ingot_tin, 1), 0.7f);

        GameRegistry.addSmelting(ItemRegistry.dust_bronze, new ItemStack(ItemRegistry.ingot_bronze, 1), 1.0f);
        GameRegistry.addSmelting(ItemRegistry.dust_copper, new ItemStack(ItemRegistry.ingot_copper, 1), 0.7f);
        GameRegistry.addSmelting(ItemRegistry.dust_gold, new ItemStack(Items.GOLD_INGOT, 1), 1.0f);
        GameRegistry.addSmelting(ItemRegistry.dust_iron, new ItemStack(Items.IRON_INGOT, 1), 0.7f);
        GameRegistry.addSmelting(ItemRegistry.dust_silver, new ItemStack(ItemRegistry.ingot_silver, 1), 1.0f);
        GameRegistry.addSmelting(ItemRegistry.dust_tin, new ItemStack(ItemRegistry.ingot_tin, 1), 0.7f);


    }
}
