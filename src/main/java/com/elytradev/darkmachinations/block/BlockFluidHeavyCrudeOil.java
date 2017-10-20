package com.elytradev.darkmachinations.block;

import com.elytradev.darkmachinations.generic.IDarmaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidHeavyCrudeOil extends BlockFluidClassic implements IDarmaBlock {
	public BlockFluidHeavyCrudeOil(Fluid fluid, Material material) {
		super(fluid, material);
		setRegistryName("heavy_crude_oil");
		setUnlocalizedName("heavy_crude_oil");
	}



	@SideOnly(Side.CLIENT)
	public void registerItemModel(ItemBlock itemBlock)
	{
		ModelBakery.registerItemVariants(itemBlock);
	}

	public ItemBlock getItemBlock() {
		return (ItemBlock)new ItemBlock(this).setRegistryName(this.getRegistryName());
	}

}
