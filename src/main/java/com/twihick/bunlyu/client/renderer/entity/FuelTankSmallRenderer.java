package com.twihick.bunlyu.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.bunlyu.client.renderer.model.ModelGatherer;
import com.twihick.bunlyu.client.renderer.util.SpecialDrawer;
import com.twihick.bunlyu.common.entities.rocket.FuelTankSmall;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class FuelTankSmallRenderer extends EntityRenderer<FuelTankSmall> {

    public FuelTankSmallRenderer(EntityRendererManager manager) {
        super(manager);
        manager.setDebugBoundingBox(true);
    }

    @Override
    public void render(FuelTankSmall fuelTank, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.push();
        matrix.rotate(Vector3f.YP.rotationDegrees(-entityYaw));
        SpecialDrawer.drawModel(ModelGatherer.FUEL_TANK_SMALL.bake(), matrix, buffer, light, OverlayTexture.NO_OVERLAY, Optional.of(0.3F));
        if(!fuelTank.hasNext())
            SpecialDrawer.drawSphere(fuelTank.getNodePointTop(), 1.0F, 1.0F, 0.0F, 0.3F, 0.25D);
        matrix.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(FuelTankSmall entity) {
        return null;
    }

}
