package com.calmbit.darkmachinations.cable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockCableCopperUninsulated extends BlockCable {
    public BlockCableCopperUninsulated(String name) {
        super(name);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if((worldIn.getTileEntity(pos) != null) && (worldIn.getTileEntity(pos) instanceof  TileEntityCableNode))
            ((TileEntityCableNode)worldIn.getTileEntity(pos)).assignCableType(BlockCableEndpoint.BlockCableType.COPPER_UNINSULATED);
    }
}
