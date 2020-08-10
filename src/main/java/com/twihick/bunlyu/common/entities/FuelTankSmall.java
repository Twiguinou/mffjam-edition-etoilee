package com.twihick.bunlyu.common.entities;

import com.twihick.bunlyu.common.lib.collision.OrientedBoundingBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class FuelTankSmall extends Entity {

    public FuelTankSmall(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void registerData() {
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        OrientedBoundingBox orientedBoundingBox = this.getOrientedCollisionBox().offset(this.getPositionVec());
        //orientedBoundingBox = orientedBoundingBox.rotateRelative(this.getPositionVec(), Direction.Axis.Z, 10.0D);
        return orientedBoundingBox.toAxisAlignedBB();
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getBoundingBox() : null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getBoundingBox();
    }

    private OrientedBoundingBox getOrientedCollisionBox() {
        return new OrientedBoundingBox(-0.5D, 0.0D, -0.5D, 0.5D, 2.0D, 0.5D);
    }

    @Override
    public void tick() {
        super.tick();
        this.prevRotationYaw += 0.25F;
        this.rotationYaw += 0.25F;
        if(this.prevRotationYaw >= 360.0F)
            this.prevRotationYaw = 360.0F;
        if(this.rotationYaw >= 360.0F)
            this.rotationYaw = 360.0F;
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
