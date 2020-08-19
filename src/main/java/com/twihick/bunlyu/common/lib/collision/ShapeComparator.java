package com.twihick.bunlyu.common.lib.collision;

import com.google.common.collect.ImmutableMap;
import com.twihick.bunlyu.common.blocks.AHorizontalFacingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class ShapeComparator {

    public static VoxelShape combineSet(Set<VoxelShape> shapes) {
        VoxelShape newShape = VoxelShapes.empty();
        for(VoxelShape shape : shapes)
            newShape = VoxelShapes.combine(newShape, shape, IBooleanFunction.OR);
        return newShape.simplify();
    }

    public static ImmutableMap<BlockState, VoxelShape> generateShapeMap(Collection<BlockState> states, VoxelShape[]... arrayShapes) {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states) {
            Set<VoxelShape> set = new HashSet<>();
            for(VoxelShape[] shapes : arrayShapes)
                set.add(shapes[state.get(AHorizontalFacingBlock.ALIGNMENT).getHorizontalIndex()]);
            builder.put(state, combineSet(set));
        }
        return builder.build();
    }

    public static VoxelShape rotateHorizontal(VoxelShape ref, Direction direction) {
        double startX = ref.getStart(Direction.Axis.X);
        double startY = ref.getStart(Direction.Axis.Y);
        double startZ = ref.getStart(Direction.Axis.Z);
        double endX = ref.getEnd(Direction.Axis.X);
        double endY = ref.getEnd(Direction.Axis.Y);
        double endZ = ref.getEnd(Direction.Axis.Z);
        final double temp0 = startX, temp1 = startZ, temp2 = endX;
        switch(direction) {
            case SOUTH:
                startX = 1.0F - endX;
                startZ = 1.0F - endZ;
                endX = 1.0F - temp0;
                endZ = 1.0F - temp1;
                break;
            case WEST:
                startX = endZ;
                startZ = 1.0F - temp0;
                endX = temp1;
                endZ = 1.0F - temp2;
                break;
            case EAST:
                startX = 1.0F - startZ;
                startZ = endX;
                endX = 1.0F - endZ;
                endZ = temp0;
                break;
            default:
                return ref;
        }
        return VoxelShapes.create(startX, startY, startZ, endX, endY, endZ);
    }

    public static VoxelShape[] getHorizontalSubs(VoxelShape ref) {
        return new VoxelShape[] {
                rotateHorizontal(ref, Direction.NORTH),
                rotateHorizontal(ref, Direction.EAST),
                rotateHorizontal(ref, Direction.SOUTH),
                rotateHorizontal(ref, Direction.WEST)
        };
    }

}
