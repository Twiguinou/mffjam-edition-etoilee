package com.twihick.bunlyu.parallel;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public interface IParallel {

    default void common(final FMLCommonSetupEvent event) {
    }

    void server(final FMLServerStartingEvent event);

    void client(final FMLClientSetupEvent event);

}
