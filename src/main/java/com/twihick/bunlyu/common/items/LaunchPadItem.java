package com.twihick.bunlyu.common.items;

import com.twihick.bunlyu.common.blocks.LaunchPadBlock;
import com.twihick.bunlyu.common.blocks.parts.LaunchPadPart;
import com.twihick.bunlyu.common.entities.rocket.RocketCoreEntity;
import com.twihick.bunlyu.common.lib.text.EntryType;
import com.twihick.bunlyu.common.lib.text.TextComponentWriter;
import com.twihick.bunlyu.common.tileentities.LaunchPadTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LaunchPadItem extends BlockItem {

    public LaunchPadItem(Block blockIn, Item.Properties builder) {
        super(blockIn, builder);
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, BlockState state) {
        World world = context.getWorld();
        if(!world.isRemote) {
            if(state.get(LaunchPadBlock.PART) == LaunchPadPart.MIDDLE) {
                for(LaunchPadPart part : LaunchPadPart.values()) {
                    BlockPos pos = context.getPos();
                    for(Direction direction : part.getDirections())
                        pos = pos.offset(direction);
                    if(!world.isAirBlock(pos)) {
                        context.getPlayer().sendStatusMessage(TextComponentWriter.getTranslated(EntryType.ITEM, "wrong_placement"), false);
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        boolean achieved = super.onBlockPlaced(pos, worldIn, player, stack, state);
        if(!worldIn.isRemote && achieved) {
            RocketCoreEntity core = new RocketCoreEntity(worldIn, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D);
            worldIn.addEntity(core);
            TileEntity te = worldIn.getTileEntity(pos);
            if(te instanceof LaunchPadTileEntity) {
                te = (LaunchPadTileEntity) te;
            }
        }
        return achieved;
    }

}
