package com.twihick.bunlyu.common.entities.rocket;

import com.twihick.bunlyu.common.registry.EntitiesList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class RocketCoreEntity extends Entity {

    public RocketCoreEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public RocketCoreEntity(World worldIn, double x, double y, double z) {
        this(EntitiesList.ROCKET_CORE, worldIn);
        this.setPosition(x, y, z);
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
