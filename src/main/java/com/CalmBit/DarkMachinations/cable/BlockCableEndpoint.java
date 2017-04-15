package com.CalmBit.DarkMachinations.cable;


import com.CalmBit.DarkMachinations.NetworkRegistry;
import com.CalmBit.DarkMachinations.network.EnergyNetworkNode;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlockCableEndpoint extends BlockCable {

    public enum BlockCableType implements IStringSerializable {

        COPPER("copper"),
        GOLD("gold"),
        TIN("tin"),
        SILVER("silver");

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
        this.setDefaultState(this.getDefaultState().withProperty(CABLE_TYPE, BlockCableType.COPPER));
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CABLE_TYPE, EDGE_MEMBER, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public TileEntityCableNode createTileEntity(World world, IBlockState state) {
        return new TileEntityCableNode();
    }





}
