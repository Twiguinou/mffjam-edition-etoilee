package com.twihick.bunlyu.common.lib.math;

import net.minecraft.util.math.Vec3d;

public final class MathComparator {

    public static Vec3d interpolate(Vec3d start, Vec3d end, double momentum) {
        double norm = 1.0D - momentum;
        return new Vec3d(start.x*norm+end.x*momentum, start.y*norm+end.y*momentum, start.z*norm+end.z*momentum);
    }

    public static double interpolate(double start, double end, double momentum) {
        return start + momentum * (end-start);
    }

}
