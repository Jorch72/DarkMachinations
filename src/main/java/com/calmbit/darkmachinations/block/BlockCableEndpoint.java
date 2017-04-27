package com.calmbit.darkmachinations.block;


import com.calmbit.darkmachinations.registry.BlockRegistry;
import com.calmbit.darkmachinations.registry.NetworkRegistry;
import com.calmbit.darkmachinations.cable.TileEntityCableNode;
import com.calmbit.darkmachinations.network.EnergyNetworkNode;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class BlockCableEndpoint extends BlockCable {

    public enum BlockCableType implements IStringSerializable {

        COPPER_INSULATED("copper_insulated"),
        GOLD_INSULATED("gold_insulated"),
        TIN_INSULATED("tin_insulated"),
        SILVER_INSULATED("silver_insulated"),
        COPPER_UNINSULATED("copper_uninsulated"),
        GOLD_UNINSULATED("gold_uninsulated"),
        TIN_UNINSULATED("tin_uninsulated"),
        SILVER_UNINSULATED("silver_uninsulated");

        private String name;
        BlockCableType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public static PropertyEnum<BlockCableType> CABLE_TYPE = PropertyEnum.create("cable_type", BlockCableType.class);

    public BlockCableEndpoint(String name) {
        super(name);
        this.setDefaultState(this.getDefaultState().withProperty(CABLE_TYPE, BlockCableType.COPPER_UNINSULATED));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }



    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityCableNode node = (TileEntityCableNode)world.getTileEntity(pos);
        if(node != null)
            return super.getActualState(state, world, pos).withProperty(CABLE_TYPE, node.getCableType());
        return super.getActualState(state, world, pos);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(!worldIn.isRemote)
        {
            TileEntityCableNode node = (TileEntityCableNode) worldIn.getTileEntity(pos);
            UUID network = NetworkRegistry.getNetworkIdentityForBlock(pos);
            if(network != null && node != null)
                NetworkRegistry.getNetwork(network).removeNode(new EnergyNetworkNode(NetworkRegistry.getNetwork(network), pos, node.getNodeType()));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        switch(state.getValue(CABLE_TYPE)) {
            case TIN_INSULATED:
                return Item.getItemFromBlock(BlockRegistry.cable_tin_insulated);
            case COPPER_INSULATED:
                return Item.getItemFromBlock(BlockRegistry.cable_copper_insulated);
            case GOLD_INSULATED:
                return Item.getItemFromBlock(BlockRegistry.cable_gold_insulated);
            case TIN_UNINSULATED:
                return Item.getItemFromBlock(BlockRegistry.cable_tin_uninsulated);
            case COPPER_UNINSULATED:
                return Item.getItemFromBlock(BlockRegistry.cable_copper_uninsulated);
            case GOLD_UNINSULATED:
                return Item.getItemFromBlock(BlockRegistry.cable_gold_uninsulated);
            default:
                return Item.getItemFromBlock(BlockRegistry.cable_copper_uninsulated);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CABLE_TYPE, EDGE_MEMBER, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public TileEntityCableNode createTileEntity(World world, IBlockState state) {
        return new TileEntityCableNode();
    }





}
