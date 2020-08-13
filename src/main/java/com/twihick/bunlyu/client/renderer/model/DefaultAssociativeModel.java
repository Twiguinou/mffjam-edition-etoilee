package com.twihick.bunlyu.client.renderer.model;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.common.lib.TypeHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefaultAssociativeModel implements IAssociativeModel {

    private final ResourceLocation location;
    private final TypeHolder<IBakedModel> model;

    public DefaultAssociativeModel(String name, Directory directory) {
        this.location = new ResourceLocation(Main.ID, directory.getPrefix() + name);
        this.model = new TypeHolder<>();
        ModelGatherer.add(this);
    }

    @Override
    public ResourceLocation getLocation() {
        return this.location;
    }

    @Override
    public IBakedModel bake() {
        if(this.model.get() == null) {
            ModelManager manager = Minecraft.getInstance().getModelManager();
            IBakedModel loadedModel = manager.getModel(this.location);
            if(loadedModel == manager.getMissingModel())
                return loadedModel;
            this.model.lockAndSet(loadedModel);
        }
        return this.model.get();
    }

}
