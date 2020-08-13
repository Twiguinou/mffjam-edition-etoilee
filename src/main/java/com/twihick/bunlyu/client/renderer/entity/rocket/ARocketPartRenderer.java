package com.twihick.bunlyu.client.renderer.entity.rocket;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.bunlyu.client.renderer.model.IAssociativeModel;
import com.twihick.bunlyu.client.renderer.util.SpecialDrawer;
import com.twihick.bunlyu.common.entities.rocket.ARocketPart;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ARocketPartRenderer<T extends ARocketPart> extends EntityRenderer<T> {

    private final IAssociativeModel model;

    public ARocketPartRenderer(EntityRendererManager manager, IAssociativeModel model) {
        super(manager);
        this.model = model;
    }

    @Override
    public void render(T part, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.push();
        matrix.rotate(Vector3f.YP.rotationDegrees(-entityYaw));
        SpecialDrawer.drawModel(this.model.bake(), matrix, buffer, light, OverlayTexture.NO_OVERLAY);
        if(!part.hasNext() && part.canSupportPart())
            SpecialDrawer.drawSphere(part.getNodePointTop(), 1.0F, 1.0F, 0.0F, 0.3F, 0.175D);
        matrix.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return null;
    }

}
