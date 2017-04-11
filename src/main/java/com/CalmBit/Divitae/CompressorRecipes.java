package com.CalmBit.Divitae;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class CompressorRecipes {
    public static final CompressorRecipes INSTANCE = new CompressorRecipes();

    private final Map<ItemStack, ItemStack> recipeList = Maps.newHashMap();

    public CompressorRecipes()
    {
        this.addRecipe(ItemRegistry.ingot_bronze, ItemRegistry.plate_bronze);
        this.addRecipe(ItemRegistry.ingot_copper, ItemRegistry.plate_copper);
        this.addRecipe(Items.GOLD_INGOT, ItemRegistry.plate_gold);
        this.addRecipe(Items.IRON_INGOT, ItemRegistry.plate_iron);
        this.addRecipe(ItemRegistry.ingot_silver, ItemRegistry.plate_silver);
        this.addRecipe(ItemRegistry.ingot_tin, ItemRegistry.plate_tin);
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
