package com.CalmBit.Divitae;

import com.CalmBit.Divitae.cable.BlockCable;
import com.CalmBit.Divitae.cable.BlockCableNode;
import com.CalmBit.Divitae.cable.TileEntityCableNode;
import com.CalmBit.Divitae.generic.*;
import com.CalmBit.Divitae.machine.BlockCompressor;
import com.CalmBit.Divitae.machine.BlockGenerator;
import com.CalmBit.Divitae.machine.BlockGrinder;
import com.CalmBit.Divitae.machine.BlockMachineBase;
import com.CalmBit.Divitae.world.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {

    public static BlockOre ore_copper;
    public static BlockOre ore_silver;
    public static BlockOre ore_tin;
    public static BlockGrinder machine_grinder;
    public static BlockCompressor machine_compressor;
    public static BlockBase machine_redstone_core;
    public static BlockGenerator machine_generator;
    public static BlockCable cable_regular;
    public static BlockCableNode cable_regular_node;
    public static void init()
    {
        ore_copper = registerBlock(new BlockOre("ore_copper", "oreCopper").setCreativeTab(Divitae.divitaeTab));
        ore_silver = registerBlock(new BlockOre("ore_silver", "oreSilver", 3, 5, 2).setCreativeTab(Divitae.divitaeTab));
        ore_tin = registerBlock(new BlockOre("ore_tin", "oreTin").setCreativeTab(Divitae.divitaeTab));
        machine_grinder = registerBlock(new BlockGrinder());
        machine_compressor = registerBlock(new BlockCompressor());
        machine_redstone_core = registerBlock(new BlockBase(Material.IRON, "machine_redstone_core")).setCreativeTab(Divitae.divitaeTab);
        machine_generator = registerBlock(new BlockGenerator());
        cable_regular = registerBlock(new BlockCable("cable_regular")).setCreativeTab(Divitae.divitaeTab);
        cable_regular_node = registerBlock(new BlockCableNode("cable_regular_node"));
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
        if(block instanceof BlockCable)
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
