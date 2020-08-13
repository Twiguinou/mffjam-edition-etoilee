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

    @Override
    public void tick() {
        /*this.prevRotationYaw += 0.25F;
        this.rotationYaw += 0.25F;
        if(this.prevRotationYaw >= 360.0F)
            this.prevRotationYaw = 360.0F;
        if(this.rotationYaw >= 360.0F)
            this.rotationYaw = 360.0F;*/
    }

}
