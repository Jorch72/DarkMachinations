package com.calmbit.darkmachinations.registry;

import com.calmbit.darkmachinations.DarkMachinations;
import com.calmbit.darkmachinations.block.*;
import com.calmbit.darkmachinations.cable.*;
import com.calmbit.darkmachinations.generic.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {

    public static BlockCrusher machine_crusher;
    public static BlockCompressor machine_compressor;
    public static BlockBase machine_redstone_core;
    public static BlockGenerator machine_generator;
    public static BlockPump machine_pump;

    public static BlockBloomery machine_bloomery;

    public static BlockCable cable_copper_insulated;
    public static BlockCable cable_gold_insulated;
    public static BlockCable cable_tin_insulated;
    public static BlockCable cable_copper_uninsulated;
    public static BlockCable cable_gold_uninsulated;
    public static BlockCable cable_tin_uninsulated;
    public static BlockCableEndpoint cable_regular_node;

    public static void init()
    {
        machine_crusher = registerBlock(new BlockCrusher());
        machine_compressor = registerBlock(new BlockCompressor());
        machine_redstone_core = registerBlock(new BlockBase(Material.IRON, "machine_redstone_core")).setCreativeTab(DarkMachinations.divitaeTab);
        machine_generator = registerBlock(new BlockGenerator());
        machine_pump = registerBlock(new BlockPump());

        machine_bloomery = registerBlock(new BlockBloomery());

        cable_copper_insulated = registerBlock(new BlockCableCopperInsulated("cable_copper_insulated")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_gold_insulated = registerBlock(new BlockCableGoldInsulated("cable_gold_insulated")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_tin_insulated = registerBlock(new BlockCableTinInsulated("cable_tin_insulated")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_copper_uninsulated = registerBlock(new BlockCableCopperUninsulated("cable_copper_uninsulated")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_gold_uninsulated = registerBlock(new BlockCableGoldUninsulated("cable_gold_uninsulated")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_tin_uninsulated = registerBlock(new BlockCableTinUninsulated("cable_tin_uninsulated")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_regular_node = registerBlock(new BlockCableEndpoint("cable_regular_node"));
    }

    private static <T extends Block & IDivitaeBlock> T registerBlock(T block, ItemBlock itemBlock)
    {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        block.registerItemModel(itemBlock);

        if(block instanceof IOreDict)
        {
            ((IOreDict)block).registerOreDict();
        }
        if(block instanceof BlockMachineBase)
        {
            GameRegistry.registerTileEntity(((BlockMachineBase<?>)block).getTileEntityClass(), block.getRegistryName().toString());
        }
        if(block instanceof BlockCableEndpoint)
        {
            GameRegistry.registerTileEntity(TileEntityCableNode.class, block.getRegistryName().toString());
        }
        if(itemBlock instanceof IOreDict)
        {
            ((IOreDict)itemBlock).registerOreDict();
        }

        return block;
    }

    public static <T extends Block & IDivitaeBlock> T registerBlock(T block)
    {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return registerBlock(block, itemBlock);
    }
}
