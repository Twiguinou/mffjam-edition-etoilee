package com.twihick.bunlyu.client.renderer.entity.rocket.part;

import com.twihick.bunlyu.common.entities.rocket.part.engine.TestEngine;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestEngineRenderer extends EntityRenderer<TestEngine> {

    public TestEngineRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(TestEngine entity) {
        return null;
    }

}
