package com.twihick.bunlyu.common.registry;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.common.entities.FuelTankSmall;
import com.twihick.bunlyu.common.lib.TypeHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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

    public static final EntityType<FuelTankSmall> FUEL_TANK_SMALL = buildEntity(FuelTankSmall.class, IAssemblyType.Default.getInstance(1.0F, 1.0F), "fuel_tank_small");

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

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ENTITIES.forEach(entity -> event.getRegistry().register(entity));
    }

}
