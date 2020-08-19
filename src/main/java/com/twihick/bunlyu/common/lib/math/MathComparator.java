package com.twihick.bunlyu.common.lib.math;

import com.twihick.bunlyu.common.lib.PairPicker;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class MathComparator {

    public static final double GROUND_ZERO = 0.00000000000000000001D;

    public static Vec3d interpolate(Vec3d start, Vec3d end, double momentum) {
        double norm = 1.0D - momentum;
        return new Vec3d(start.x*norm+end.x*momentum, start.y*norm+end.y*momentum, start.z*norm+end.z*momentum);
    }

    public static Vector3f interpolate(Vector3f start, Vector3f end, float momentum) {
        float norm = 1.0F - momentum;
        return new Vector3f(start.getX()*norm+end.getX()*momentum, start.getY()*norm+end.getY()*momentum, start.getZ()*norm+end.getZ()*momentum);
    }

    public static PairPicker<Vec3d, Vector3f> toVector(BlockPos pos, PairPicker<Class<? extends Vec3d>, Class<? extends Vector3f>> pair) {
        if(pair.getValidState() == PairPicker.State.FIRST)
            return PairPicker.firstOf(new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
        return PairPicker.lastOf(new Vector3f(pos.getX(), pos.getY(), pos.getZ()));
    }

    public static Vec3d midpoint(Vec3d vec3d, Vec3d vec3d1) {
        return new Vec3d((vec3d.x+vec3d1.x)/2.0D, (vec3d.y+vec3d1.y)/2.0D, (vec3d.z+vec3d1.z)/2.0D);
    }

    public static Vector3f midpoint(Vector3f vector3f, Vector3f vector3f1) {
        return new Vector3f((vector3f.getX()+vector3f1.getX())/2.0F, (vector3f.getY()+vector3f1.getY())/2.0F, (vector3f.getZ()+vector3f1.getZ())/2.0F);
    }

    public static boolean intersects(AxisAlignedBB axisAlignedBB, Vec3d vec3d) {
        return (vec3d.x >= axisAlignedBB.minX && vec3d.x <= axisAlignedBB.maxX) && (vec3d.y >= axisAlignedBB.minY && vec3d.y <= axisAlignedBB.maxY) && (vec3d.z >= axisAlignedBB.minZ && vec3d.z <= axisAlignedBB.maxZ);
    }

}
