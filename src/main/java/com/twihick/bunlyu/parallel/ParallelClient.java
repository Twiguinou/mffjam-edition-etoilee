package com.twihick.bunlyu.parallel;

import com.twihick.bunlyu.client.renderer.MasterRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ParallelClient extends ParallelServer {

    @Override
    public void client(FMLClientSetupEvent event) {
        MasterRenderer.entities();
        MasterRenderer.tileEntities();
    }
}
