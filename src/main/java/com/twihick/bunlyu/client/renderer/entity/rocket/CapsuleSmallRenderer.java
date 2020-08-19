package com.twihick.bunlyu.client.renderer.entity.rocket;

import com.twihick.bunlyu.client.renderer.model.ModelGatherer;
import com.twihick.bunlyu.common.entities.rocket.CapsuleSmall;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CapsuleSmallRenderer extends ARocketPartRenderer<CapsuleSmall> {

    public CapsuleSmallRenderer(EntityRendererManager manager) {
        super(manager, ModelGatherer.CAPSULE_SMALL);
    }

}
