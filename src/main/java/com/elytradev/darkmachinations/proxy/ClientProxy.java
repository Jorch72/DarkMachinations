package com.elytradev.darkmachinations.proxy;

import com.elytradev.darkmachinations.DarkMachinations;
import com.elytradev.darkmachinations.client.RenderPump;
import com.elytradev.darkmachinations.registry.BlockRegistry;
import com.elytradev.darkmachinations.tileentity.TileEntityPump;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@SuppressWarnings("MethodCallSideOnly")
public class ClientProxy extends CommonProxy {

	private static ModelResourceLocation heavyCrudeOilLocation = new ModelResourceLocation("darkmachinations:heavy_crude_oil", "heavy_crude_oil");

	public void init()
	{
		super.init();
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(BlockRegistry.heavy_crude_oil), stack -> heavyCrudeOilLocation);
		ModelLoader.setCustomStateMapper(BlockRegistry.heavy_crude_oil, new StateMapperBase()
		{
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return heavyCrudeOilLocation;
			}
		});
	}
	@Override
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(DarkMachinations.DARKMACHINATIONS_MOD_ID + ":" + id, "inventory"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPump.class, new RenderPump());
	}
}
