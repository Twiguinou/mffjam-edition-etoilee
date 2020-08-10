package com.twihick.bunlyu.common.lib.math;

import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;

public final class MathComparator {

    public static Vec3d interpolate(Vec3d start, Vec3d end, double momentum) {
        double norm = 1.0D - momentum;
        return new Vec3d(start.x*norm+end.x*momentum, start.y*norm+end.y*momentum, start.z*norm+end.z*momentum);
    }

    public static double interpolate(double start, double end, double momentum) {
        return start + momentum * (end-start);
    }

    public static Vec3d rotateVector(Vec3d origin, Vec3d vec3d, Direction.Axis axis, double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double x = vec3d.x - origin.x;
        double y = vec3d.y - origin.z;
        double z = vec3d.y - origin.z;
        switch(axis) {
            case Z:
                double x0 = x * cos - y * sin;
                double y0 = y * cos + x * sin;
                return new Vec3d(x0+origin.x, y0+origin.y, vec3d.z);
            case X:
                double y1 = y * cos - z * sin;
                double z0 = z * cos + y * sin;
                return new Vec3d(vec3d.x, y1+origin.y, z0+origin.z);
            case Y:
                double x1 = x * cos + z * sin;
                double z1 = z * cos - x * sin;
                return new Vec3d(x1+origin.x, vec3d.y, z1+origin.z);
            default:
                break;
        }
        return vec3d;
    }

}
