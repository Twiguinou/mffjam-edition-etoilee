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
            for(int y = 1; y < this.getPartsNumber()*2; y++) {
                BlockPos blockPos = this.getPos().add(0, y, 1);
                for(LaunchPadPart part : LaunchPadPart.values()) {
                    for(Direction direction : part.getDirections())
                        blockPos = blockPos.offset(direction);
                    if(!this.world.isAirBlock(blockPos)) {
                        System.out.println(blockPos.toString());
                        this.world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }

    public int getPartsNumber() {
        TypeHolder<Integer> typeHolder = new TypeHolder<>(0);
        if(this.hasPartAttached()) {
            this.basePart.forEachPart(part -> typeHolder.set(typeHolder.get()+1), true);
            typeHolder.lock();
            return typeHolder.get();
        }
        return 0;
    }

    public boolean hasPartAttached() {
        return this.basePart != null;
    }

    public void removePart() {
        if(this.hasPartAttached() && !this.world.isRemote) {
            if(this.basePart.hasNext()) {
                ARocketPart part = this.basePart.getLastNext();
                part.remove();
                part.getPrevious().setNext(null);
            }else {
                this.basePart.remove();
                this.setBasePart(null);
            }
        }
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
