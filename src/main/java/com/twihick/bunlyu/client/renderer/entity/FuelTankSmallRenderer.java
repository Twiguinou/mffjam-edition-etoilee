package com.twihick.bunlyu.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.bunlyu.client.renderer.util.SpecialDrawer;
import com.twihick.bunlyu.common.entities.FuelTankSmall;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FuelTankSmallRenderer extends EntityRenderer<FuelTankSmall> {

    public FuelTankSmallRenderer(EntityRendererManager manager) {
        super(manager);
        manager.setDebugBoundingBox(true);
    }

    @Override
    public void render(FuelTankSmall fuelTank, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        for(Vec3d vec3d : fuelTank.getOrientedCollisionBox().offset(fuelTank.getPositionVec()).getNodes())
            SpecialDrawer.drawPoint(vec3d, 1.0F, 0.0F, 1.0F, 1.0F, 10.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(FuelTankSmall entity) {
        return null;
    }

}
