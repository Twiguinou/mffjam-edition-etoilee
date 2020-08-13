package com.twihick.bunlyu.common.registry;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.common.entities.rocket.ARocketPart;
import com.twihick.bunlyu.common.entities.rocket.EngineSmall;
import com.twihick.bunlyu.common.entities.rocket.FuelTankSmall;
import com.twihick.bunlyu.common.items.RocketPartItem;
import com.twihick.bunlyu.common.lib.TypeHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesList {

    private static final Logger LOGGER = LogManager.getLogger(EntitiesList.class);
    private static final Set<EntityType<?>> ENTITIES = new HashSet<>();

    public static final EntityType<EngineSmall> ENGINE_SMALL = buildRocketPart(EngineSmall.class, "engine_small");
    public static final EntityType<FuelTankSmall> FUEL_TANK_SMALL = buildRocketPart(FuelTankSmall.class, "fuel_tank_small");

    private static <T extends Entity> EntityType<T> buildEntity(Class<T> clazz, IAssemblyType properties, String label) {
        Vec2f size = properties.getSize();
        // EntityType<T> holder, actually, using #spawnEntity.getEntity().getType()# make strange things.
        final TypeHolder<EntityType<T>> savedType = new TypeHolder<>();
        EntityType.Builder<T> builder = EntityType.Builder.<T>create((type, world) -> {
            T entity = null;
            try {
                entity = clazz.getDeclaredConstructor(EntityType.class, World.class).newInstance(type, world);
                // Here is where the type is stored
                savedType.lockAndSet(type);
            }catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                LOGGER.error(e);
            }
            return entity;
        }, properties.getClassification()).size(size.x, size.y).setCustomClientFactory((spawnEntity, world) -> {
            T entity = null;
            try {
                // Here is where the savedType is used.
                entity = clazz.getDeclaredConstructor(EntityType.class, World.class).newInstance(savedType.get(), world);
            }catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                LOGGER.error(e);
            }
            return entity;
        }).setTrackingRange(properties.getTrackingRange()).setUpdateInterval(properties.getUpdateInterval());
        if(properties.isImmuneToFire())
            builder.immuneToFire();
        final String id = Main.ID + ":" + label;
        EntityType<T> entity = builder.build(id);
        entity.setRegistryName(id);
        ENTITIES.add(entity);
        return entity;
    }

    private static <T extends ARocketPart> EntityType<T> buildRocketPart(Class<T> clazz, String label) {
        EntityType<T> entity = buildEntity(clazz, IAssemblyType.Default.getInstance(0.0F, 0.0F), label);
        ItemsList.buildItem(new RocketPartItem(new Item.Properties().group(Main.GROUP).maxStackSize(1), entity), label);
        return entity;
    }

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ENTITIES.forEach(entity -> event.getRegistry().register(entity));
        ENTITIES.clear();
    }

}
