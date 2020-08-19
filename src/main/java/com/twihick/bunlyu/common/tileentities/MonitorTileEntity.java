package com.twihick.bunlyu.common.tileentities;

import com.twihick.bunlyu.common.blocks.LaunchPadBlock;
import com.twihick.bunlyu.common.registry.TileEntitiesList;
import com.twihick.bunlyu.common.state.CustomProperties;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class MonitorTileEntity extends TileEntity implements ITickableTileEntity {

    private BlockPos launchPad;

    public MonitorTileEntity() {
        super(TileEntitiesList.MONITOR);
        this.launchPad = null;
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.pos.offset(this.getBlockState().get(CustomProperties.ALIGNMENT)).down();
        BlockState blockState = this.world.getBlockState(blockPos);
        if(blockState.getBlock() instanceof LaunchPadBlock) {
            if(!LaunchPadBlock.isMiddle(blockState)) {
                for(Direction direction : blockState.get(CustomProperties.PART).getDirections())
                    blockPos = blockPos.offset(direction.getOpposite());
            }
            this.setLaunchPad(blockPos);
        }else if(blockState.isAir()) {
            this.setLaunchPad(null);
        }
    }

    private void setLaunchPad(@Nullable BlockPos blockPos) {
        this.launchPad = blockPos;
        this.markDirty();
    }

    public Direction getAlignment() {
        return this.getBlockState().get(CustomProperties.ALIGNMENT);
    }

    public boolean isConnected() {
        return this.launchPad != null;
    }

    @Nullable
    public BlockPos getLaunchPad() {
        return this.launchPad;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.contains("BlockPos", Constants.NBT.TAG_COMPOUND))
            this.setLaunchPad(NBTUtil.readBlockPos(compound));
        else
            this.setLaunchPad(null);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if(this.isConnected())
            compound.put("BlockPos", NBTUtil.writeBlockPos(this.launchPad));
        return compound;
    }
}
