package com.twihick.bunlyu.common.entities.rocket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class AMountableRocketPart extends ARocketPart {

    public AMountableRocketPart(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return entityIn instanceof PlayerEntity && this.getPassengers().size() == 0;
    }

}
