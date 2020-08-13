package com.twihick.bunlyu.common.entities.rocket;

import com.twihick.bunlyu.common.lib.TypeHolder;
import com.twihick.bunlyu.common.lib.collision.OrientedBoundingBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class ARocketPart extends Entity {

    @Nullable
    private ARocketPart previousPart;
    @Nullable
    private ARocketPart nextPart;
    private boolean isLaunched;

    public ARocketPart(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.previousPart = null;
        this.nextPart = null;
        this.isLaunched = false;
    }

    public abstract boolean canSupportPart();

    public boolean hasPrevious() {
        return this.previousPart != null;
    }

    public boolean hasNext() {
        return this.nextPart != null;
    }

    @Nullable
    public ARocketPart getPrevious() {
        return this.previousPart;
    }

    @Nullable
    public ARocketPart getNext() {
        return this.nextPart;
    }

    public boolean setPrevious(ARocketPart part) {
        if(!this.hasPrevious()) {
            this.previousPart = part;
            return true;
        }
        return false;
    }

    public boolean setNext(ARocketPart part) {
        if(!this.hasNext()) {
            this.nextPart = part;
            part.setPrevious(this);
            return true;
        }
        return false;
    }

    public boolean isLaunched() {
        return this.isLaunched;
    }

    @Override
    protected void registerData() {
    }

    @Nullable
    public Vec3d getNodePointTop() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        return axisAlignedBB.getCenter().add(0.0D, axisAlignedBB.getYSize()/2.0D, 0.0D);
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.getAdjustedCollisionBox().toAxisAlignedBB();
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

    protected abstract OrientedBoundingBox getOrientedCollisionBox();

    public OrientedBoundingBox getAdjustedCollisionBox() {
        OrientedBoundingBox boundingBox = this.getOrientedCollisionBox().offset(this.getPositionVec());
        boundingBox = boundingBox.rotateRelative(this.getPositionVec(), Direction.Axis.Y, Math.toRadians(this.rotationYaw));
        return boundingBox;
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

    public ARocketPart getLastNext() {
        TypeHolder<ARocketPart> typeHolder = new TypeHolder<>(this);
        this.forEachNext(part -> typeHolder.set(part));
        typeHolder.lock();
        return typeHolder.get();
    }

    public void forEachPart(Consumer<ARocketPart> consumer, boolean andReference) {
        if(andReference)
            consumer.accept(this);
        this.forEachPrevious(consumer);
        this.forEachNext(consumer);
    }

    private void forEachPrevious(Consumer<ARocketPart> consumer) {
        if(this.hasPrevious()) {
            ARocketPart part = this.getPrevious();
            do {
                consumer.accept(part);
            }while((part = part.getPrevious()) != null);
        }
    }

    private void forEachNext(Consumer<ARocketPart> consumer) {
        if(this.hasNext()) {
            ARocketPart part = this.getNext();
            do {
                consumer.accept(part);
            }while((part = part.getNext()) != null);
        }
    }

}
