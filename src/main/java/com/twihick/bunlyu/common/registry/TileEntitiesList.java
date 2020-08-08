package com.twihick.bunlyu.common.registry;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.common.tileentities.LaunchPadTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Main.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntitiesList {

    private static final Set<TileEntityType<?>> TILE_ENTITIES = new HashSet<>();

    public static final TileEntityType<LaunchPadTileEntity> LAUNCH_PAD = buildTileEntity(LaunchPadTileEntity::new, "launch_pad", BlocksList.LAUNCH_PAD);

    private static <T extends TileEntity> TileEntityType<T> buildTileEntity(Supplier<T> supplier, String label, Block... blocks) {
        TileEntityType<T> te = TileEntityType.Builder.create(supplier, blocks).build(null);
        te.setRegistryName(Main.ID + ":" + label);
        TILE_ENTITIES.add(te);
        return te;
    }

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        TILE_ENTITIES.forEach(te -> event.getRegistry().register(te));
    }

}
