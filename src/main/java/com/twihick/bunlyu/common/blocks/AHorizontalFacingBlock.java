package com.twihick.bunlyu.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public abstract class AHorizontalFacingBlock extends Block {

    public static final DirectionProperty ALIGNMENT = BlockStateProperties.HORIZONTAL_FACING;

    public AHorizontalFacingBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(ALIGNMENT, Direction.NORTH));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.with(ALIGNMENT, rotation.rotate(state.get(ALIGNMENT)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.toRotation(state.get(ALIGNMENT)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(ALIGNMENT, context.getPlacementHorizontalFacing());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(ALIGNMENT);
    }
}
