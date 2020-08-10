package com.twihick.bunlyu.client.renderer.entity;

import com.twihick.bunlyu.common.entities.FuelTankSmall;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FuelTankSmallRenderer extends EntityRenderer<FuelTankSmall> {

    public FuelTankSmallRenderer(EntityRendererManager manager) {
        super(manager);
        manager.setDebugBoundingBox(true);
    }

    @Override
    public ResourceLocation getEntityTexture(FuelTankSmall entity) {
        return null;
    }

}
