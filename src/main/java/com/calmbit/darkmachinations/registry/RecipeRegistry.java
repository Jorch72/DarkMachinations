package com.calmbit.darkmachinations.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeRegistry {

    public static void init()
    {
        // Crafting
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.machine_corner, new Object[] {"PP", "P*", 'P', "plateSteel"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.machine_corner, new Object[] {"P*", "PP", 'P', "plateSteel"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockRegistry.machine_redstone_core, new Object[] {"CPC", "PRP", "CPC", 'C', ItemRegistry.machine_corner, 'P', "plateSteel", 'R', "blockRedstone"}));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockRegistry.cable_copper_uninsulated, 6), new Object[] {"CCC", 'C', "ingotCopper"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockRegistry.cable_tin_uninsulated, 9), new Object[] {"TTT", 'T', "ingotTin"}));

    }
}
