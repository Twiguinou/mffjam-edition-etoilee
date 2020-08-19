package com.twihick.bunlyu.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.bunlyu.common.tileentities.MonitorTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MonitorTileEntityRenderer extends TileEntityRenderer<MonitorTileEntity> {

    public MonitorTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(MonitorTileEntity monitor, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
    }

}
