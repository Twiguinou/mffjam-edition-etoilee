package com.twihick.bunlyu.common.entities.rocket;

import com.twihick.bunlyu.common.lib.collision.OrientedBoundingBox;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class ParachuteSmall extends ARocketPart {

    public ParachuteSmall(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public boolean canSupportPart() {
        return false;
    }

    @Override
    protected OrientedBoundingBox getOrientedCollisionBox() {
        return new OrientedBoundingBox(-0.2D, -1.0D, -0.2D, 0.2D, -0.9D, 0.2D);
    }

}
