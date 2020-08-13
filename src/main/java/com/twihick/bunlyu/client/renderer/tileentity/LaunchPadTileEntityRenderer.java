package com.twihick.bunlyu.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.bunlyu.client.renderer.model.ModelGatherer;
import com.twihick.bunlyu.client.renderer.util.SpecialDrawer;
import com.twihick.bunlyu.common.blocks.AHorizontalFacingBlock;
import com.twihick.bunlyu.common.entities.rocket.ARocketPart;
import com.twihick.bunlyu.common.lib.TypeHolder;
import com.twihick.bunlyu.common.tileentities.LaunchPadTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LaunchPadTileEntityRenderer extends TileEntityRenderer<LaunchPadTileEntity> {

    public LaunchPadTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(LaunchPadTileEntity launchPad, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        matrix.push();
        matrix.rotate(Vector3f.YP.rotationDegrees(launchPad.getBlockState().get(AHorizontalFacingBlock.ALIGNMENT).getHorizontalAngle()));
        matrix.translate(-0.5D, 0.0D, -0.5D);
        for(double y = 0.0D; y < launchPad.getPartsNumber()*2.0D; y += 1.2D) {
            matrix.push();
            matrix.translate(0.0D, y, 0.0D);
            SpecialDrawer.drawModel(ModelGatherer.METAL_RAMP.bake(), matrix, buffer, light, overlay);
            matrix.pop();
        }
        matrix.pop();
    }

}
