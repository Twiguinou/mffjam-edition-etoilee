package com.twihick.bunlyu.common.entities.rocket.part;

import com.twihick.bunlyu.common.entities.rocket.RocketCoreEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class ARocketPartEntity extends Entity {

    private RocketCoreEntity linkedCore;

    public ARocketPartEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public ARocketPartEntity(EntityType<?> entityTypeIn, World worldIn, double x, double y, double z, @Nullable RocketCoreEntity linkedCore) {
        this(entityTypeIn, worldIn);
        this.setPosition(x, y, z);
        if(linkedCore == null)
            this.linkedCore = new RocketCoreEntity(worldIn, x, y, z);
        else
            this.linkedCore = linkedCore;
    }

    @Override
    protected void registerData() {
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
