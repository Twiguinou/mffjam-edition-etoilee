package com.twihick.bunlyu.parallel;

import com.twihick.bunlyu.common.lib.Mandatory;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ParallelServer implements IParallel {

    @Override
    public final void server(FMLServerStartingEvent event) {
    }

    @Mandatory
    @Override
    public void client(FMLClientSetupEvent event) {
    }

}
