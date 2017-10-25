package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.generic.IDarmaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockFluidBaseClassic extends BlockFluidClassic implements IDarmaBlock {
	public BlockFluidBaseClassic(Fluid fluid, Material material, String name) {
		super(fluid, material);
		setUnlocalizedName(DarkMachinations.DARKMACHINATIONS_MOD_ID+"."+name);
		setRegistryName(name);
	}

	@SideOnly(Side.CLIENT)
	public void registerItemModel(ItemBlock itemBlock) {
		ModelBakery.registerItemVariants(itemBlock);
	}

	public ItemBlock getItemBlock() {
		return (ItemBlock)new ItemBlock(this).setRegistryName(this.getRegistryName());
	}

}
