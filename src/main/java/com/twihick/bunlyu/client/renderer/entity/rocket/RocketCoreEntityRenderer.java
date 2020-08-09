package com.twihick.bunlyu.client.renderer.entity.rocket;

import com.twihick.bunlyu.common.entities.rocket.RocketCoreEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RocketCoreEntityRenderer extends EntityRenderer<RocketCoreEntity> {

    public RocketCoreEntityRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(RocketCoreEntity entity) {
        return null;
    }

}
