package com.twihick.bunlyu.client.renderer.model;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IAssociativeModel {

    ResourceLocation getLocation();

    IBakedModel bake();

}
