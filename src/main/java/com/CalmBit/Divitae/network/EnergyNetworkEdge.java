package com.CalmBit.Divitae.network;


import com.CalmBit.Divitae.BlockRegistry;
import com.CalmBit.Divitae.cable.BlockCable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

public class EnergyNetworkEdge implements INetworkEdge {
    // The blocks which are being connected by the edge.
    private EnergyNetworkNode first, last;

    // The actual cables that represent the start/end of the route.
    private BlockPos firstCable, lastCable;

    // The blocks that comprise the path, not including the two above.
    private ArrayList<BlockPos> route;

    private EnergyNetwork parent;

    public EnergyNetworkEdge(EnergyNetwork parent, EnergyNetworkNode first, EnergyNetworkNode last)
    {
        this.parent = parent;
        this.first = first;
        this.firstCable = first.getPosition().add(first.getFace().getOpposite().getDirectionVec());
        this.last = last;
        this.lastCable = last.getPosition().add(last.getFace().getOpposite().getDirectionVec());
        this.route = new ArrayList<>();
        findShortestRoute();
    }

    public void findShortestRoute() {
        ArrayList<BlockPos> nodeList = (ArrayList<BlockPos>)parent.memberBlocks.clone();
        HashMap<BlockPos, Tuple<BlockPos, Integer>> openList = new HashMap<>();
        HashMap<BlockPos, Tuple<BlockPos, Integer>> closedList = new HashMap<>();

        int travelDistance = 0;
        int hDistance = (int)Math.sqrt(firstCable.distanceSq(last.getPosition()));
        openList.put(firstCable, new Tuple<>(BlockPos.ORIGIN, travelDistance + hDistance));

        while(true) {

            if(openList.isEmpty())
            {
                break;
            }

            travelDistance++;

            int currentLowest = Integer.MAX_VALUE;
            BlockPos currentSquare = BlockPos.ORIGIN;
            for(BlockPos pos : openList.keySet())
            {
                if(openList.get(pos).getSecond() < currentLowest)
                    currentSquare = pos;
            }

            closedList.put(currentSquare, openList.remove(currentSquare));

            if(currentSquare == lastCable)
            {
                constructRoute(closedList, currentSquare);
                break;
            }

            else
            {
                for(EnumFacing facing : EnumFacing.VALUES) {
                    BlockPos workingSquare = currentSquare.add(facing.getDirectionVec());
                    if(!nodeList.contains(workingSquare) || closedList.containsKey(workingSquare))
                        continue;

                    if(parent.getWorld().getBlockState(workingSquare).getValue(BlockCable.EDGE_MEMBER)) {
                        // Silent Promotion, Bookkeeping Node

                        // Create a clone of the current last node
                        EnergyNetworkNode oldLast = (EnergyNetworkNode)this.last.clone();

                        // Establish the new last node (the book keeping node)
                        this.last = new EnergyNetworkNode(workingSquare, facing.getOpposite());

                        // Try to find the intersecting edge
                        EnergyNetworkEdge otherEdge = null;
                        for(EnergyNetworkEdge edge : parent.edges) {
                            if(edge.containsBlock(workingSquare)) {
                                otherEdge = edge;
                                break;
                            }
                        }

                        // The other edge now also receives the edge
                        otherEdge.last = this.last;
                        otherEdge.findShortestRoute();

                        parent.getWorld().setBlockState(workingSquare, BlockRegistry.cable_regular_node.getDefaultState(), 3);
                        this.parent.addEdge(this.last, oldLast);
                        constructRoute(closedList, currentSquare);
                        break;
                    }
                    if(!openList.containsKey(workingSquare))
                    {
                        hDistance = (int)Math.sqrt(workingSquare.distanceSq(lastCable));
                        openList.put(workingSquare, new Tuple<>(currentSquare, travelDistance + hDistance));
                    }
                }
            }
        }
    }

    @Override
    public boolean containsBlock(BlockPos block) {
        for(BlockPos routeBlock : route) {
            if(block == routeBlock)
                return true;
        }
        return false;
    }

    private void constructRoute(HashMap<BlockPos, Tuple<BlockPos, Integer>> cables, BlockPos start) {
        BlockPos parent = start;
        while(parent != firstCable) {
            parent = cables.get(parent).getFirst();
            this.parent.getWorld().setBlockState(parent, this.parent.getWorld().getBlockState(parent).withProperty(BlockCable.EDGE_MEMBER, true));
            if(parent != firstCable)
                route.add(parent);
        }
    }

    @Override
    public boolean equals(INetworkEdge other) {
        if(other instanceof EnergyNetworkEdge) {
            EnergyNetworkEdge otherEdge = (EnergyNetworkEdge)other;
            return this.first.equals(otherEdge.first) && this.last.equals(otherEdge.last);
        }
        return false;
    }

    @Override
    public boolean equalsProspective(INetworkNode first, INetworkNode last) {
        boolean flag = true;

        if(first instanceof EnergyNetworkNode) {
            EnergyNetworkNode firstNode = (EnergyNetworkNode)first;
            flag = flag && (firstNode.equals(this.first));
        }
        else return false;

        if(last instanceof EnergyNetworkNode) {
            EnergyNetworkNode lastNode = (EnergyNetworkNode)last;
            flag = flag && (lastNode.equals(this.first));
        }
        else return false;

        return flag;
    }

    public boolean isValidEdge() {
        return !route.isEmpty();
    }
}
