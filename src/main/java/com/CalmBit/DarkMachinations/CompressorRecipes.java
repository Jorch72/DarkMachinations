package com.CalmBit.DarkMachinations;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Map;

public class CompressorRecipes {
    public static final CompressorRecipes INSTANCE = new CompressorRecipes();

    private final Map<ItemStack, ItemStack> recipeList = Maps.newHashMap();

    public CompressorRecipes()
    {
        this.addRecipe("ingotBronze", "plateBronze");
        this.addRecipe("ingotCopper", "plateCopper");
        this.addRecipe("ingotGold", "plateGold");
        this.addRecipe("ingotIron", "plateIron");
        this.addRecipe("ingotSilver", "plateSilver");
        this.addRecipe("ingotTin", "plateTin");
    }

    public void addRecipe(String supplyDict, String productDict, int qty)
    {
        NonNullList<ItemStack> supplyList = OreDictionary.getOres(supplyDict);
        NonNullList<ItemStack> productList = OreDictionary.getOres(productDict);

        if(supplyList.isEmpty() || productList.isEmpty()) {
            DarkMachinations.LOG.error("Couldn't add a Compressor recipe for conversion of " + supplyDict + " to " + qty + " " + productDict + "(s)! You might be missing one of those...");
            return;
        }

        ItemStack product = productList.get(0);

        for(ItemStack supplyStack : supplyList) {
            if(!this.recipeList.containsKey(supplyStack))
                this.recipeList.put(supplyStack, new ItemStack(product.getItem(), product.getCount(), product.getItemDamage()));
        }
    }

    public void addRecipe(String supplyDict, String productDict) {
        addRecipe(supplyDict, productDict, 1);
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
