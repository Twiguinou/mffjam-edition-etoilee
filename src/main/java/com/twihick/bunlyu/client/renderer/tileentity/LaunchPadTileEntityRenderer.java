package com.twihick.bunlyu.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.bunlyu.common.tileentities.LaunchPadTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
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
    }

}
