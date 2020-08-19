package com.twihick.bunlyu.common.entities.rocket;

import com.twihick.bunlyu.common.lib.TypeHolder;
import com.twihick.bunlyu.common.lib.collision.OrientedBoundingBox;
import com.twihick.bunlyu.common.tileentities.LaunchPadTileEntity;
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
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ARocketPart extends Entity {

    private ARocketPart previousPart;
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

    @Override
    public void tick() {
        if(!this.hasPrevious() && !this.isLaunched()) {
            LaunchPadTileEntity launchPad = (LaunchPadTileEntity) this.world.getTileEntity(this.getPosition().add(0.0D, -(this.getBoundingBox().getYSize()/2.0D), 0.0D));
            if(launchPad != null)
                launchPad.setBasePart(this);
        }
        Vec3d node = this.getNodePointTop();
        List<ARocketPart> parts = this.world.getEntitiesWithinAABB(ARocketPart.class, new AxisAlignedBB(node.x, node.y, node.z, node.x+0.1D, node.y+0.1D, node.z+0.1D))
                .stream()
                .filter(part -> part != this)
                .collect(Collectors.toList());
        if(!parts.isEmpty()) {
            ARocketPart part = parts.get(0);
            part.setPrevious(this);
            this.setNext(part);
        }
        if(this.hasNext()) {
            ARocketPart part = this.getNext();
            part.setPosition(this.getNodePointTop().x, this.getNodePointTop().y+1.0D, this.getNodePointTop().z);
            part.setRotation(this.rotationYaw, this.rotationPitch);
        }
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

    public ARocketPart getLastPrevious() {
        TypeHolder<ARocketPart> typeHolder = new TypeHolder<>(this);
        this.forEachPrevious(part -> typeHolder.set(part));
        typeHolder.lock();
        return typeHolder.get();
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

    public void forEachPrevious(Consumer<ARocketPart> consumer) {
        if(this.hasPrevious()) {
            ARocketPart part = this.getPrevious();
            do {
                consumer.accept(part);
            }while((part = part.getPrevious()) != null);
        }
    }

    public void forEachNext(Consumer<ARocketPart> consumer) {
        if(this.hasNext()) {
            ARocketPart part = this.getNext();
            do {
                consumer.accept(part);
            }while((part = part.getNext()) != null);
        }
    }

}
