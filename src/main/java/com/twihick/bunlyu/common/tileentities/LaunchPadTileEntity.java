package com.twihick.bunlyu.common.tileentities;

import com.twihick.bunlyu.common.blocks.parts.LaunchPadPart;
import com.twihick.bunlyu.common.entities.rocket.ARocketPart;
import com.twihick.bunlyu.common.lib.TypeHolder;
import com.twihick.bunlyu.common.registry.TileEntitiesList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class LaunchPadTileEntity extends TileEntity implements ITickableTileEntity {

    @Nullable
    private ARocketPart basePart;

    public LaunchPadTileEntity() {
        super(TileEntitiesList.LAUNCH_PAD);
    }

    @Override
    public void tick() {
        if(this.hasPartAttached()) {
            for(double y = 1; y < 15; y++) {
                BlockPos pos = this.getPos().add(this.getPos().getX(), y, this.getPos().getZ());
                for(LaunchPadPart part : LaunchPadPart.values()) {
                    for(Direction direction : part.getDirections())
                        pos = pos.offset(direction);
                    if(!this.world.isAirBlock(pos))
                        this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    public int getPartsNumber() {
        TypeHolder<Integer> typeHolder = new TypeHolder<>();
        if(this.hasPartAttached()) {
            typeHolder.set(1);
            this.basePart.forEachPart(part -> typeHolder.set(typeHolder.get()+1), false);
            typeHolder.lock();
            return typeHolder.get();
        }
        return 0;
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

    public <T extends ARocketPart> boolean attachPart(EntityType<T> entity) {
        if(!this.world.isRemote) {
            ARocketPart part = entity.create(this.world);
            if(!this.hasPartAttached()) {
                part.setPosition(this.getPos().getX()+0.5D, this.getPos().getY()+1.1D, this.getPos().getZ()+0.5D);
                this.world.addEntity(part);
                this.basePart = part;
                return true;
            }else if(this.basePart.getLastNext().canSupportPart()) {
                ARocketPart last = this.basePart.getLastNext();
                part.setPosition(this.getPos().getX()+0.5D, last.getNodePointTop().y+1.0D, this.getPos().getZ()+0.5D);
                this.world.addEntity(part);
                part.setPrevious(last);
                last.setNext(part);
                return true;
            }
        }
        return false;
    }

    public boolean setBasePart(ARocketPart part) {
        if(this.basePart == null) {
            this.basePart = part;
            return true;
        }
        return false;
    }

    @Override
    public void remove() {
        if(!this.world.isRemote && this.hasPartAttached())
            this.basePart.forEachPart(part -> part.remove(), true);
        super.remove();
    }

}
