package com.twihick.bunlyu.common.blocks;

import com.google.common.collect.ImmutableMap;
import com.twihick.bunlyu.client.gui.GuiHelpers;
import com.twihick.bunlyu.common.lib.collision.ShapeComparator;
import com.twihick.bunlyu.common.lib.text.EntryType;
import com.twihick.bunlyu.common.lib.text.TextComponentWriter;
import com.twihick.bunlyu.common.state.CustomProperties;
import com.twihick.bunlyu.common.tileentities.MonitorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MonitorBlock extends AWaterloggedHorizontalFacingBlock {

    public static final BooleanProperty TOP = CustomProperties.TOP;
    private final ImmutableMap<BlockState, VoxelShape> bottomShapes;
    private final ImmutableMap<BlockState, VoxelShape> topShapes;

    public MonitorBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(TOP, false));
        VoxelShape[] shapes0 = ShapeComparator.getHorizontalSubs(Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 1.0D, 12.0D));
        VoxelShape[] shapes1 = ShapeComparator.getHorizontalSubs(Block.makeCuboidShape(4.5D, 1.0D, 4.5D, 11.5D, 15.5D, 11.5D));
        VoxelShape[] shapes2 = ShapeComparator.getHorizontalSubs(Block.makeCuboidShape(3.0D, 1.0D, 6.4087D, 13.0D, 5.0D, 9.5913D));
        VoxelShape[] shapes3 = ShapeComparator.getHorizontalSubs(Block.makeCuboidShape(3.0D, 11.5D, 6.4087D, 13.0D, 15.5D, 9.5913D));
        this.bottomShapes = ShapeComparator.generateShapeMap(this.getStateContainer().getValidStates(), shapes0, shapes1, shapes2, shapes3);
        VoxelShape[] shapes4 = ShapeComparator.getHorizontalSubs(Block.makeCuboidShape(4.5D, 0.0D, 4.5D, 11.5D, 0.5D, 11.5D));
        VoxelShape[] shapes5 = ShapeComparator.getHorizontalSubs(Block.makeCuboidShape(4.625D, 0.5D, 6.5D, 11.375D, 3.25D, 11.25D));
        VoxelShape[] shapes6 = ShapeComparator.getHorizontalSubs(Block.makeCuboidShape(4.85D, 0.5D, 5.2D, 11.15D, 9.05D, 11.5D));
        this.topShapes = ShapeComparator.generateShapeMap(this.getStateContainer().getValidStates(), shapes4, shapes5, shapes6);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(TOP) ? this.topShapes.get(state) : this.bottomShapes.get(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.get(TOP) ? this.topShapes.get(state) : this.bottomShapes.get(state);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(state.get(TOP)) {
            if(GuiHelpers.showMonitorScreen(worldIn, pos))
                return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!state.get(TOP) && !worldIn.isRemote) {
            if(worldIn.isAirBlock(pos.up())) {
                worldIn.setBlockState(pos.up(), state.with(TOP, true));
            }else {
                if(placer instanceof PlayerEntity)
                    ((PlayerEntity)placer).sendStatusMessage(TextComponentWriter.getTranslated(EntryType.ALERT, "monitor.wrong_placement"), false);
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!worldIn.isRemote) {
            if(!state.get(TOP))
                worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
            else
                worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(TOP);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MonitorTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(TOP);
    }

}
