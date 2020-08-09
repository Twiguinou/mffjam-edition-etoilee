package com.twihick.bunlyu.common.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.Vec2f;

public interface IAssemblyType {

    EntityClassification getClassification();

    Vec2f getSize();

    boolean handlesVelocityUpdate();

    boolean isImmuneToFire();

    int getTrackingRange();

    int getUpdateInterval();

    final class Default implements IAssemblyType {
        private final Vec2f size;
        private Default(Vec2f size) {
            this.size = size;
        }
        @Override
        public EntityClassification getClassification() {
            return EntityClassification.MISC;
        }
        @Override
        public Vec2f getSize() {
            return this.size;
        }
        @Override
        public boolean handlesVelocityUpdate() {
            return true;
        }
        @Override
        public boolean isImmuneToFire() {
            return false;
        }
        @Override
        public int getTrackingRange() {
            return 5;
        }
        @Override
        public int getUpdateInterval() {
            return 3;
        }
        public static IAssemblyType.Default getInstance(float width, float height) {
            return new IAssemblyType.Default(new Vec2f(width, height));
        }
    }

}
