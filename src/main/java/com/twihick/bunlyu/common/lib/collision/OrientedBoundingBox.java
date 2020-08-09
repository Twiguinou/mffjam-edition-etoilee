package com.twihick.bunlyu.common.lib.collision;

import com.twihick.bunlyu.common.lib.math.MathComparator;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;

public class OrientedBoundingBox implements IBoundingBox {

    private final Vec3d[] nodes;
    private final double rotX;
    private final double rotY;
    private final double rotZ;

    protected OrientedBoundingBox(Vec3d[] nodes, double rotX, double rotY, double rotZ) {
        if(nodes.length != 8)
            throw new IllegalStateException();
        this.nodes = nodes;
        this.rotX = rotX >= 360.0D ? rotX - 360.0D : rotX;
        this.rotY = rotY >= 360.0D ? rotY - 360.0D : rotY;
        this.rotZ = rotZ >= 360.0D ? rotZ - 360.0D : rotZ;
    }

    public OrientedBoundingBox(AxisAlignedBB axisAlignedBB) {
        this(new Vec3d[] {
                new Vec3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ),
                new Vec3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ),
                new Vec3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ),
                new Vec3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ),
                new Vec3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ),
                new Vec3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ),
                new Vec3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ),
                new Vec3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ)
        }, 0.0D, 0.0D, 0.0D);
    }

    public Vec3d getCenter() {
        Vec3d center = this.nodes[0];
        for(int i = 1; i < this.nodes.length; i++)
            center = MathComparator.interpolate(center, this.nodes[i], 0.5D);
        return center;
    }

    private Vec3d getMinVec() {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        for(int i = 0; i < this.nodes.length; i++) {
            Vec3d node = this.nodes[i];
            if(node.x < minX)
                minX = node.x;
            if(node.y < minY)
                minY = node.y;
            if(node.z < minZ)
                minZ = node.z;
        }
        return new Vec3d(minX, minY, minZ);
    }

    private Vec3d getMaxVec() {
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double maxZ = Double.MIN_VALUE;
        for(int i = 0; i < this.nodes.length; i++) {
            Vec3d node = this.nodes[i];
            if(node.x > maxX)
                maxX = node.x;
            if(node.y > maxY)
                maxY = node.y;
            if(node.z > maxZ)
                maxZ = node.z;
        }
        return new Vec3d(maxX, maxY, maxZ);
    }

    public OrientedBoundingBox rotateRelative(Vec3d origin, Direction.Axis axis, double angle) {
        Vec3d[] copy = Arrays.copyOf(this.nodes, this.nodes.length);
        for(int i = 0; i < copy.length; i++)
            copy[i] = MathComparator.rotateVector(origin, copy[i], axis, angle);
        switch(axis) {
            case Z:
                return new OrientedBoundingBox(copy, this.rotX, this.rotY, this.rotZ+angle);
            case X:
                return new OrientedBoundingBox(copy, this.rotX+angle, this.rotY, this.rotZ);
            case Y:
                return new OrientedBoundingBox(copy, this.rotX, this.rotY+angle, this.rotZ);
            default:
                break;
        }
        return this;
    }

    @Override
    public AxisAlignedBB toAxisAlignedBB() {
        Vec3d min = this.getMinVec();
        Vec3d max = this.getMaxVec();
        return new AxisAlignedBB(min.x, min.y, min.z, max.x, max.y, max.z);
    }

}
