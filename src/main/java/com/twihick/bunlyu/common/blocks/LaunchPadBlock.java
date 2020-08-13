package com.twihick.bunlyu.common.blocks;

import com.twihick.bunlyu.common.blocks.parts.LaunchPadPart;
import com.twihick.bunlyu.common.tileentities.LaunchPadTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LaunchPadBlock extends AWaterloggedHorizontalFacingBlock {

    public static final EnumProperty<LaunchPadPart> PART = EnumProperty.create("part", LaunchPadPart.class);
    public static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    public LaunchPadBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(PART, LaunchPadPart.MIDDLE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.isMiddle(state) ? SHAPE : VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return this.isMiddle(state) ? SHAPE : VoxelShapes.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return this.isMiddle(state) ? super.getRenderType(state) : BlockRenderType.INVISIBLE;
    }

    private boolean isMiddle(BlockState state) {
        return state.get(PART) == LaunchPadPart.MIDDLE;
    }

    private void process(World world, BlockPos pos, boolean flag) {
        Set<LaunchPadPart> parts = Arrays.stream(LaunchPadPart.values()).filter(part -> part != LaunchPadPart.MIDDLE).collect(Collectors.toSet());
        for(LaunchPadPart part : parts) {
            BlockPos blockPos = pos;
            for(Direction direction : part.getDirections())
                blockPos = blockPos.offset(direction);
            if(flag)
                world.setBlockState(blockPos, world.getBlockState(pos).with(PART, part));
            else {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!worldIn.isRemote) {
            if(this.isMiddle(state)) {
                this.process(worldIn, pos, true);
            }
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!worldIn.isRemote) {
            if(!this.isMiddle(state))
                for(Direction direction : state.get(PART).getDirections())
                    pos = pos.offset(direction.getOpposite());
            this.process(worldIn, pos, false);
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(worldIn.isAirBlock(currentPos.down()) && this.isMiddle(stateIn)) {
            this.process(worldIn.getWorld(), currentPos, true);
            return Blocks.AIR.getDefaultState();
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(PART) == LaunchPadPart.MIDDLE;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LaunchPadTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(PART);
    }
}
