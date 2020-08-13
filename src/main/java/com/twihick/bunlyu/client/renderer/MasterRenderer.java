package com.twihick.bunlyu.client.renderer;

import com.twihick.bunlyu.client.renderer.entity.rocket.EngineSmallRenderer;
import com.twihick.bunlyu.client.renderer.entity.rocket.FuelTankSmallRenderer;
import com.twihick.bunlyu.client.renderer.tileentity.LaunchPadTileEntityRenderer;
import com.twihick.bunlyu.common.registry.EntitiesList;
import com.twihick.bunlyu.common.registry.TileEntitiesList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public final class MasterRenderer {

    public static void entities() {
        RenderingRegistry.registerEntityRenderingHandler(EntitiesList.ENGINE_SMALL, EngineSmallRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitiesList.FUEL_TANK_SMALL, FuelTankSmallRenderer::new);
    }

    public static void tileEntities() {
        ClientRegistry.bindTileEntityRenderer(TileEntitiesList.LAUNCH_PAD, LaunchPadTileEntityRenderer::new);
    }

}
