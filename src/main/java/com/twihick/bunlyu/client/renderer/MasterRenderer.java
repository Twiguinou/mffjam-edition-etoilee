package com.twihick.bunlyu.client.renderer;

import com.twihick.bunlyu.client.renderer.tileentity.LaunchPadTileEntityRenderer;
import com.twihick.bunlyu.common.registry.TileEntitiesList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
public final class MasterRenderer {

    public static void tileEntities() {
        ClientRegistry.bindTileEntityRenderer(TileEntitiesList.LAUNCH_PAD, LaunchPadTileEntityRenderer::new);
    }

}
