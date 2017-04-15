package com.CalmBit.DarkMachinations.proxy;

import com.CalmBit.DarkMachinations.DarkMachinations;
import com.CalmBit.DarkMachinations.FluidRegistry;
import com.CalmBit.DarkMachinations.monster.EntityGoldenCreeper;
import com.CalmBit.DarkMachinations.monster.RenderGoldenCreeper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@SuppressWarnings("MethodCallSideOnly")
public class ClientProxy extends CommonProxy {

    private static ModelResourceLocation heavyCrudeOilLocation = new ModelResourceLocation("darkmachinations:heavy_crude_oil", "heavy_crude_oil");

    public void init()
    {
        super.init();
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(FluidRegistry.heavy_crude_oil), stack -> heavyCrudeOilLocation);
        ModelLoader.setCustomStateMapper(FluidRegistry.heavy_crude_oil, new StateMapperBase()
        {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return heavyCrudeOilLocation;
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityGoldenCreeper.class, RenderGoldenCreeper::new);
    }
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(DarkMachinations.DARKMACHINATIONS_MOD_ID + ":" + id, "inventory"));
    }
}
