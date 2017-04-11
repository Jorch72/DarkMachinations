package com.CalmBit.Divitae.proxy;

import com.CalmBit.Divitae.Divitae;
import com.CalmBit.Divitae.FluidRegistry;
import com.CalmBit.Divitae.monster.EntityGoldenCreeper;
import com.CalmBit.Divitae.monster.RenderGoldenCreeper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    private static ModelResourceLocation heavyCrudeOilLocation = new ModelResourceLocation("divitae:heavy_crude_oil", "heavy_crude_oil");

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
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Divitae.DIVITAE_MOD_ID + ":" + id, "inventory"));
    }
}
