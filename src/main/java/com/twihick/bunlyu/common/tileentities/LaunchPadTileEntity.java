package com.twihick.bunlyu.common.tileentities;

import com.twihick.bunlyu.common.entities.rocket.ARocketPart;
import com.twihick.bunlyu.common.registry.TileEntitiesList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class LaunchPadTileEntity<T extends ARocketPart> extends TileEntity implements ITickableTileEntity {

    @Nullable
    private ARocketPart basePart;

    public LaunchPadTileEntity() {
        super(TileEntitiesList.LAUNCH_PAD);
        this.basePart = null;
    }

    @Override
    public void tick() {
        if(this.hasPartAttached()) {
            this.basePart.forEachPart(part -> {
                AxisAlignedBB axisAlignedBB = part.getBoundingBox();
                double sideX = axisAlignedBB.getXSize() / 2.0D;
                double sideY = axisAlignedBB.getYSize() / 2.0D;
                double sideZ = axisAlignedBB.getZSize() / 2.0D;
                for(double x = -sideX; x < sideX; x += 0.5D) {
                    for(double y = -sideY; y < sideY; y++) {
                        for(double z = -sideZ; z < sideZ; z++) {
                            BlockPos pos = this.getPos().add(x, y, z);
                            if(!this.world.isAirBlock(pos))
                                this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }, true);
        }
    }

    public boolean hasPartAttached() {
        return this.basePart != null;
    }

    public boolean removePart() {
        if(this.hasPartAttached()) {
            ARocketPart part = this.basePart.getLastNext();
            part.remove();
            if(part == this.basePart)
                this.basePart = null;
            return true;
        }
        return false;
    }

    public boolean attachPart(EntityType<T> entity) {
        if(!this.world.isRemote) {
            ARocketPart part = entity.create(this.world);
            if(!this.hasPartAttached()) {
                part.setPosition(this.getPos().getX()+0.5D, this.getPos().getY()+1.1D, this.getPos().getZ()+0.5D);
                this.world.addEntity(part);
                this.basePart = part;
                return true;
            }else if(this.basePart.getLastNext().canSupportPart()) {
                part.setPosition(this.getPos().getX()+0.5D, this.basePart.getLastNext().getNodePointTop().y+1.0D, this.getPos().getZ()+0.5D);
                this.world.addEntity(part);
                this.basePart.getLastNext().setNext(part);
                return true;
            }
        }
        return false;
    }

    @Override
    public void remove() {
        if(!this.world.isRemote && this.basePart != null)
            this.basePart.forEachPart(part -> part.remove(), true);
        super.remove();
    }

}
