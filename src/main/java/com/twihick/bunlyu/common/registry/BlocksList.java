package com.twihick.bunlyu.common.registry;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.common.blocks.LaunchPadBlock;
import com.twihick.bunlyu.common.blocks.MonitorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlocksList {

    private static final Set<Block> BLOCKS = new HashSet<>();

    public static final Block LAUNCH_PAD = buildBlock(new LaunchPadBlock(Block.Properties.create(Material.IRON)), "launch_pad", false);
    public static final Block MONITOR = buildBlock(new MonitorBlock(Block.Properties.create(Material.IRON)), "monitor", true);

    private static Block buildBlock(Block block, String label, boolean withItem) {
        block.setRegistryName(Main.ID + ":" + label);
        BLOCKS.add(block);
        if(withItem)
            ItemsList.buildItem(new BlockItem(block, new Item.Properties().group(Main.GROUP)), label);
        return block;
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
        BLOCKS.clear();
    }

}
