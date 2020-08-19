package com.twihick.bunlyu.common.items;

import com.twihick.bunlyu.common.blocks.LaunchPadBlock;
import com.twihick.bunlyu.common.blocks.parts.LaunchPadPart;
import com.twihick.bunlyu.common.entities.rocket.ARocketPart;
import com.twihick.bunlyu.common.state.CustomProperties;
import com.twihick.bunlyu.common.tileentities.LaunchPadTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class RocketPartItem<T extends ARocketPart> extends Item {

    private final EntityType<T> part;

    public RocketPartItem(Item.Properties properties, EntityType<T> part) {
        super(properties);
        this.part = part;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        RayTraceResult result = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
        if(result.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos pos = ((BlockRayTraceResult)result).getPos();
            BlockState state = worldIn.getBlockState(pos);
            if(state.getBlock() instanceof LaunchPadBlock) {
                if(state.get(CustomProperties.PART) == LaunchPadPart.MIDDLE) {
                    LaunchPadTileEntity te = (LaunchPadTileEntity) worldIn.getTileEntity(pos);
                    if(te.attachPart(this.part)) {
                        return ActionResult.resultConsume(stack);
                    }
                }
            }
        }
        return ActionResult.resultPass(stack);
    }
}
