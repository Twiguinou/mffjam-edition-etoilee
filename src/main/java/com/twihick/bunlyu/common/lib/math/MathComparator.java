package com.twihick.bunlyu.common.lib.math;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public final class MathComparator {

    public static Vec3d interpolate(Vec3d start, Vec3d end, double momentum) {
        double norm = 1.0D - momentum;
        return new Vec3d(start.x*norm+end.x*momentum, start.y*norm+end.y*momentum, start.z*norm+end.z*momentum);
    }

    public static boolean intersects(AxisAlignedBB axisAlignedBB, Vec3d vec3d) {
        return (vec3d.x >= axisAlignedBB.minX && vec3d.x <= axisAlignedBB.maxX) && (vec3d.y >= axisAlignedBB.minY && vec3d.y <= axisAlignedBB.maxY) && (vec3d.z >= axisAlignedBB.minZ && vec3d.z <= axisAlignedBB.maxZ);
    }

}
