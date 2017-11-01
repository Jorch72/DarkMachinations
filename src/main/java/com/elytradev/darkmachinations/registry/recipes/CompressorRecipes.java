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

package com.elytradev.darkmachinations.registry.recipes;

import com.elytradev.darkmachinations.DarkMachinations;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Map;

public class CompressorRecipes {
	public static final CompressorRecipes INSTANCE = new CompressorRecipes();

	private final Map<Ingredient, ItemStack> recipeList = Maps.newHashMap();

	public CompressorRecipes() {
		this.addRecipe("ingotBronze", "plateBronze");
		this.addRecipe("ingotCopper", "plateCopper");
		this.addRecipe("ingotGold", "plateGold");
		this.addRecipe("ingotIron", "plateIron");
		this.addRecipe("ingotSilver", "plateSilver");
		this.addRecipe("ingotTin", "plateTin");
		this.addRecipe("ingotLead", "plateLead");
		this.addRecipe("ingotBrass", "plateBrass");
		this.addRecipe("ingotNickel", "plateNickel");
		this.addRecipe("ingotPlatinum", "platePlatinum");

	}

	public void addRecipe(String supplyDict, String productDict, int qty) {
		NonNullList<ItemStack> supplyList = OreDictionary.getOres(supplyDict);
		NonNullList<ItemStack> productList = OreDictionary.getOres(productDict);

		if(supplyList.isEmpty() || productList.isEmpty()) {
			DarkMachinations.LOG.error("Couldn't add a Compressor recipe for conversion of " + supplyDict + " to " + qty + " " + productDict + "(s)! You might be missing one of those...");
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
