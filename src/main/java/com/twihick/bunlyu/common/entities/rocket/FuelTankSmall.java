package com.twihick.bunlyu.common.entities.rocket;

import com.twihick.bunlyu.common.lib.collision.OrientedBoundingBox;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class FuelTankSmall extends ARocketPart {

    public FuelTankSmall(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public boolean canSupportPart() {
        return true;
    }

    @Override
    public OrientedBoundingBox getOrientedCollisionBox() {
        return new OrientedBoundingBox(-0.5D, -1.0D, -0.5D, 0.5D, 1.0D, 0.5D);
    }

}
