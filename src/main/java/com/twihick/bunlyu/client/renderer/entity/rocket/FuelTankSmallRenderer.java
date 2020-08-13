package com.twihick.bunlyu.client.renderer.entity.rocket;

import com.twihick.bunlyu.client.renderer.model.ModelGatherer;
import com.twihick.bunlyu.common.entities.rocket.FuelTankSmall;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FuelTankSmallRenderer extends ARocketPartRenderer<FuelTankSmall> {

    public FuelTankSmallRenderer(EntityRendererManager manager) {
        super(manager, ModelGatherer.FUEL_TANK_SMALL);
        manager.setDebugBoundingBox(true);
    }

}
