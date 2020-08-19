package com.twihick.bunlyu.common.items;

import com.twihick.bunlyu.common.blocks.parts.LaunchPadPart;
import com.twihick.bunlyu.common.lib.text.EntryType;
import com.twihick.bunlyu.common.lib.text.TextComponentWriter;
import com.twihick.bunlyu.common.state.CustomProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaunchPadItem extends BlockItem {

    public LaunchPadItem(Block blockIn, Item.Properties builder) {
        super(blockIn, builder);
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, BlockState state) {
        World world = context.getWorld();
        if(!world.isRemote) {
            if(state.get(CustomProperties.PART) == LaunchPadPart.MIDDLE) {
                for(LaunchPadPart part : LaunchPadPart.values()) {
                    BlockPos pos = context.getPos();
                    for(Direction direction : part.getDirections())
                        pos = pos.offset(direction);
                    if(!world.isAirBlock(pos)) {
                        context.getPlayer().sendStatusMessage(TextComponentWriter.getTranslated(EntryType.ALERT, "launch_pad.wrong_placement"), false);
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

}
