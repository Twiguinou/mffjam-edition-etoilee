package com.twihick.bunlyu.common.registry;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.common.items.LaunchPadItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsList {

    private static final Set<Item> ITEMS = new HashSet<>();

    public static final Item LAUNCH_PAD = buildItem(new LaunchPadItem(BlocksList.LAUNCH_PAD, new Item.Properties().group(Main.GROUP)), "launch_pad");

    static Item buildItem(Item item, String label) {
        item.setRegistryName(Main.ID + ":" + label);
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ITEMS.forEach(item -> event.getRegistry().register(item));
    }

}
