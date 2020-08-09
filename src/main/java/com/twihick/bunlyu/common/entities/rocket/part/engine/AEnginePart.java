package com.twihick.bunlyu.common.entities.rocket.part.engine;

import com.twihick.bunlyu.common.entities.rocket.RocketCoreEntity;
import com.twihick.bunlyu.common.entities.rocket.part.ARocketPartEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class AEnginePart extends ARocketPartEntity {

    public AEnginePart(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public AEnginePart(EntityType<?> entityTypeIn, World worldIn, double x, double y, double z, @Nullable RocketCoreEntity linkedCore) {
        super(entityTypeIn, worldIn, x, y, z, linkedCore);
    }

}
