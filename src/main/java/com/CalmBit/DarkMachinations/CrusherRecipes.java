package com.CalmBit.DarkMachinations;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class CrusherRecipes {
    public static final CrusherRecipes INSTANCE = new CrusherRecipes();

    private final Map<ItemStack, ItemStack> recipeList = Maps.newHashMap();

    public CrusherRecipes()
    {
        this.addRecipe(ItemRegistry.ingot_bronze, ItemRegistry.dust_bronze);
        this.addRecipe(ItemRegistry.ingot_copper, ItemRegistry.dust_copper);
        this.addRecipe(Items.GOLD_INGOT, ItemRegistry.dust_gold);
        this.addRecipe(Items.IRON_INGOT, ItemRegistry.dust_iron);
        this.addRecipe(ItemRegistry.ingot_silver, ItemRegistry.dust_silver);
        this.addRecipe(ItemRegistry.ingot_tin, ItemRegistry.dust_iron);

        this.addRecipe(BlockRegistry.ore_copper, new ItemStack(ItemRegistry.dust_copper, 2));
        this.addRecipe(Blocks.GOLD_ORE, new ItemStack(ItemRegistry.dust_gold, 2));
        this.addRecipe(Blocks.IRON_ORE, new ItemStack(ItemRegistry.dust_iron, 2));
        this.addRecipe(BlockRegistry.ore_silver, new ItemStack(ItemRegistry.dust_silver, 2));
        this.addRecipe(BlockRegistry.ore_tin, new ItemStack(ItemRegistry.dust_tin, 2));
    }

    public void addRecipe(Block supply, ItemStack product)
    {
        this.recipeList.put(new ItemStack(supply, 1), product);
    }

    public void addRecipe(Item supply, ItemStack product)
    {
        this.recipeList.put(new ItemStack(supply, 1), product);
    }

    public void addRecipe(Item supply, Item product)
    {
        this.recipeList.put(new ItemStack(supply, 1), new ItemStack(product, 1));
    }

    public void addRecipe(Block supply, Item product)
    {
        this.recipeList.put(new ItemStack(supply, 1), new ItemStack(product, 1));
    }

    public ItemStack getRecipeResult(ItemStack supply)
    {
        for(Map.Entry<ItemStack, ItemStack> entry : recipeList.entrySet())
        {
            if(entry.getKey().getItem() ==  supply.getItem())
            {
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }
}
