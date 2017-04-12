package com.CalmBit.Divitae.network;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

public class NetworkEdge {
    // The blocks which are being connected by the edge.
    private BlockPos first, last;

    // The blocks that comprise the path, not including the two above.
    private ArrayList<BlockPos> route;

    private Network parent;

    public NetworkEdge(Network parent, BlockPos first, BlockPos last)
    {
        this.parent = parent;
        this.first = first;
        this.last = last;
        this.route = new ArrayList<>();
        findShortestRoute();
    }

    private void findShortestRoute() {
        ArrayList<BlockPos> nodeList = (ArrayList<BlockPos>)parent.memberBlocks.clone();
        HashMap<BlockPos, Tuple<BlockPos, Integer>> openList = new HashMap<>();
        HashMap<BlockPos, Tuple<BlockPos, Integer>> closedList = new HashMap<>();

        int travelDistance = 0;
        int hDistance = (int)Math.sqrt(first.distanceSq(last));
        openList.put(first, new Tuple<>(BlockPos.ORIGIN, travelDistance + hDistance));

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

            if(currentSquare == last)
            {
                BlockPos parent = BlockPos.ORIGIN;
                while(parent != first) {
                    parent = closedList.get(currentSquare).getFirst();
                    if(parent != first)
                        route.add(parent);
                }
                break;
            }

            else
            {
                for(EnumFacing facing : EnumFacing.VALUES) {
                    BlockPos workingSquare = currentSquare.add(facing.getDirectionVec());
                    if(!nodeList.contains(workingSquare) || closedList.containsKey(workingSquare))
                        continue;

                    if(!openList.containsKey(workingSquare))
                    {
                        hDistance = (int)Math.sqrt(first.distanceSq(last));
                        openList.put(workingSquare, new Tuple<>(currentSquare, travelDistance + hDistance));
                    }
                }
            }
        }
    }

    public boolean isValidEdge() {
        return !route.isEmpty();
    }

    //public BlockPos getFirst();
}
