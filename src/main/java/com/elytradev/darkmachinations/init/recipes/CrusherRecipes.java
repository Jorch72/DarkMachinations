/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017:
 *     Ethan Brooks (CalmBit),
 *     and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.elytradev.darkmachinations.init.recipes;

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
		this.addRecipe("ingotElectrum", "dustElectrum");
		this.addRecipe("ingotInvar", "dustInvar");
		this.addRecipe("ingotSteel", "dustSteel");
		this.addRecipe("ingotBronze", "dustBronze");
		this.addRecipe("ingotBrass","dustBrass");

		this.addRecipe("ingotCopper", "dustCopper");
		this.addRecipe("ingotTin", "dustTin");
		this.addRecipe("ingotLead", "dustLead");
		this.addRecipe("ingotSilver", "dustSilver");
		this.addRecipe("ingotNickel", "dustNickel");
		this.addRecipe("ingotPlatinum","dustPlatinum");
		this.addRecipe("ingotMithril", "dustMithril");
		this.addRecipe("ingotZinc", "dustZinc");
		this.addRecipe("ingotIron", "dustIron");
		this.addRecipe("ingotGold", "dustGold");

		this.addRecipe("oreCopper", "dustCopper", 2);
		this.addRecipe("oreTin", "dustTin", 2);
		this.addRecipe("oreLead", "dustLead", 2);
		this.addRecipe("oreSilver", "dustSilver", 2);
		this.addRecipe("oreNickel", "dustNickel", 2);
		this.addRecipe("orePlatinum","dustPlatinum", 2);
		this.addRecipe("oreMithril", "dustMithril", 2);
		this.addRecipe("oreZinc", "dustZinc", 2);
		this.addRecipe("oreIron", "dustIron", 2);
		this.addRecipe("oreGold", "dustGold", 2);
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
