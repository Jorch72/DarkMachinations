package com.CalmBit.Divitae.world;

import com.CalmBit.Divitae.generic.IDivitaeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidHeavyCrudeOil extends BlockFluidClassic implements IDivitaeBlock {
    public BlockFluidHeavyCrudeOil(Fluid fluid, Material material) {
        super(fluid, material);
        setRegistryName("heavy_crude_oil");
        setUnlocalizedName("heavy_crude_oil");
    }

    public void registerItemModel(ItemBlock itemBlock)
    {
        ModelBakery.registerItemVariants(itemBlock);
    }

}
