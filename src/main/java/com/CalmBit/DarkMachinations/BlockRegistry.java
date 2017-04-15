package com.CalmBit.DarkMachinations;

import com.CalmBit.DarkMachinations.cable.*;
import com.CalmBit.DarkMachinations.generic.*;
import com.CalmBit.DarkMachinations.machine.BlockCompressor;
import com.CalmBit.DarkMachinations.machine.BlockGenerator;
import com.CalmBit.DarkMachinations.machine.BlockCrusher;
import com.CalmBit.DarkMachinations.machine.BlockMachineBase;
import com.CalmBit.DarkMachinations.world.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {

    public static BlockOre ore_copper;
    public static BlockOre ore_silver;
    public static BlockOre ore_tin;
    public static BlockCrusher machine_crusher;
    public static BlockCompressor machine_compressor;
    public static BlockBase machine_redstone_core;
    public static BlockGenerator machine_generator;
    public static BlockCable cable_copper;
    public static BlockCable cable_gold;
    public static BlockCable cable_tin;
    public static BlockCableEndpoint cable_regular_node;
    public static void init()
    {
        ore_copper = registerBlock(new BlockOre("ore_copper", "oreCopper").setCreativeTab(DarkMachinations.divitaeTab));
        ore_silver = registerBlock(new BlockOre("ore_silver", "oreSilver", 3, 5, 2).setCreativeTab(DarkMachinations.divitaeTab));
        ore_tin = registerBlock(new BlockOre("ore_tin", "oreTin").setCreativeTab(DarkMachinations.divitaeTab));
        machine_crusher = registerBlock(new BlockCrusher());
        machine_compressor = registerBlock(new BlockCompressor());
        machine_redstone_core = registerBlock(new BlockBase(Material.IRON, "machine_redstone_core")).setCreativeTab(DarkMachinations.divitaeTab);
        machine_generator = registerBlock(new BlockGenerator());
        cable_copper = registerBlock(new BlockCableCopper("cable_copper")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_gold = registerBlock(new BlockCableGold("cable_gold")).setCreativeTab(DarkMachinations.divitaeTab);
        cable_tin = registerBlock(new BlockCableTin("cable_tin")).setCreativeTab(DarkMachinations.divitaeTab);
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
