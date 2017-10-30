package com.elytradev.darkmachinations.registry.recipes;

import com.elytradev.darkmachinations.DarkMachinations;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Map;

public class CrusherRecipes {
	public static final CrusherRecipes INSTANCE = new CrusherRecipes();

	private final Map<Ingredient, ItemStack> recipeList = Maps.newHashMap();

	public CrusherRecipes()
	{
		this.addRecipe("ingotBronze", "dustBronze");
		this.addRecipe("ingotCopper", "dustCopper");
		this.addRecipe("ingotGold", "dustGold");
		this.addRecipe("ingotIron", "dustIron");
		this.addRecipe("ingotSilver", "dustSilver");
		this.addRecipe("ingotTin", "dustTin");

		this.addRecipe("oreCopper", "dustCopper", 2);
		this.addRecipe("oreGold", "dustGold", 2);
		this.addRecipe("oreIron", "dustIron", 2);
		this.addRecipe("oreSilver", "dustSilver", 2);
		this.addRecipe("oreTin", "dustTin", 2);
	}

	public void addRecipe(String supplyDict, String productDict, int qty) {
		NonNullList<ItemStack> supplyList = OreDictionary.getOres(supplyDict);
		NonNullList<ItemStack> productList = OreDictionary.getOres(productDict);

		if (supplyList.isEmpty() || productList.isEmpty()) {
			DarkMachinations.LOG.error("Couldn't add a Crusher recipe for conversion of " + supplyDict + " to " + qty + " " + productDict + "(s)! You might be missing one of those...");
			return;
		}

		ItemStack product = productList.get(0);

		this.recipeList.put(new OreIngredient(supplyDict), product.copy());
	}

	public void addRecipe(String supplyDict, String productDict) {
		addRecipe(supplyDict, productDict, 1);
	}

	public ItemStack getRecipeResult(ItemStack supply) {
		for (Map.Entry<Ingredient, ItemStack> entry : recipeList.entrySet()) {
			if (entry.getKey().apply(supply)) {
				return entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}
}
