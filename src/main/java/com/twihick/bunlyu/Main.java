package com.twihick.bunlyu;

import com.twihick.bunlyu.parallel.ParallelClient;
import com.twihick.bunlyu.parallel.ParallelServer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.ID)
public final class Main {

    public static final String ID = "bunlyu";
    public static final ItemGroup GROUP = new ItemGroup(ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.ITEM_FRAME);
        }
    };

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ParallelServer parallel = DistExecutor.runForDist(() -> ParallelClient::new, () -> ParallelServer::new);
        bus.addListener(parallel::common);
        bus.addListener(parallel::server);
        bus.addListener(parallel::client);
        MinecraftForge.EVENT_BUS.register(this);
    }

}
