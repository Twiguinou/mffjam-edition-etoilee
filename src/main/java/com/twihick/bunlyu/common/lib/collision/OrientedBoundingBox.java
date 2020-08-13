package com.twihick.bunlyu.common.lib.collision;

import com.google.common.collect.ImmutableList;
import com.twihick.bunlyu.common.lib.math.MathComparator;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;

public class OrientedBoundingBox {

    public static final OrientedBoundingBox EMPTY = new OrientedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D ,0.0D));

    private final Vec3d[] nodes;

    protected OrientedBoundingBox(Vec3d[] nodes) {
        if(nodes.length != 8)
            throw new IllegalStateException();
        this.nodes = nodes;
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
        });
    }

    public OrientedBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
    }

    public OrientedBoundingBox offset(Vec3d vec3d) {
        Vec3d[] copy = Arrays.copyOf(this.nodes, this.nodes.length);
        for(int i = 0; i < copy.length; i++)
            copy[i] = copy[i].add(vec3d);
        return new OrientedBoundingBox(copy);
    }

    public Vec3d getCenter() {
        Vec3d center = this.nodes[0];
        for(int i = 1; i < this.nodes.length; i++)
            center = MathComparator.interpolate(center, this.nodes[i], 0.5D);
        return center;
    }

    private Vec3d getMinVec() {
        Vec3d firstNode = this.nodes[0];
        double minX = firstNode.x;
        double minY = firstNode.y;
        double minZ = firstNode.z;
        for(int i = 1; i < this.nodes.length; i++) {
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
        Vec3d firstNode = this.nodes[0];
        double maxX = firstNode.x;
        double maxY = firstNode.y;
        double maxZ = firstNode.z;
        for(int i = 1; i < this.nodes.length; i++) {
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
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        switch(axis) {
            case Z:
                Vec3d[] copyz = Arrays.copyOf(this.nodes, this.nodes.length);
                for(int i = 0; i < copyz.length; i++) {
                    double x = copyz[i].x - origin.x;
                    double y = copyz[i].y - origin.y;
                    double z = copyz[i].z - origin.z;
                    double x1 = x * cos - y * sin;
                    double y1 = y * cos + x * sin;
                    copyz[i] = new Vec3d(x1+origin.x, y1+origin.y, z+origin.z);
                }
                return new OrientedBoundingBox(copyz);
            case X:
                Vec3d[] copyx = Arrays.copyOf(this.nodes, this.nodes.length);
                for(int i = 0; i < copyx.length; i++) {
                    double x = copyx[i].x - origin.x;
                    double y = copyx[i].y - origin.y;
                    double z = copyx[i].z - origin.z;
                    double y1 = y * cos - z * sin;
                    double z1 = z * cos + y * sin;
                    copyx[i] = new Vec3d(x+origin.x, y1+origin.y, z1+origin.z);
                }
                return new OrientedBoundingBox(copyx);
            case Y:
                Vec3d[] copyy = Arrays.copyOf(this.nodes, this.nodes.length);
                for(int i = 0; i < copyy.length; i++) {
                    double x = copyy[i].x - origin.x;
                    double y = copyy[i].y - origin.y;
                    double z = copyy[i].z - origin.z;
                    double x1 = x * cos + z * sin;
                    double z1 = z * cos - x * sin;
                    copyy[i] = new Vec3d(x1+origin.x, y+origin.y, z1+origin.z);
                }
                return new OrientedBoundingBox(copyy);
            default:
                return this;
        }
    }

    public AxisAlignedBB toAxisAlignedBB() {
        Vec3d min = this.getMinVec();
        Vec3d max = this.getMaxVec();
        return new AxisAlignedBB(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public ImmutableList<Vec3d> getNodes() {
        return ImmutableList.copyOf(this.nodes);
    }

}
